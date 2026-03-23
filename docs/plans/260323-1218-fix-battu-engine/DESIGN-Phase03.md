# 🎨 DESIGN: Bát Tự AI - Phase 03: Advanced Bazi Rules

Ngày tạo: 2026-03-23
Dựa trên: [HANDOVER.md](file:///home/skul9x/Desktop/Test_code/BatTu/.brain/handover.md) & Expert Feedback.
Kiến trúc sư: Minh (Antigravity Solution Designer)

---

## 🎭 LỜI DẪN CỦA KIẾN TRÚC SƯ
"Chào anh! Sau khi đã hoàn thiện bộ khung cơ bản (Nạp Âm, Tàng Can, Trường Sinh) ở Phase 02, chúng ta sẽ bước vào phần 'linh hồn' của Bát Tự: **Advanced Rules**. 

Ở phần này, app sẽ không chỉ liệt kê các trụ mà còn biết 'nhìn' xem các trụ đó **Hợp, Xung, Hình, Hại** với nhau thế nào, Nhật Chủ có được **Quý Nhân** phù trợ hay gặp **Sát tinh** không, và quan trọng nhất là tính toán **Đại Vận** - dòng chảy 10 năm của cuộc đời.

Em đã tra cứu các công thức chuẩn nhất từ Google để đảm bảo logic của chúng ta chính xác ở mức chuyên gia."

---

## 1. CÁCH LƯU THÔNG TIN BỔ SUNG (Models)

Chúng ta cần thêm 3 "ngăn chứa" mới trong `Models.kt` để lưu các kết quả tính toán nâng cao:

### 1.1. Thần Sát (Shen Sha)
Lưu các sao tốt/xấu chiếu vào từng trụ.
*   **Tên:** Thiên Ất, Lộc Tồn, Kình Dương, Dịch Mã...
*   **Vị trí:** Trụ nào bị chiếu (Năm/Tháng/Ngày/Giờ).

### 1.2. Tương Tác (Interactions)
Lưu mối quan hệ giữa các trụ.
*   **Loại:** Lục Hợp, Tam Hợp, Lục Xung, Lục Hại, Tam Hình...
*   **Các trụ liên quan:** Ví dụ: Trụ Năm xung Trụ Ngày.

### 1.3. Đại Vận (Luck Pillars)
Lưu chuỗi 10 năm cuộc đời.
*   **Số tuổi bắt đầu/kết thúc.**
*   **Can Chi của vận đó.**

---

## 2. THIẾT KẾ LOGIC CHI TIẾT

### 2.1. Thần Sát (Lookup Tables trong BaZiConstants)
Em sẽ thiết lập các bảng tra cứu (Map) dựa trên Nhật Can (Can Ngày) hoặc Niên Chi (Chi Năm):

| Thần Sát | Căn cứ tra cứu | Công thức tóm tắt |
|----------|---------------|-------------------|
| **Thiên Ất Quý Nhân** | Can Ngày/Năm | Giáp/Mậu -> Sửu/Mùi; Ất/Kỷ -> Tý/Thân... |
| **Lộc Tồn** | Can Ngày | Giáp -> Dần; Ất -> Mão; Bính/Mậu -> Tỵ... |
| **Kình Dương** | Can Ngày | Vị trí Đế Vượng (trước Lộc 1 cung). |
| **Dịch Mã** | Chi Năm/Ngày | Dần-Ngọ-Tuất -> Thân; Thân-Tý-Thìn -> Dần... |
| **Hoa Cái** | Chi Năm/Ngày | Dần-Ngọ-Tuất -> Tuất; Thân-Tý-Thìn -> Thìn... |

### 2.2. Hợp/Xung/Hình/Hại (Interaction Engine)
Bộ lọc này sẽ quét qua 4 trụ và phát hiện các cặp quan hệ:
*   **Thiên Can:** 5 Hợp (Giáp-Kỷ, Ất-Canh...), 4 Xung (Giáp-Canh, Ất-Tân...).
*   **Địa Chi:** 
    *   **Lục Hợp:** Tý-Sửu, Dần-Hợi...
    *   **Tam Hợp:** Thân-Tý-Thìn, Dần-Ngọ-Tuất...
    *   **Lục Xung:** Tý-Ngọ, Sửu-Mùi...
    *   **Lục Hại:** Tý-Mùi, Sửu-Ngọ...
    *   **Tam Hình:** Dần-Tỵ-Thân, Mùi-Sửu-Tuất...

### 2.3. Đại Vận (Luck Pillar Engine)
Đây là phần phức tạp nhất, cần độ chính xác cao:
1.  **Hướng đi (Direction):**
    *   Nam sinh năm Dương / Nữ sinh năm Âm: **Thuận hành**.
    *   Nam sinh năm Âm / Nữ sinh năm Dương: **Nghịch hành**.
2.  **Tuổi khởi vận:** 
    *   Tính số ngày từ lúc sinh đến Tiết Khí kế tiếp (nếu Thuận) hoặc trước đó (nếu Nghịch).
    *   `Tuổi = Số ngày / 3`.
3.  **Lập chuỗi:** Bắt đầu từ Trụ Tháng, tiến/lùi 1 bước cho mỗi 10 năm.

---

## 3. LUỒNG HOẠT ĐỘNG (User Journey)

1.  **Tính toán:** Sau khi có 4 trụ cơ bản -> Quét Thần Sát -> Quét Tương Tác -> Tính Đại Vận.
2.  **Kết quả:** Hiển thị danh sách Thần Sát (ví dụ: "Trụ Năm có Thiên Ất Quý Nhân").
3.  **Cảnh báo:** Nếu có Xung/Hình (ví dụ: "Trụ Năm và Trụ Tháng Lục Xung").
4.  **Dòng thời gian:** Hiển thị 10 đại vận (vị trí hiển thị sẽ thiết kế ở Phase 04).

---

## 4. CHUẨN BỊ KIỂM TRA (Test Cases)

Để đảm bảo logic Advanced này chạy đúng, em thiết kế 5 bài test tiếp theo:

### TC-11: Thần Sát - Quý Nhân & Lộc Tồn
*   **Given:** Nam, sinh 23/03/2026 12:00 (Can Ngày: Bính, Chi Năm: Ngọ).
*   **When:** Tính Bát Tự.
*   **Then:** 
    *   ✓ Có Lộc Tồn tại Tỵ (nếu có chi Tỵ).
    *   ✓ Có Thiên Ất Quý Nhân tại Hợi/Dậu (nếu có chi đó).

### TC-12: Sự Tương Tác - Lục Xung
*   **Given:** Lá số có Chi Ngày là Tý, Chi Giờ là Ngọ.
*   **When:** Quét Interactions.
*   **Then:** ✓ Phát hiện "Tý - Ngọ Lục Xung" ở Trụ Ngày và Trụ Giờ.

### TC-13: Đại Vận - Hướng Thuận
*   **Given:** Nam, sinh năm Giáp (Dương), Trụ Tháng là Đinh Mão.
*   **When:** Tính Đại Vận.
*   **Then:** ✓ Đại vận 1 phải là Mậu Thìn (tiến lên từ Đinh Mão).

### TC-14: Đại Vận - Hướng Nghịch
*   **Given:** Nữ, sinh năm Giáp (Dương), Trụ Tháng là Đinh Mão.
*   **When:** Tính Đại Vận.
*   **Then:** ✓ Đại vận 1 phải là Bính Dần (lùi lại từ Đinh Mão).

### TC-15: Tuổi Khởi Vận
*   **Given:** Khoảng cách đến Tiết Khí là 18 ngày.
*   **When:** Tính tuổi khởi vận.
*   **Then:** ✓ Phải là 6 tuổi (18 / 3 = 6).

---

"Anh thấy bản thiết kế này đã đủ sâu chưa? Nếu anh OK, em sẽ bắt đầu cập nhật Models và Constants ngay!"

➡️ **Tiếp theo:**
1️⃣ Cập nhật `Models.kt` và `BaZiConstants.kt` -> Gõ `/code`
2️⃣ Xem lại logic Đại Vận?
3️⃣ Cần add thêm Thần Sát nào khác không? (Em đang list 5 cái phổ biến nhất)
