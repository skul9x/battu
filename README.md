# 🧧 Bát Tự AI (BatTu) - Hệ Thống Luận Giải Mệnh Lý Chuyên Sâu

![Phiên bản](https://img.shields.io/badge/version-2.2.0-blue)
![Nền tảng](https://img.shields.io/badge/platform-Android-green)
![Ngôn ngữ](https://img.shields.io/badge/kotlin-2.0.20-purple)

**Bát Tự AI** là một ứng dụng Android hiện đại được thiết kế để tính toán và luận giải lá số Bát Tự (Tứ Trụ) với độ chính xác cao nhất. Ứng dụng kết hợp tinh hoa Mệnh Lý học cổ truyền (Tử Bình Chân Thuyên, Trích Thiên Tùy) với sức mạnh của trí tuệ nhân tạo (Google Gemini) để mang đến những bản luận giải chuyên sâu về vận mệnh, tính cách và sự nghiệp.

---

## ✨ Tính Năng Nổi Bật (Precision Engine v2.2)

### 🧩 Bộ Máy Tính Toán Chính Xác
*   **Lập Lá Số Tứ Trụ:** Xác định chính xác Can Chi của Năm, Tháng, Ngày, Giờ (Dựa trên Tiết Khí thực tế).
*   **Thập Thần & Tàng Can Chuyên Sâu:** Phân tích trọng số Tàng Can (60% - 30% - 10%) và Ten Gods chính xác cho cả 8 vị trí.
*   **Vòng Trường Sinh (12 Trạng Thái):** Áp dụng quy tắc Âm nghịch - Dương thuận cho 10 Thiên Can trên tất cả các trụ.
*   **Hệ Thống Thần Sát Đa Tầng:** Phân tích Văn Xương, Kiếp Sát, Thập Ác Đại Bại, Đào Hoa, Thiên Y, Hồng Loan, Thiên Hỉ... dựa trên cả Trụ Năm và Trụ Ngày.
*   **Tuần Không (Không Vong):** Tính toán chính xác Niên Không và Nhật Không.
*   **Trụ Phụ (Auxiliary Pillars):** Tích hợp **Thai Nguyên** và **Mệnh Cung** giúp bổ trợ luận đoán tiền vận và hậu vận.
*   **Tương Tác Địa Chi Động:** Nhận diện đầy đủ Lục Hợp, Lục Xung, Lục Hại, Tam Hợp, **Bán Tam Hợp**, **Củng Hợp** và **Phục Ngâm**.
*   **Đại Vận & Lưu Niên:** Tính mốc khởi vận chính xác (3 ngày = 1 năm) và dự báo chi tiết **Lưu Niên (Hành năm)** cho 10 năm hiện tại.

### 🤖 Trí Tuệ Nhân Tạo (Gemini AI)
*   Luận giải lá số dựa trên cấu trúc dữ liệu JSON đa tầng.
*   Tự động xoay vòng API Key và hỗ trợ chuyển đổi model linh hoạt.
*   Phân tích đa chiều về Nhật Chủ, Dụng Thần, Hỉ Thần thông qua 3 tầng logic: Căn Bản, Biến Hóa và Thời Gian.

### 📱 Tiện Ích Người Dùng
*   Lưu trữ lịch sử lá số ngoại tuyến (Offline) bằng Room Database.
*   Chia sẻ kết quả luận giải thông qua Android Intent.
*   Giao diện Modern Android (Material 3 + Jetpack Compose) hỗ trợ Chế độ tối (Dark Mode).

---

## 🏗️ Cấu Trúc Thư Mục

```text
com.skul9x.battu/
├── core/                    # Bộ máy tính toán cốt lõi (BaZiLogic, LunarConverter)
├── data/                    # Định nghĩa dữ liệu và Lưu trữ (Room, DataStore)
├── ai/                      # Tích hợp Gemini API và Trình xây dựng Prompt
├── ui/                      # Giao diện người dùng Jetpack Compose
│   ├── screens/             # Màn hình: Nhập liệu, Lá số, Kết quả, Cài đặt, Lịch sử
│   └── components/          # Thành phần: Trụ số, Biểu đồ ngũ hành, Hiển thị Markdown
└── assets/                  # Dữ liệu Tiết khí (Solar Terms) từ năm 1900 - 2049
```

---

## 🛠️ Hướng Dẫn Cài Đặt

1.  **Clone mã nguồn:**
    ```bash
    git clone https://github.com/skul9x/battu.git
    ```
2.  **Mở dự án:**
    Sử dụng **Android Studio (Ladybug hoặc mới hơn)**.
3.  **Cấu hình API Key:**
    - Truy cập màn hình **Cài đặt** trong ứng dụng.
    - Thêm API Key của Google Gemini (Lấy từ Google AI Studio).
4.  **Xây dựng và Chạy:**
    Build dự án với Gradle 8.x và JDK 21 (Đã cấu hình trong `gradle.properties`).

---

## 📖 Cách Sử Dụng

1.  Tại màn hình chính, nhập **Họ tên, Ngày tháng năm sinh (Dương lịch)** và **Giờ sinh**.
2.  Nhấn nút **Xem Lá Số** để hệ thống tính toán Tứ Trụ, Thần Sát và Đại Vận.
3.  Nhấn nút **Luận Giải AI** để gửi dữ liệu cho AI Gemini và nhận bản bình giải chuyên sâu.
4.  Bạn có thể lưu lá số vào lịch sử hoặc chia sẻ cho bạn bè.

---

## ⚖️ Bản Quyền (Copyright)

Copyright 2026 Nguyễn Duy Trường. 

Mọi quyền được bảo lưu. Dự án được phát triển và duy trì bởi Nguyễn Duy Trường với sự hỗ trợ của Antigravity Workflow Framework (AWF).

---

*Cập nhật lần cuối: 23/03/2026*
