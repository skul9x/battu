# 🧧 Bát Tự AI (BatTu) - Hệ Thống Luận Giải Mệnh Lý Chuyên Sâu

![Version](https://img.shields.io/badge/version-2.1.0-blue)
![Platform](https://img.shields.io/badge/platform-Android-green)
![Kotlin](https://img.shields.io/badge/kotlin-2.0.20-purple)

**Bát Tự AI** là ứng dụng Android hiện đại được thiết kế để tính toán và luận giải lá số Bát Tự (Tứ Trụ) với độ chính xác tuyệt đối. Ứng dụng kết hợp giữa kinh nghiệm Mệnh lý học cổ truyền (Tử Bình Chân Thuyên, Trích Thiên Tùy) và sức mạnh trí tuệ nhân tạo (Google Gemini) để cung cấp những bản luận giải chuyên sâu về vận mệnh, tính cách và sự nghiệp.

---

## ✨ Tính Năng Nổi Bật (Precision Engine v2.1)

### 🧩 Engine Tính Toán Độ Chính Xác Cao
- **Lập Lá Số Tứ Trụ:** Tính toán chính xác Can Chi của Năm, Tháng, Ngày, Giờ (Midpoint traditional hour-based calculation).
- **Thập Thần & Tàng Can chuyên sâu:** Phân tích trọng số Tàng Can (60% - 30% - 10%) và Ten Gods chính xác cho cả 8 vị trí.
- **Vòng Trường Sinh (12 Life Stages):** Áp dụng đầy đủ quy tắc Âm nghịch - Dương thuận cho 10 Thiên Can trên tất cả các trụ.
- **Thần Sát (Shen Sha):** Phân tích hệ thống Thần Sát đa tầng (Thiên Ất Quý Nhân, Văn Xương, Lộc Tồn, Kình Dương, v.v.) dựa trên cả Năm và Ngày sinh.
- **Tuần Không (Void Branches):** Tính toán chính xác Niên Không và Nhật Không (Void of Day/Year).
- **Thập Thần Tàng Can chuyên sâu:** Thập Thần được tính cho toàn bộ các Tàng Can (Bản khí, Trung khí, Dư khí).
- **Tương Tác Địa Chi Động:** Nhận diện đầy đủ Lục Hợp, Lục Xung, Lục Hại, Tam Hợp, **Bán Tam Hợp**, **Củng Hợp** và **Phục Ngâm**.
- **Đại Vận (Luck Pillars) Tiết Khí:** Tính mốc khởi vận chính xác đến từng ngày và tháng lẻ dựa trên khoảng cách tới Tiết khí (quy tắc 3 ngày = 1 năm).

### 🤖 Trí Tuệ Nhân Tạo (Gemini AI)
- Luận giải lá số dựa trên cấu trúc JSON đa tầng.
- Tự động xoay vòng (rotation) API Key và hỗ trợ model fallback.
- Phân tích đa chiều: Nhật Chủ, Dụng Thần, Hỉ Thần, Kỵ Thần thông qua 3 tầng logic (Căn bản, Biến hóa, Thời gian).

### 📱 Tiện Ích Người Dùng
- Lưu trữ lịch sử lá số ngoại tuyến (Offline History) với Room Database.
- Chia sẻ kết quả luận giải thông qua Android Intent.
- Giao diện Modern Android (Material 3 + Jetpack Compose) hỗ trợ Dark Mode.

---

## 🏗️ Cấu Trúc Dự Án

```text
com.skul9x.battu/
├── core/                    # Engine tính toán cốt lõi (BaZiLogic, LunarConverter)
├── data/                    # Models dữ liệu và Local Storage (Room, DataStore)
├── ai/                      # Tích hợp Gemini API và PromptBuilder
├── ui/                      # Giao diện Jetpack Compose (Screens, Components, Theme)
│   ├── screens/             # Input, Chart, Result, Settings, History
│   └── components/          # PillarCard, ElementBalanceChart, MarkdownText
└── assets/                  # Dữ liệu Tiết khí (Solar Terms) từ 1900 - 2049
```

---

## 🛠️ Hướng Dẫn Cài Đặt

1. **Clone Repository:**
   ```bash
   git clone https://github.com/skul9x/battu.git
   ```
2. **Mở dự án:**
   Mở thư mục bằng **Android Studio (Ladybug hoặc mới hơn)**.
3. **Cấu hình API Key:**
   - Vào màn hình **Cài đặt** trong App.
   - Thêm API Key Google Gemini (lấy từ AI Studio).
4. **Build & Run:**
   Build dự án với Gradle 8.x + JDK 17.

---

## 📝 Yêu Cầu Hệ Thống
- Android 7.0 (API 24) trở lên.
- Kết nối internet (để sử dụng tính năng luận giải AI).

---

## ⚖️ Bản Quyền (Copyright)

Copyright 2026 Nguyễn Duy Trường. 

Mọi quyền được bảo lưu. Dự án được phát triển và duy trì bởi Nguyễn Duy Trường với sự hỗ trợ của Antigravity Workflow Framework (AWF).

---

*Cập nhật lần cuối: 23/03/2026*
