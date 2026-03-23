# 🎨 DESIGN: Phase 02 Fix - Bazi Basic Logic

Ngày tạo: 2026-03-23
Trạng thái: 🟢 Implementing

## 1. Dữ liệu bổ sung (BaZiConstants.kt)

### 1.1. Vòng Trường Sinh (10 Can)
| Can | Trường Sinh | Mộc Dục | Quan Đới | Lâm Quan | Đế Vượng | Suy | Bệnh | Tử | Mộ | Tuyệt | Thai | Dưỡng |
|:---|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|:---:|
| Giáp | Hợi | Tý | Sửu | Dần | Mão | Thìn | Tỵ | Ngọ | Mùi | Thân | Dậu | Tuất |
| Ất | Ngọ | Tỵ | Thìn | Mão | Dần | Sửu | Tý | Hợi | Tuất | Dậu | Thân | Mùi |
| Bính | Dần | Mão | Thìn | Tỵ | Ngọ | Mùi | Thân | Dậu | Tuất | Hợi | Tý | Sửu |
| Đinh | Dậu | Thân | Mùi | Ngọ | Tỵ | Thìn | Mão | Dần | Sửu | Tý | Hợi | Tuất |
| Mậu | Dần | Mão | Thìn | Tỵ | Ngọ | Mùi | Thân | Dậu | Tuất | Hợi | Tý | Sửu |
| Kỷ | Dậu | Thân | Mùi | Ngọ | Tỵ | Thìn | Mão | Dần | Sửu | Tý | Hợi | Tuất |
| Canh | Tỵ | Ngọ | Mùi | Thân | Dậu | Tuất | Hợi | Tý | Sửu | Dần | Mão | Thìn |
| Tân | Tý | Hợi | Tuất | Dậu | Thân | Mùi | Ngọ | Tỵ | Thìn | Mão | Dần | Sửu |
| Nhâm | Thân | Dậu | Tuất | L.Quan | Đế Vượng | Suy | Bệnh | Tử | Mộ | Tuyệt | Thai | Dưỡng |
| Quý | Mão | Dần | Sửu | Tý | Hợi | Tuất | Dậu | Thân | Mùi | Ngọ | Tỵ | Thìn |

*Lưu ý: Mậu hành theo Bính, Kỷ hành theo Đinh.*

### 1.2. Trạng thái Vượng/Tướng/Hưu/Tù/Tử theo Mùa
| Mùa | Can Vương | Can Tướng | Can Hưu | Can Tù | Can Tử |
|:---|:---:|:---:|:---:|:---:|:---:|
| **Xuân (Dần/Mão)** | Mộc | Hỏa | Thủy | Thổ | Kim |
| **Hạ (Tỵ/Ngọ)** | Hỏa | Thổ | Mộc | Kim | Thủy |
| **Thu (Thân/Dậu)** | Kim | Thủy | Thổ | Hỏa | Mộc |
| **Đông (Hợi/Tý)** | Thủy | Mộc | Kim | Thổ | Hỏa |
| **Tứ Quý (Thìn/Tuất/Sửu/Mùi)** | Thổ | Kim | Hỏa | Mộc | Thủy |

## 2. Cập nhật cấu trúc (Models.kt)

### 2.1 Pillar
- Thêm `lifeStage: String?` (Trạng thái Trường sinh so với Nhật chủ)

### 2.2 BaZiData
- Thêm `season: String` (Mùa sinh: Xuân/Hạ/Thu/Đông/Tứ Quý)
- Thêm `dayMasterStrength: String` (Vượng/Tướng/Hưu/Tù/Tử)

## 3. Thuật toán tính điểm (Weighted Balance)
- **Tổng 1 trụ = 100 điểm**
- **Thiên Can:** 40 điểm.
- **Tàng Can (Địa Chi):** 60 điểm (nhân % Bản/Trung/Dư Khí).
- **Nạp Âm:** Thưởng +10 điểm cho hành trùng.
- **Cách cộng:** Duyệt qua 4 trụ, cộng dồn điểm của từng hành.

## 4. Test Cases
- TC-08: Kiểm tra Trường sinh Ất Mộc tại Hợi (Expected: Tử)
- TC-09: Kiểm tra Nhật chủ Giáp Mộc tháng Dần (Expected: Vượng)
- TC-10: Kiểm tra điểm số chi Thìn (Expected: Thổ +36, Mộc +18, Thủy +6)
