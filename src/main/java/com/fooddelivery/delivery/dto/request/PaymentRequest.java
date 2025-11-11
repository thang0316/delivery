package com.fooddelivery.delivery.dto.request;

public class PaymentRequest {
	private Long orderId;
    private String method;   // Hình thức thanh toán
    
    private String status;   // Trạng thái ("PENDING", "SUCCESS", "FAILED")
    
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

    
}
