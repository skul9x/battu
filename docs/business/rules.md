# Bazi Business Rules

Dài liệu này ghi lại các quy tắc thuật lý được áp dụng trong ứng dụng Bát Tự AI.

## 1. Lục Thập Hoa Giáp (Nạp Âm)
- Mỗi cặp Thiên Can - Địa Chi (VD: Nhâm Thân) ứng với một hành Nạp Âm cụ thể.
- Có tổng cộng 60 cặp phối hợp tạo thành 30 hành Nạp Âm (mỗi hành có 2 cặp).
- **Quy tắc:** Sử dụng bảng tra cứu tĩnh trong `BaZiConstants.NAP_AM_TABLE`.

## 2. Tàng Can (Hidden Stems)
- Địa Chi không chỉ mang một hành đơn thuần mà chứa các Thiên Can ẩn tàng bên trong.
- **Phân loại:**
  - **Bản Khí (Main):** Hành chính của Địa Chi, chiếm tỷ trọng cao nhất (60-100%).
  - **Trung Khí (Secondary):** Hành chiếm tỷ trọng trung bình (~30%).
  - **Dư Khí (Residual):** Hành chiếm tỷ trọng thấp nhất (~10%).
- **Tỷ trọng áp dụng:**
  - Tý, Mão, Dậu (Chuyên khí): 100% Bản khí.
  - Ngọ: Bản khí (Kỷ) và Trung khí (Đinh) - quy tắc đặc biệt.
  - Các chi khác: Theo bảng tra trong `BaZiConstants.HIDDEN_STEMS_TABLE`.

## 3. Thập Thần (Ten Gods)
- Thập Thần được xác định dựa trên mối quan hệ giữa **Nhật Chủ (Thiên Can ngày sinh)** và các Can khác trong lá số.
- **Quy tắc phối hợp:**
  - Cùng hành, cùng âm dương: **Tỷ Kiên**
  - Cùng hành, khác âm dương: **Kiếp Tài**
  - Nhật Chủ sinh (cùng âm dương): **Thực Thần**
  - Nhật Chủ sinh (khác âm dương): **Thương Quan**
  - Nhật Chủ khắc (cùng âm dương): **Thiên Tài**
  - Nhật Chủ khắc (khác âm dương): **Chính Tài**
  - Khắc Nhật Chủ (cùng âm dương): **Thất Sát**
  - Khắc Nhật Chủ (khác âm dương): **Chính Quan**
  - Sinh Nhật Chủ (cùng âm dương): **Thiên Ấn**
  - Sinh Nhật Chủ (khác âm dương): **Chính Ấn**
- **Áp dụng cho Địa Chi:** Thập thần của Địa Chi được tính dựa trên **Bản khí (Main Hidden Stem)** của Chi đó.

## 4. Cân bằng Ngũ Hành (Element Balance)
- Điểm số Ngũ hành được tính tổng từ:
  - Thiên Can của 4 trụ: Mỗi can đóng góp 40 đơn vị.
  - Tàng Can của 4 trụ: Tổng đóng góp của 1 chi là 60 đơn vị, chia theo tỷ lệ phần trăm của các Tàng Can bên trong.
- **Công thức:** `Điểm = (Số Thiên Can gốc * 40) + Σ(Tỷ lệ Tàng Can * 60)`

## 5. Đại Vận (Luck Pillars)
- **Quy tắc tính tuổi khởi vận:** Sử dụng quy tắc **3 ngày = 1 năm, 6 giờ = 1 tháng, 12 phút = 1 ngày**.
- Tính từ thời điểm sinh (Solar Time) đến thời điểm chuyển Tiết Khí (Lập Xuân, Vũ Thủy...) kế tiếp hoặc trước đó (tùy theo tính chất Âm Nam/Dương Nữ...).
- **Fix lỗi:** Reset Calendar giây và mili giây về 0 để tính toán khoảng cách Tiết khí chính xác nhất.

## 6. Tương Tác Địa Chi (Interactions)
- Hệ thống tự động nhận diện các quan hệ giữa các trụ:
  - **Lục Xung, Lục Hợp, Lục Hại.**
  - **Tam Hợp (VD: Thân-Tý-Thìn).**
  - **Bán Tam Hợp (VD: Thân-Tý, Tý-Thìn):** Chỉ tính điểm bonus khi chi Tháng là hành trung tâm (Tý, Ngọ, Mão, Dậu).
  - **Củng Hợp (VD: Thân-Thìn):** Nhận diện lực lượng ngầm tiềm năng.

## 7. Thần Sát (Shen Sha)
- **Thiên Ất Quý Nhân:** Phân tách rõ ràng Quý nhân theo **Can Năm** (người bảo hộ từ gia đình) và **Can Ngày** (quý nhân cho cá nhân).
- **Phân loại Cát/Hung:** Giúp AI tập trung phân tích đúng trọng tâm các sao ảnh hưởng lớn tới mệnh cục.

---
*Cập nhật lần cuối: 2026-03-23*
