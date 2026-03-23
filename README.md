# Bát Tự (BatTu) - Tra cứu & Luận giải Tứ Trụ bằng AI

Ứng dụng Android hiện đại được xây dựng bằng Jetpack Compose, cho phép người dùng lập lá số Bát Tự (Tứ Trụ) chính xác và cung cấp khả năng luận giải thông minh thông qua tích hợp Trí tuệ nhân tạo (AI - Gemini).

## 🌟 Tính năng chính

- **Lập lá số Tứ Trụ:** Phân tích Năm, Tháng, Ngày, Giờ sinh để xác định Thiên Can, Địa Chi.
- **Phân tích chuyên sâu:**
    - **Thập Thần:** Xác định chính xác các đại diện (Thực Thần, Thương Quan, Thiên Tài, Chính Quan, etc).
    - **Vòng Trường Sinh:** Theo dõi các giai đoạn sinh trưởng của Nhật Chủ theo từng trụ.
    - **Nạp Âm:** Xác định tính chất ngũ hành của từng cặp Thiên Can - Địa Chi.
    - **Tàng Can:** Phân tích sâu các năng lượng ẩn trong Địa Chi.
- **Hệ thống Thần Sát:** Tra cứu các thần sát phổ biến như Thiên Ất Quý Nhân, Hoa Cái, Dịch Mã, Khôi Cương, v.v.
- **Đại Vận & Lưu Niên:** Cung cấp thông tin về các chu kỳ vận hạn 10 năm và diễn biến từng năm.
- **Ngũ Hành Balance:** Biểu đồ hóa sự cân bằng của 5 yếu tố Kim, Mộc, Thủy, Hỏa, Thổ.
- **Luận giải AI:** Tự động tạo Prompt cấu trúc JSON chuyên sâu để gửi cho AI (Gemini) thực hiện phân tích mệnh lý chi tiết.

## 🛠 Công nghệ sử dụng

- **Ngôn ngữ:** Kotlin
- **UI Framework:** Jetpack Compose (Modern Android Development)
- **Kiến trúc:** MVVM (Model-View-ViewModel)
- **Dependency Injection:** Dagger Hilt
- **Local Cache:** Room Database (Lưu lịch sử xem lá số)
- **AI Integration:** Google Gemini SDK

## 📁 Cấu trúc thư mục

```text
├── app/                        # Mã nguồn chính của ứng dụng Android
│   ├── src/main/java/com/skul9x/battu/
│   │   ├── ai/                 # Xử lý Prompt và giao tiếp AI
│   │   ├── core/               # Engine tính toán Bát Tự (Logic lõi)
│   │   ├── data/               # Models, Constants, Room DB
│   │   └── ui/                 # Giao diện (Compose Components, Screens)
├── docs/                       # Tài liệu thiết kế và kế hoạch phát triển (Phases)
├── .brain/                     # Lưu trữ lịch sử AI Session (Context Knowledge)
└── CHANGELOG.md                # Nhật ký thay đổi và các phiên bản cập nhật
```

## 🚀 Hướng dẫn cài đặt

1. **Clone dự án:**
   ```bash
   git clone https://github.com/skul9x/battu.git
   ```
2. **Mở dự án:**
   Mở thư mục bằng **Android Studio (Ladybug hoặc mới hơn)**.
3. **Cài đặt API Key:**
   Tạo file `local.properties` (nếu chưa có) và thêm:
   ```properties
   GEMINI_API_KEY=YOUR_API_KEY_HERE
   ```
4. **Build & Run:**
   Đảm bảo bạn có JDK 17+ và Android SDK API 24 trở lên.

## 📖 Cách sử dụng

1. Mở ứng dụng, nhập Họ tên, Giới tính và Ngày giờ sinh.
2. Nhấn "Lập Lá Số" để xem thông tin chi tiết các trụ, thần sát và đại vận.
3. Sử dụng nút "Gửi AI" để xem phần bình giải chi tiết về sự nghiệp, tình duyên và sức khỏe.

---

### Copyright

Copyright 2026 Nguyễn Duy Trường
