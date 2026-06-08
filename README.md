# FoodFast Delivery

Ứng dụng web đặt và quản lý đơn hàng đồ ăn xây dựng bằng Spring Boot. Dự án cung cấp giao diện cho khách hàng, nhà hàng và quản trị viên, đồng thời hỗ trợ thanh toán VNPay, quản lý đơn hàng, menu, giao hàng, người dùng và đánh giá.

## Công nghệ sử dụng

- Java 21
- Spring Boot 3.5.7
- Spring Web
- Spring Data JPA
- Spring Security
- Thymeleaf
- MySQL
- Maven Wrapper
- Docker

## Tính năng chính

- Đăng nhập, đăng ký và phân quyền người dùng.
- Quản lý nhà hàng, món ăn, đơn hàng, giao hàng, drone, vai trò và đánh giá.
- Giao diện web theo từng khu vực: admin, customer, restaurant.
- Tạo thanh toán và xử lý callback VNPay.

## Yêu cầu môi trường

- JDK 21
- Maven 3.9+ hoặc Maven Wrapper
- MySQL 8+

## Cấu hình

Ứng dụng chạy mặc định ở cổng `8080`.

File cấu hình chính nằm tại `src/main/resources/application.yaml`.

Bạn cần kiểm tra và cập nhật các giá trị sau trước khi chạy:

- `spring.datasource.url`
- `spring.datasource.username`
- `spring.datasource.password`
- `vnpay.vnp-tmn-code`
- `vnpay.vnp-hash-secret`
- `vnpay.vnp-return-url`

Khuyến nghị dùng biến môi trường để thay thế thông tin nhạy cảm khi triển khai thực tế.

## Chạy ứng dụng

### Trên máy local

```bash
./mvnw spring-boot:run
```

Trên Windows:

```powershell
mvnw.cmd spring-boot:run
```

### Build JAR

```bash
./mvnw clean package
```

Sau khi build xong, file JAR sẽ nằm trong thư mục `target/`.

### Chạy bằng Docker

```bash
docker build -t delivery-app .
docker run -p 8080:8080 delivery-app
```

## API thanh toán chính

- `POST /api/payments` - tạo payment mới.
- `GET /api/payments` - danh sách payment.
- `GET /api/payments/{id}` - xem payment theo ID.
- `PUT /api/payments/{id}/status` - cập nhật trạng thái payment.
- `DELETE /api/payments/{id}` - xoá payment.
- `GET /api/payments/order/{orderId}` - lấy payment theo đơn hàng.
- `POST /api/payments/vnpay/create` - tạo URL thanh toán VNPay.
- `GET /api/payments/vnpay/return` - nhận callback từ VNPay sau thanh toán.

## Cấu trúc dự án

- `src/main/java/com/fooddelivery/delivery` (source packages)
- `src/main/resources` (Thymeleaf templates, static assets, `application.yaml`)
- Entry point: `DeliveryApplication.java` in the package root

```text
src/main/java/com/fooddelivery/delivery
├── config
├── controller
├── dto
├── entity
├── repository
├── security
├── service
└── util
```

