# Phase 04: Mở rộng Thần Sát (Shen Sha)
Status: ✅ Completed
Dependencies: Phase 01

## Objective
Bổ sung 4 sao quan trọng mà chuyên gia chỉ ra app đang thiếu:
- **Đào Hoa** (Hàm Trì) — Sức hút, duyên ngầm
- **Thiên Y** — Y lý, sức khỏe được phù trợ
- **Hồng Loan** — Hôn nhân, hỉ sự 
- **Thiên Hỉ** — Tin vui, con cái

## Công thức tra cứu

### 1. Đào Hoa (桃花) — Theo Chi Ngày/Năm

| Chi Ngày/Năm | Đào Hoa |
|:-------------|:--------|
| Thân, Tý, Thìn | **Dậu** |
| Dần, Ngọ, Tuất | **Mão** |
| Tỵ, Dậu, Sửu | **Ngọ** |
| Hợi, Mão, Mùi | **Tý** |

```kotlin
val DAO_HOA = mapOf(
    "Thân" to "Dậu", "Tý" to "Dậu", "Thìn" to "Dậu",
    "Dần" to "Mão", "Ngọ" to "Mão", "Tuất" to "Mão",
    "Tỵ" to "Ngọ", "Dậu" to "Ngọ", "Sửu" to "Ngọ",
    "Hợi" to "Tý", "Mão" to "Tý", "Mùi" to "Tý"
)
```

**Kiểm chứng Ngọc:** Chi Ngày = Thân → Đào Hoa = **Dậu** → Giờ Dậu → **Đào Hoa tại Giờ** ✅

### 2. Thiên Y (天醫) — Theo Chi Tháng

Công thức: Chi đứng TRƯỚC chi Tháng sinh (lùi 1 vị trí)

```kotlin
val THIEN_Y = mapOf(
    "Tý" to "Hợi", "Sửu" to "Tý", "Dần" to "Sửu",
    "Mão" to "Dần", "Thìn" to "Mão", "Tỵ" to "Thìn",
    "Ngọ" to "Tỵ", "Mùi" to "Ngọ", "Thân" to "Mùi",
    "Dậu" to "Thân", "Tuất" to "Dậu", "Hợi" to "Tuất"
)
```

**Kiểm chứng Ngọc:** Tháng Tuất → Thiên Y = **Dậu** → Giờ Dậu → **Thiên Y tại Giờ** ✅

### 3. Hồng Loan (紅鸞) — Theo Chi Năm

```kotlin
val HONG_LOAN = mapOf(
    "Tý" to "Mão", "Sửu" to "Dần", "Dần" to "Sửu",
    "Mão" to "Tý", "Thìn" to "Hợi", "Tỵ" to "Tuất",
    "Ngọ" to "Dậu", "Mùi" to "Thân", "Thân" to "Mùi",
    "Dậu" to "Ngọ", "Tuất" to "Tỵ", "Hợi" to "Thìn"
)
```

### 4. Thiên Hỉ (天喜) — Đối xung Hồng Loan

Thiên Hỉ nằm ở vị trí đối xung (Lục Xung) với Hồng Loan.
→ Dùng `BaZiConstants.LUC_XUNG[hongLoanBranch]`

## Implementation Steps

1. [ ] **BaZiConstants.kt** — Thêm 3 map: `DAO_HOA`, `THIEN_Y`, `HONG_LOAN`

2. [ ] **BaZiLogic.kt** — Update `calculateShenSha()` thêm logic check:
   ```kotlin
   // 9. Đào Hoa
   val daoHoaBranches = listOf(
       BaZiConstants.DAO_HOA[dayBranch], 
       BaZiConstants.DAO_HOA[yearBranch]
   )
   if (cb in daoHoaBranches) {
       result.add(ShenSha("Đào Hoa", name, "Cát/Hung", "Sức hút, duyên ngầm"))
   }
   
   // 10. Thiên Y (theo chi Tháng)
   val thienYBranch = BaZiConstants.THIEN_Y[monthBranch]
   if (cb == thienYBranch) {
       result.add(ShenSha("Thiên Y", name, "Cát", "Y lý, sức khỏe phù trợ"))
   }
   
   // 11. Hồng Loan (theo chi Năm)
   val hongLoanBranch = BaZiConstants.HONG_LOAN[yearBranch]
   if (cb == hongLoanBranch) {
       result.add(ShenSha("Hồng Loan", name, "Cát", "Hôn nhân, hỉ sự"))
   }
   
   // 12. Thiên Hỉ (đối xung Hồng Loan)
   val thienHiBranch = BaZiConstants.LUC_XUNG[hongLoanBranch]
   if (cb == thienHiBranch) {
       result.add(ShenSha("Thiên Hỉ", name, "Cát", "Tin vui, sinh nở"))
   }
   ```

3. [x] **BaZiLogic.kt** — Truyền thêm `monthPillar` vào `calculateShenSha()`
   - Hiện tại hàm chỉ nhận `yearPillar`, `dayPillar`, `pillarsMap`
   - Cần thêm `monthBranch` để tính Thiên Y

4. [x] **Test** — Verify 4 sao mới

5. [x] **Verify với Ngọc** — Đào Hoa tại Giờ, Thiên Y tại Giờ

## Files to Modify
- `app/src/main/java/com/skul9x/battu/data/BaZiConstants.kt` — 3 maps mới
- `app/src/main/java/com/skul9x/battu/core/BaZiLogic.kt` — Update calculateShenSha

## Test Criteria
- [ ] Ngọc: Đào Hoa tại Giờ (Dậu) ✅
- [ ] Ngọc: Thiên Y tại Giờ (Dậu) ✅  
- [ ] Đào Hoa: Chi Dần → Mão
- [ ] Hồng Loan: Chi Năm Thân → Mùi
- [ ] Thiên Hỉ = Lục Xung của Hồng Loan
- [ ] Không duplicate (1 sao 1 trụ chỉ xuất hiện 1 lần)

---
Next Phase: [phase-05-annual-luck.md](./phase-05-annual-luck.md)
