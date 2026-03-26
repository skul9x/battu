# Bát Tự AI (BatTu) ☯️

![Phiên bản](https://img.shields.io/badge/version-2.3.0-blue)
![Nền tảng](https://img.shields.io/badge/platform-Android-green)
![Ngôn ngữ](https://img.shields.io/badge/kotlin-2.0.20-purple)

**Bát Tự AI** là ứng dụng Android hiện đại giúp tính toán và luận giải lá số Bát Tự (Tứ Trụ) dựa trên các nguyên lý mệnh lý truyền thống (Tử Bình). Ứng dụng tích hợp trí tuệ nhân tạo Gemini để đưa ra các phân tích chuyên sâu và cá nhân hóa.

---

## 📱 Mô tả dự án

Ứng dụng cung cấp bộ công cụ mạnh mẽ để lập lá số Tứ Trụ, phân tích độ vượng suy của Ngũ Hành và dự đoán vận hạn. Đây là kết hợp giữa thuật toán mệnh lý chính xác và khả năng ngôn ngữ của AI hiện đại.

### Các tính năng chính:
- **Lập lá số Tứ Trụ:** Tính toán Năm, Tháng, Ngày, Giờ sinh theo Tiết Khí.
- **Phân tích Ngũ Hành (v2.3.0):** Tính điểm Ngũ Hành có trọng số (Seasonality & Xun Kong).
- **Thần Sát & Tương Tác:** Tra cứu đầy đủ các sao (Quý Nhân, Đào Hoa...) và các quan hệ Hợp/Xung/Hình/Hại.
- **Đại Vận & Lưu Niên:** Dự báo chi tiết theo từng thời kỳ và từng năm.
- **Luận giải AI:** Kết nối Gemini API để giải mã lá số một cách thông minh.

---

## 🛠️ Hướng dẫn cài đặt

### Yêu cầu hệ thống:
- **Android Studio:** Ladybug 2024.2.1 hoặc mới hơn.
- **JDK:** Phiên bản 21.
- **Android SDK:** Cấp độ 24 (Android 7.0) trở lên.

### Các bước cài đặt:
1. Clone repository:
   ```bash
   git clone https://github.com/skul9x/battu.git
   ```
2. Mở dự án trong **Android Studio**.
3. Đồng bộ Gradle và chờ tải các dependencies.
4. Chạy ứng dụng trên thiết bị thật hoặc máy ảo (Emulator).

---

## 🚀 Cách sử dụng

1. **Nhập dữ liệu:** Điền tên, giới tính và ngày giờ sinh. Hỗ trợ cả lịch âm và lịch dương.
2. **Xem lá số:** Kiểm tra các trụ, tàng can và biểu đồ ngũ hành tại màn hình chính.
3. **Cấu hình AI:** Mở **Cài đặt** để nhập API Key của Gemini.
4. **Luận giải:** Nhấn **"Gửi AI"** để nhận bài phân tích chi tiết.

---

## 📂 Cấu trúc thư mục

- `app/src/main/java/com/skul9x/battu/core`: Engine tính toán (Lệnh tháng, Trường sinh, Thần sát).
- `app/src/main/java/com/skul9x/battu/data`: Model dữ liệu và cơ sở dữ liệu Room.
- `app/src/main/java/com/skul9x/battu/ai`: Logic tích hợp Gemini và Builder Prompt.
- `app/src/main/java/com/skul9x/battu/ui`: Giao diện Jetpack Compose.
- `.brain`: Thư mục lưu trữ tri thức và tiến độ của trợ lý AI Antigravity.

---

## 📑 Thông tin bổ sung
- Dự án sử dụng **Antigravity Workflow Framework (AWF)** để quản lý quy trình phát triển.
- Tính điểm ngũ hành được tối ưu hóa theo tiêu chuẩn Tử Bình chuyên nghiệp (**Accuracy Audit v2.3.0**).
- Đang triển khai **Phase 05: Room Database UI Sync** để lưu trữ lịch sử lá số.

---

## 📄 Bản quyền
Copyright 2026 Nguyễn Duy Trường

---
*Cập nhật lần cuối: 26/03/2026*
