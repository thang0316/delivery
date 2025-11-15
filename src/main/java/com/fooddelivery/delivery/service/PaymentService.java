package com.fooddelivery.delivery.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fooddelivery.delivery.config.VNPayConfig;
import com.fooddelivery.delivery.dto.request.PaymentRequest;
import com.fooddelivery.delivery.entity.Order;
import com.fooddelivery.delivery.entity.Payment;
import com.fooddelivery.delivery.repository.OrderRepository;
import com.fooddelivery.delivery.repository.PaymentRepository;
import com.fooddelivery.delivery.util.VNPayUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private VNPayConfig vnPayConfig;

    // 🔹 Tạo mới payment
    public Payment createPayment(PaymentRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Payment payment = new Payment();
        payment.setOrder(order); // setter sẽ tự động lấy amount từ order.totalAmount
        payment.setMethod(request.getMethod());
        payment.setStatus(Payment.PaymentStatus.PENDING); // mặc định là PENDING
        payment.setCreatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    // 🔹 Lấy tất cả payment
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    // 🔹 Lấy payment theo ID
    public Payment getPaymentById(String id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy payment với ID: " + id));
    }

    // 🔹 Lấy payment theo đơn hàng
    public List<Payment> getPaymentsByOrder(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    
    // 🔹 Cập nhật trạng thái payment
    public Payment updatePayment(String id, Payment.PaymentStatus status) {
        Payment payment = getPaymentById(id);
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    // 🔹 Xóa payment
    public void deletePayment(String id) {
        paymentRepository.deleteById(id);
    }
    
    // ==================== VNPAY INTEGRATION ====================
    
    /**
     * Tạo URL thanh toán VNPay
     * @param orderId ID của đơn hàng cần thanh toán
     * @param ipAddress IP của người dùng
     * @return URL redirect đến trang thanh toán VNPay
     */
    public String createVNPayPaymentUrl(Long orderId, String ipAddress) throws UnsupportedEncodingException {
        // 1. Lấy thông tin đơn hàng
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        
        // 2. Tạo payment record với status PENDING
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setMethod("VNPAY");
        payment.setStatus(Payment.PaymentStatus.PENDING);
        payment = paymentRepository.save(payment);
        
        // 3. Chuẩn bị tham số cho VNPay
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_TxnRef = payment.getId(); // Mã giao dịch = Payment ID
        String vnp_IpAddr = ipAddress;
        String orderType = "other"; // Loại hàng hóa
        
        // Số tiền phải nhân 100 (VNPay yêu cầu đơn vị VNĐ * 100)
        long amount = (long) (order.getTotalAmount() * 100);
        
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnPayConfig.getVnpTmnCode());
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang " + orderId);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnpReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        
        // Thời gian tạo giao dịch
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        
        // Thời gian hết hạn (15 phút)
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        
        // 4. Tạo chữ ký bảo mật
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data (URL encode với UTF-8)
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                // Build query (URL encode với UTF-8)
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getVnpHashSecret(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        
        return vnPayConfig.getVnpUrl() + "?" + queryUrl;
    }
    
    /**
     * Xử lý callback từ VNPay sau khi người dùng thanh toán
     * @param request HttpServletRequest chứa các tham số VNPay trả về
     * @return Payment đã được cập nhật trạng thái
     */
    public Payment handleVNPayCallback(HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }
        
        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        fields.remove("vnp_SecureHashType");
        fields.remove("vnp_SecureHash");
        
        // Tạo chữ ký để kiểm tra (giống như lúc tạo URL)
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = fields.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.toString()));
                if (itr.hasNext()) {
                    hashData.append('&');
                }
            }
        }
        
        String signValue = VNPayUtil.hmacSHA512(vnPayConfig.getVnpHashSecret(), hashData.toString());
        
        if (!signValue.equals(vnp_SecureHash)) {
            throw new RuntimeException("Chữ ký không hợp lệ");
        }
        
        // Lấy kết quả thanh toán
        String vnp_ResponseCode = request.getParameter("vnp_ResponseCode");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef"); // Payment ID
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo"); // Mã giao dịch VNPay
        String vnp_BankCode = request.getParameter("vnp_BankCode"); // Mã ngân hàng
        
        Payment payment = paymentRepository.findById(vnp_TxnRef)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy payment"));
        
        // Cập nhật trạng thái payment
        if ("00".equals(vnp_ResponseCode)) {
            // Thanh toán thành công
            payment.setStatus(Payment.PaymentStatus.COMPLETED);
            payment.setTransactionId(vnp_TransactionNo);
            payment.setBankCode(vnp_BankCode);
            payment.setCompletedAt(LocalDateTime.now());
        } else {
            // Thanh toán thất bại
            payment.setStatus(Payment.PaymentStatus.FAILED);
        }
        
        return paymentRepository.save(payment);
    }
}
