# Bát Tự AI (BatTu) ☯️

![Phiên bản](https://img.shields.io/badge/version-2.2.0-blue)
![Nền tảng](https://img.shields.io/badge/platform-Android-green)
![Ngôn ngữ](https://img.shields.io/badge/kotlin-2.0.20-purple)

Ứng dụng Android hiện đại giúp tính toán và luận giải lá số Bát Tự (Bốn Trụ - Tứ Trụ) ứng dụng trí tuệ nhân tạo Gemini để đưa ra các phân tích chuyên sâu về mệnh lý.

## 📱 Giới thiệu dự án

**Bát Tự AI** là một công cụ mạnh mẽ dành cho cả những người mới bắt đầu và các chuyên gia nghiên cứu mệnh lý. Ứng dụng kết hợp giữa tinh hoa mệnh lý học truyền thống (Tử Bình Chân Thuyên, Trích Thiên Tùy) và công nghệ AI tiên tiến nhất hiện nay.

### Các tính năng chính:
- **Tính toán Tứ Trụ:** Xác định chính xác Trụ Năm, Tháng, Ngày, Giờ dựa trên Tiết Khí.
- **Thành phần chi tiết:** Tra cứu Nạp Âm, Tàng Can (Bản khí, Trung khí, Dư khí), Thập Thần.
- **Phân tích Ngũ Hành:** Thống kê điểm số Ngũ Hành có trọng số và đánh giá sức mạnh Nhật Chủ dựa trên 12 vòng Trường Sinh.
- **Thần Sát cao cấp:** Tra cứu Thiên Ất Quý Nhân, Lộc Tồn, Kình Dương, Dịch Mã, Hoa Cái, Văn Xương, Đào Hoa, Thiên Y...
- **Tương tác Địa Chi:** Nhận diện đầy đủ Lục Hợp, Lục Xung, Tam Hợp, Bán Tam Hợp, Tam Hình, Tự Hình, Phục Ngâm.
- **Trụ Phụ (Auxiliary Pillars):** Tích hợp **Thai Nguyên** và **Mệnh Cung** giúp bổ trợ luận đoán.
- **Đại Vận & Lưu Niên:** Tính toán mốc khởi vận chính xác đến từng ngày và dự báo cho từng năm.
- **Luận giải AI:** Tích hợp Google Gemini (Flash/Pro) để giải mã lá số theo phong cách điềm đạm, chuyên môn cao.
- **Quản lý lịch sử:** Lưu trữ và xem lại các lá số đã tính toán (Offline) bằng Room Database.

## 🛠️ Hướng dẫn cài đặt

### Yêu cầu hệ thống:
- Android Studio Ladybug (hoặc mới hơn).
- Android SDK 24 (Android 7.0) trở lên.
- JDK 21.

### Các bước thực hiện:
1. Clone repository:
   ```bash
   git clone https://github.com/skul9x/battu.git
   ```
2. Mở dự án trong **Android Studio**.
3. Chờ Gradle đồng bộ các thư viện.
4. Build và chạy trên thiết bị Android hoặc Emulator.

## 🚀 Cách sử dụng
1. **Nhập thông tin:** Tên, giới tính, ngày giờ sinh (Dương lịch hoặc Âm lịch).
2. **Xem lá số:** Ứng dụng sẽ hiển thị bảng Tứ Trụ với đầy đủ thông tin chi tiết.
3. **Cấu hình AI:** Vào mục **Cài đặt** (icon bánh răng) để thêm API Key từ [Google AI Studio](https://aistudio.google.com/apikey).
4. **Luận giải:** Nhấn nút **"Gửi AI"** để nhận phân tích chi tiết về tính cách, sự nghiệp, tài lộc và sức khỏe.

## 📂 Cấu trúc thư mục chính
- `app/src/main/java/com/skul9x/battu/core`: Chứa engine tính toán lõi (BaZiLogic, BaZiConstants).
- `app/src/main/java/com/skul9x/battu/data`: Các mô hình dữ liệu (Models) và cơ sở dữ liệu Room.
- `app/src/main/java/com/skul9x/battu/ai`: Tích hợp Gemini API và xây dựng Prompt logic.
- `app/src/main/java/com/skul9x/battu/ui`: Giao diện người dùng (Jetpack Compose).
- `.brain`: Chứa kiến thức và trạng thái dự án của trợ lý AI Antigravity (Mandatory for AWF).

## 📄 Bản quyền
Copyright 2026 Nguyễn Duy Trường

---
*Dự án được phát triển với sự hỗ trợ của Antigravity Workflow Framework (AWF).*
*Cập nhật lần cuối: 26/03/2026*
