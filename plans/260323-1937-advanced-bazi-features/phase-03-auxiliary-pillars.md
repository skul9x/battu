# Phase 03: Thai Nguyên + Mệnh Cung (Trụ Phụ)
Status: ✅ Complete
Dependencies: Phase 02

## Objective
Thêm 2 trụ phụ (trụ thứ 5 và thứ 6) vào lá số:
1. **Thai Nguyên (胎元)** — Trụ thụ thai, phản ánh sức khỏe bẩm sinh
2. **Mệnh Cung (命宮)** — Cuộc đời thu nhỏ, ảnh hưởng đến hậu vận

## Lý thuyết Thai Nguyên

### Công thức
- **Can Thai Nguyên** = Can Tháng sinh **tiến 1 vị trí**
- **Chi Thai Nguyên** = Chi Tháng sinh **tiến 3 vị trí**

### Ví dụ (lá số Ngọc)
- Tháng sinh: **Canh Tuất** (Can idx=6, Chi idx=10)
- Can Thai Nguyên: Canh → tiến 1 → **Tân** (idx 7) ✅
- Chi Thai Nguyên: Tuất → tiến 3 → **Sửu** (idx 1, vì (10+3)%12=1) ✅
- Kết quả: **Tân Sửu** — Trùng khớp ảnh tham chiếu ✅

## Lý thuyết Mệnh Cung

### Công thức (Chuẩn Bát Tự)
Lấy số 26 trừ cho tổng (Tháng sinh + Giờ sinh), trong đó Chi quy ước Dần=1, Mão=2, ..., Sửu=12.
`Chi Mệnh Cung = 26 - (MonthIdx + HourIdx)`
Nếu kết quả > 12 thì trừ 12.

Can Mệnh Cung tính bằng **Ngũ Hổ Độn** dựa theo Can Năm sinh và Chi Mệnh Cung vừa tìm được.

### Ví dụ kiểm chứng (Lá số Ngọc)
- Năm Nhâm Thân, Tháng Canh Tuất, Giờ Kỷ Dậu.
- MonthIdx (Tuất) = 9.
- HourIdx (Dậu) = 8.
- 26 - (9 + 8) = 9 -> **Tuất**.
- Ngũ Hổ Độn cho năm Nhâm tại Tuất -> **Canh**.
- Kết quả: **Canh Tuất** — Trùng khớp ảnh tham chiếu ✅

## Implementation Steps

1. [x] **Models.kt** — Thêm data class `AuxiliaryPillar`
2. [x] **BaZiLogic.kt** — Thêm `calculateFetalOrigin()`
3. [x] **BaZiLogic.kt** — Thêm `calculateLifePalace()`
4. [x] **PromptBuilder.kt** — Thêm `auxiliary_pillars` section
5. [x] **Test** — Verify Thai Nguyên Ngọc = Tân Sửu, Mệnh Cung = Canh Tuất ✅

## Files to Modify
- `app/src/main/java/com/skul9x/battu/data/Models.kt`
- `app/src/main/java/com/skul9x/battu/core/BaZiLogic.kt`
- `app/src/main/java/com/skul9x/battu/ai/PromptBuilder.kt`

## Test Criteria
- [x] Thai Nguyên: Tháng Canh Tuất → Tân Sửu ✅
- [x] Mệnh Cung: Ngọc → Canh Tuất ✅
- [x] JSON output chứa `auxiliary_pillars` đầy đủ ✅

## Notes
- Đã verify công thức 26 - (M+H) với Dần=1 mapping. Hoạt động chính xác cho các lá số mẫu.
- Cần dọn dẹp file test sau khi hoàn tất.

---
Next Phase: [phase-04-shen-sha.md](./phase-04-shen-sha.md)
