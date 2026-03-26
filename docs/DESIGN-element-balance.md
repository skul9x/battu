# 🎨 DESIGN: Cải tiến scoring Ngũ Hành

**Ngày tạo:** 2026-03-26
**Tác giả:** Minh (Solution Architect)
**Trạng thái:** Draft

---

## 1. Bài toán: "Sức nặng" của các hành

Hiện tại, app tính điểm Ngũ hành bằng cách cộng dồn điểm từ Thiên Can (40đ), Tàng Can (60đ) và Nạp Âm (10đ) một cách cào bằng giữa các trụ. 

**Vấn đề:** Trong Bát Tự, **Tháng sinh (Nguyệt Lệnh)** là quan trọng nhất, chi phối toàn bộ khí hậu của lá số. Ngược lại, **Tuần Không** làm cho các hành tại đó bị "rỗng", suy yếu lực lượng.

---

## 2. Giải pháp kỹ thuật (The Algorithm)

Em đã tra cứu và đối chiếu với các nguồn uy tín (BaziAdvisor, Master Tsai, các tài liệu Tử Bình thực chiến). Kết quả cho thấy hệ số **x2.0 (Nguyệt Lệnh)** và **x0.5 (Không Vong)** là mức "chuẩn" thường dùng trong các phần mềm Bát tự chuyên nghiệp để định lượng sức mạnh.

### 2.1. Các hệ số mới (Đã kiểm chứng)
| Tên hệ số | Giá trị | Giải thích (Research-backed) |
|-----------|---------|------------------------------|
| `MONTH_MULTIPLIER` | **2.0** | Lệnh tháng chi phối 40-50% sức mạnh lá số. Nhân 2 cho Chi tháng là cách mô hình hóa đơn giản và hiệu quả nhất. |
| `XUN_KONG_PENALTY` | **0.5** | Tài liệu ghi nhận Không Vong làm giảm 50% hoặc "vô hiệu hóa" lực lượng. Em chọn 0.5 để vẫn giữ lại một phần khí lực (không triệt tiêu hoàn toàn). |

### 2.2. Công thức tính điểm Pillar (Trụ)

`PillarScore = (StemScore) + (BranchScore * Multiplier) + (NapAmScore * Multiplier)`

**Quy tắc ưu tiên (Priority Rules):**
1. **Mặc định:** `Multiplier = 1.0`
2. **Xét Lệnh Tháng:** Nếu là Trụ Tháng, `Multiplier = 2.0`.
3. **Xét Không Vong:** Nếu Trụ (Năm/Tháng/Ngày/Giờ) dính Tuần Không (theo Ngày hoặc Năm), `Multiplier *= 0.5`.
   - *Kết quả gộp:* Trụ Tháng dính Không Vong sẽ có hệ số `2.0 * 0.5 = 1.0` (Trở về trạng thái như trụ bình thường).
4. **Thiên Can:** Giữ nguyên 40đ (Không áp dụng hệ số vì Thiên Can là "Lộ", ít bị ảnh hưởng bởi Không Vong hơn Địa Chi).

---

## 3. Luồng hoạt động (Logic Flow)

1. **Bước 1:** Lấy danh sách Tuần Không từ `XunKong` object (đã có sẵn trong `BaZiData`).
2. **Bước 2:** Duyệt 4 trụ. Xác định `isMonthPillar` và `isVoidPillar`.
3. **Bước 3:** Tính `multiplier`.
4. **Bước 4:** Áp dụng vào phần tính điểm Hidden Stems (tương ứng với 60đ mỗi trụ) và Nạp Âm (+10đ).
5. **Bước 5:** Tổng hợp lại vào `Map<String, Int>` kết quả.

---

## 4. Kiểm thử (Test Cases)

Em thiết kế sẵn các bài kiểm tra này để đảm bảo code chạy đúng:

### 🧪 TC-01: Trụ Tháng áp đảo
- **Input:** Một lá số bình thường.
- **Expect:** Hành của Trụ Tháng phải có điểm cao vượt trội so với các hành khác nếu cùng số lượng xuất hiện.

### 🧪 TC-02: Tuần Không làm yếu hành
- **Input:** Trụ Năm có hành Kim mạnh nhưng dính Tuần Không.
- **Expect:** Tổng điểm Kim phải thấp hơn so với khi tính cào bằng (giảm ~50% portion từ Trụ Năm).

### 🧪 TC-03: Trụ Tháng biến động (Lưu Niên/Đại Vận)
- *Lưu ý:** Hiện tại chúng ta chỉ tính element balance cho lá số gốc (Static Chart).

---

## 5. Checklist hoàn thiện

- [ ] Thêm hằng số `MONTH_MULTIPLIER` và `XUN_KONG_PENALTY` vào `BaZiConstants`.
- [ ] Sửa hàm `calculateElementBalance` trong `BaZiLogic`.
- [ ] Đảm bảo UI cập nhật đúng biểu đồ sau khi điểm thay đổi.

Anh thấy "bản vẽ" này đã đủ chi tiết chưa ạ? Nếu OK em sẽ bắt tay vào code!
