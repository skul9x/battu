# Phase 01: Fix Interactions Bug (Phục Ngâm + Bán Hợp)
Status: ✅ Complete
Dependencies: Không

## Objective
Sửa lỗi logic trong `calculateInteractions()`:
- `Thân - Thân` đang bị nhận diện sai là "Bán Hợp Thủy" → Phải là **Phục Ngâm**
- Thêm logic phát hiện Phục Ngâm (伏吟) khi 2 trụ trùng Can hoặc trùng Chi

## Bug Analysis
```
// HIỆN TẠI (SAI):
interactions: [
  { type: "Bán Hợp Thủy", pillars: ["Năm","Ngày"], description: "Thân - Thân" }
]

// SAU KHI FIX (ĐÚNG):
interactions: [
  { type: "Phục Ngâm", pillars: ["Năm","Ngày"], description: "Thân trùng Thân" }
]
```

## Root Cause
Dòng ~429 trong `BaZiLogic.kt`, hàm `calculateInteractions`:
```kotlin
BaZiConstants.TAM_HOP.forEach { (group, element) ->
    if (group.contains(p1.branch) && group.contains(p2.branch)) {
        // BUG: Khi p1.branch == p2.branch (VD: Thân-Thân)
        // Cả 2 đều thuộc group {Thân, Tý, Thìn} → match → "Bán Hợp Thủy" SAI!
        result.add(...)
    }
}
```

## Implementation Steps

1. [x] **Models.kt** — Thêm `PHUC_NGAM` vào `InteractionType` enum
   ```kotlin
   enum class InteractionType {
       LUC_HOP, TAM_HOP, BAN_TAM_HOP, TAM_HOI, 
       LUC_XUNG, LUC_HAI, TAM_HINH, TU_HINH, 
       CAN_HOP, CAN_XUNG,
       PHUC_NGAM  // NEW
   }
   ```

2. [x] **BaZiLogic.kt** — Fix Bán Tam Hợp: thêm guard `p1.branch != p2.branch`
   ```kotlin
   // Dòng ~429: Thêm điều kiện
   if (group.contains(p1.branch) && group.contains(p2.branch) 
       && p1.branch != p2.branch) { // ← FIX
       result.add(...)
   }
   ```

3. [x] **BaZiLogic.kt** — Thêm logic Phục Ngâm (Chi trùng Chi)
   ```kotlin
   // Sau Lục Hại, trước Tam Hợp:
   if (p1.branch == p2.branch && !BaZiConstants.TU_HINH.contains(p1.branch)) {
       result.add(Interaction(
           InteractionType.PHUC_NGAM, "Phục Ngâm",
           listOf(name1, name2), "${p1.branch} trùng ${p2.branch}"
       ))
   }
   ```

4. [x] **Test** — Verify Thân-Thân = Phục Ngâm, NOT Bán Hợp

## Files to Modify
- `app/src/main/java/com/skul9x/battu/data/Models.kt` — Thêm enum value
- `app/src/main/java/com/skul9x/battu/core/BaZiLogic.kt` — Fix logic

## Test Criteria
- [x] Thân-Thân → Phục Ngâm (không còn Bán Hợp Thủy)
- [x] Thân-Tý → Vẫn là Bán Hợp Thủy (không regression)
- [x] Thân-Tý-Thìn → Tam Hợp Thủy (không regression)
- [x] Existing tests pass: `BaZiLogicTest.testInteractions_BanTamHop`

## Notes
- Phục Ngâm = 2 chi giống nhau ở 2 trụ khác nhau
- Tự hình (Thìn-Thìn, Ngọ-Ngọ, Dậu-Dậu, Hợi-Hợi) đã được xử lý riêng → cần exclude khỏi Phục Ngâm

---
Next Phase: [phase-02-xun-kong.md](./phase-02-xun-kong.md)
