# Phase 05: Lưu Niên (Annual Luck Pillars)
Status: ⬜ Pending
Dependencies: Phase 01

## Objective
Thêm Can Chi từng năm (Lưu Niên) vào trong Đại Vận. 
**Scope:** Chỉ liệt kê **10 năm trong Đại Vận hiện tại** (dựa vào tuổi) để tiết kiệm token Gemini.

## Lý thuyết Lưu Niên

### Công thức Can Chi theo năm dương lịch
```
Can index = (year - 4) % 10  → THIEN_CAN[canIdx]
Chi index = (year - 4) % 12  → DIA_CHI[chiIdx]
```

### Ví dụ kiểm chứng (đối chiếu ảnh tham chiếu hocvienlyso.org)
Đại Vận 26-35t (Đinh Mùi), Lưu Niên bắt đầu từ 2017:

| Năm | Can Chi | Tuổi | Verify |
|-----|---------|------|--------|
| 2017 | Đinh Dậu | 26t | (2017-4)%10=3→Đinh, (2017-4)%12=9→Dậu ✅ |
| 2018 | Mậu Tuất | 27t | ✅ |
| 2019 | Kỷ Hợi | 28t | ✅ |
| 2020 | Canh Tý | 29t | ✅ |
| 2021 | Tân Sửu | 30t | ✅ |
| 2022 | Nhâm Dần | 31t | ✅ |
| 2023 | Quý Mão | 32t | ✅ |
| 2024 | Giáp Thìn | 33t | ✅ |
| 2025 | Ất Tỵ | 34t | ✅ |
| 2026 | **Bính Ngọ** | **35t** | ✅ (hiện tại) |

## Xác định Đại Vận hiện tại

```kotlin
// Tuổi hiện tại = currentYear - birthYear + 1 (tuổi mụ) hoặc + 0 (tuổi tây)
// Đại Vận hiện tại = luckPillar mà startAge <= tuổi < startAge + 10

fun getCurrentLuckPillarIndex(luckPillars: List<LuckPillar>, currentAge: Int): Int {
    return luckPillars.indexOfLast { it.startAge <= currentAge }
}
```

## Implementation Steps

1. [ ] **Models.kt** — Thêm data class và field
   ```kotlin
   @Serializable
   data class AnnualPillar(
       val year: Int,
       val stem: String,
       val branch: String,
       val ageDisplay: String  // VD: "26 tuổi"
   )
   
   // Thêm vào LuckPillar:
   val annualPillars: List<AnnualPillar> = emptyList()
   ```

2. [ ] **BaZiLogic.kt** — Thêm populate Lưu Niên trong `calculateLuckPillars()`
   ```kotlin
   // Tính năm dương lịch bắt đầu Đại Vận
   val birthYear = tstYear  // Cần truyền thêm vào hàm
   val startYear = birthYear + pillarStartAge
   
   val annualPillars = (0 until 10).map { offset ->
       val year = startYear + offset
       val canIdx = ((year - 4) % 10 + 10) % 10
       val chiIdx = ((year - 4) % 12 + 12) % 12
       AnnualPillar(
           year = year,
           stem = BaZiConstants.THIEN_CAN[canIdx],
           branch = BaZiConstants.DIA_CHI[chiIdx],
           ageDisplay = "${pillarStartAge + offset} tuổi"
       )
   }
   ```

3. [ ] **PromptBuilder.kt** — Serialize `annual_pillars` (chỉ Đại Vận hiện tại)
   ```kotlin
   // Xác định Đại Vận hiện tại dựa trên tuổi
   // Chỉ output annual_pillars cho Đại Vận đó
   val currentAge = Calendar.getInstance().get(Calendar.YEAR) - birthYear
   ```

4. [ ] **Test** — Verify Lưu Niên 2026 = Bính Ngọ

## Files to Modify
- `app/src/main/java/com/skul9x/battu/data/Models.kt` — Thêm model
- `app/src/main/java/com/skul9x/battu/core/BaZiLogic.kt` — Calculate
- `app/src/main/java/com/skul9x/battu/ai/PromptBuilder.kt` — Serialize

## Test Criteria
- [ ] 2026 = Bính Ngọ
- [ ] 1992 = Nhâm Thân (năm sinh Ngọc)
- [ ] 10 Lưu Niên liên tiếp đúng Can Chi
- [ ] Chỉ xuất 10 năm trong Đại Vận hiện tại (không xuất toàn bộ)
- [ ] JSON output coherent

## Notes
- Cần truyền `birthYear` vào hàm `calculateLuckPillars`
- Trong PromptBuilder, cần logic xác định Đại Vận hiện tại
- Token Gemini: ~10 annual_pillars × ~50 chars = 500 chars → rất hợp lý

---
Next Phase: [phase-06-promptbuilder-sync.md](./phase-06-promptbuilder-sync.md)
