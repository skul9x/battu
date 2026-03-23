# Phase 03: Thai Nguyên + Mệnh Cung (Trụ Phụ)
Status: ⬜ Pending
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

### Công thức (3 bước)

**Bước 1:** Map tháng Âm lịch → vị trí trên vòng tròn (nghịch chiều từ Tý)
```
Tháng 1 (Dần) → Sửu (vị trí đặt)
Tháng 2 (Mão) → Tý
Tháng 3 (Thìn) → Hợi
...
Công thức: monthPosition = (14 - monthChiIdx) % 12
Trong đó monthChiIdx: Dần=2, Mão=3, ..., Sửu=1
```

**Bước 2:** Từ monthPosition, đặt giờ sinh vào rồi đếm thuận đến Mão
```
lifePalaceChiIdx = (monthPosition + hourChiIdx + 2) ... 
```

Thực tế dùng công thức đơn giản hơn:
```
Chi Mệnh Cung = DIA_CHI[(14 - monthChiIdx + hourChiIdx) % 12]

// Kiểm chứng cho lá số Ngọc:
// Tháng Tuất (idx=10), Giờ Dậu (idx=9)
// (14 - 10 + 9) % 12 = 13 % 12 = 1 → Sửu???
```

> ⚠️ Công thức trên cần verify cẩn thận. Ảnh tham chiếu ghi Mệnh Cung = **Canh Tuất**.
> Có thể cần dùng phương pháp: tháng + giờ → đếm đến Dần (không phải Mão).
> Em sẽ **search và verify trước khi code** khi bắt tay vào implement Phase này.

**Bước 3:** Can Mệnh Cung tính bằng **Ngũ Hổ Độn** (giống `calculateMonthCanIndex`)

### Ví dụ kiểm chứng  
- Ảnh tham chiếu: Mệnh Cung = **Canh Tuất** 
- Can Tuất theo Ngũ Hổ Độn (năm Nhâm): Nhâm → startCan=2 (Bính) → Tuất=idx10 → diff=8 → (2+8)%10=0 → Canh??? 
- Cần kiểm tra kỹ khi implement

## Implementation Steps

1. [ ] **Models.kt** — Thêm data class `AuxiliaryPillar`
   ```kotlin
   @Serializable
   data class AuxiliaryPillar(
       val name: String,    // "Thai Nguyên" hoặc "Mệnh Cung"
       val stem: String,
       val branch: String
   )
   
   // Thêm vào BaZiData:
   val fetalOrigin: AuxiliaryPillar? = null,   // Thai Nguyên
   val lifePalace: AuxiliaryPillar? = null      // Mệnh Cung
   ```

2. [ ] **BaZiLogic.kt** — Thêm `calculateFetalOrigin()`
   ```kotlin
   private fun calculateFetalOrigin(monthCanIdx: Int, monthChiIdx: Int): AuxiliaryPillar {
       val canIdx = (monthCanIdx + 1) % 10
       val chiIdx = (monthChiIdx + 3) % 12
       return AuxiliaryPillar(
           name = "Thai Nguyên",
           stem = BaZiConstants.THIEN_CAN[canIdx],
           branch = BaZiConstants.DIA_CHI[chiIdx]
       )
   }
   ```

3. [ ] **BaZiLogic.kt** — Thêm `calculateLifePalace()` (cần research thêm khi implement)

4. [ ] **PromptBuilder.kt** — Thêm `auxiliary_pillars` section
   ```kotlin
   put("auxiliary_pillars", JSONObject().apply {
       data.fetalOrigin?.let {
           put("fetal_origin", JSONObject().apply {
               put("name", "Thai Nguyên")
               put("stem", it.stem)
               put("branch", it.branch)
           })
       }
       data.lifePalace?.let {
           put("life_palace", JSONObject().apply {
               put("name", "Mệnh Cung")
               put("stem", it.stem)
               put("branch", it.branch)
           })
       }
   })
   ```

5. [ ] **Test** — Verify Thai Nguyên Ngọc = Tân Sửu, Mệnh Cung = Canh Tuất

## Files to Modify
- `app/src/main/java/com/skul9x/battu/data/Models.kt`
- `app/src/main/java/com/skul9x/battu/core/BaZiLogic.kt`
- `app/src/main/java/com/skul9x/battu/ai/PromptBuilder.kt`

## Test Criteria
- [ ] Thai Nguyên: Tháng Canh Tuất → Tân Sửu ✅
- [ ] Thai Nguyên: Tháng Giáp Tý → Ất Mão
- [ ] Mệnh Cung: Ngọc → Canh Tuất (đối chiếu ảnh tham chiếu)
- [ ] JSON output chứa `auxiliary_pillars` đầy đủ

## Notes
- Mệnh Cung cần research kỹ hơn vì có nhiều phương pháp tính khác nhau
- Công thức sẽ được verify trước khi code

---
Next Phase: [phase-04-shen-sha.md](./phase-04-shen-sha.md)
