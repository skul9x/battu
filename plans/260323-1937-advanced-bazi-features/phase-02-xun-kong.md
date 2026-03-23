# Phase 02: Tuần Không + Thập Thần Tàng Can
Status: ✅ Complete
Dependencies: Phase 01

## Objective
1. Thêm tính **Tuần Không (Không Vong)** — 2 chi bị mất lực trong mỗi tuần Lục Thập Hoa Giáp
2. Gắn **Thập Thần** cho từng Tàng Can (không chỉ Bản Khí) để AI luận sâu hơn

## Lý thuyết Tuần Không

### Công thức
Lục Thập Hoa Giáp chia 60 tổ hợp Can-Chi thành 6 tuần, mỗi tuần bắt đầu bằng Giáp:

| Tuần | Bắt đầu | Kết thúc | Không Vong |
|------|---------|----------|------------|
| Giáp Tý | Giáp Tý | Quý Dậu | **Tuất, Hợi** |
| Giáp Tuất | Giáp Tuất | Quý Mùi | **Thân, Dậu** |
| Giáp Thân | Giáp Thân | Quý Tỵ | **Ngọ, Mùi** |
| Giáp Ngọ | Giáp Ngọ | Quý Mão | **Thìn, Tỵ** |
| Giáp Thìn | Giáp Thìn | Quý Sửu | **Dần, Mão** |
| Giáp Dần | Giáp Dần | Quý Hợi | **Tý, Sửu** |

### Thuật toán
```
canIdx = indexOf(stem) trong THIEN_CAN  // 0-9
chiIdx = indexOf(branch) trong DIA_CHI  // 0-11

// Đếm lùi về Giáp (canIdx bước)
startChiIdx = (chiIdx - canIdx + 12) % 12

// 2 chi Không Vong = vị trí 10 và 11 sau startChiIdx
void1 = DIA_CHI[(startChiIdx + 10) % 12]
void2 = DIA_CHI[(startChiIdx + 11) % 12]
```

### Ví dụ kiểm chứng (lá số Ngọc)
- Trụ Ngày: **Nhâm Thân** → Can Nhâm (index 8), Chi Thân (index 8)
- Lùi 8 bước từ Thân: startChi = (8 - 8 + 12) % 12 = 0 → Tý
- Tuần **Giáp Tý**: Không Vong = DIA_CHI[10] = **Tuất**, DIA_CHI[11] = **Hợi** ✅
- Trùng khớp ảnh tham chiếu: Nhật Không = Tuất-Hợi ✅

## Implementation Steps

1. [ ] **BaZiConstants.kt** — Thêm hàm `getXunKong(stem, branch): Pair<String, String>`
   ```kotlin
   fun getXunKong(stem: String, branch: String): Pair<String, String> {
       val canIdx = THIEN_CAN.indexOf(stem)
       val chiIdx = DIA_CHI.indexOf(branch)
       val startChiIdx = (chiIdx - canIdx + 12) % 12
       val void1 = DIA_CHI[(startChiIdx + 10) % 12]
       val void2 = DIA_CHI[(startChiIdx + 11) % 12]
       return void1 to void2
   }
   ```

2. [ ] **Models.kt** — Thêm data class và field
   ```kotlin
   @Serializable
   data class XunKong(
       val yearVoid: Pair<String, String>,  // Niên Không
       val dayVoid: Pair<String, String>    // Nhật Không
   )
   
   // Thêm vào HiddenStem
   data class HiddenStem(
       val stem: String,
       val percentage: Int,
       val type: HiddenStemType,
       val tenGod: String? = null  // NEW: Thập Thần
   )
   
   // Thêm vào BaZiData
   val xunKong: XunKong? = null
   ```

3. [ ] **BaZiLogic.kt** — Tính Tuần Không trong `calculateBaZi()`
   ```kotlin
   val yearXunKong = BaZiConstants.getXunKong(yearPillar.stem, yearPillar.branch)
   val dayXunKong = BaZiConstants.getXunKong(dayPillar.stem, dayPillar.branch)
   val xunKong = XunKong(yearVoid = yearXunKong, dayVoid = dayXunKong)
   ```

4. [ ] **BaZiLogic.kt** — Update `createPillar()` để gắn `tenGod` cho mỗi Tàng Can
   ```kotlin
   hiddenStems = BaZiConstants.getHiddenStems(chi).map { hs ->
       hs.copy(tenGod = if (dayMaster.isNotEmpty()) 
           BaZiConstants.calculateTenGod(dayMaster, hs.stem) else null)
   }
   ```

5. [ ] **PromptBuilder.kt** — Xuất `ten_god` và `xun_kong`
   ```kotlin
   // Trong buildPillarJson:
   hsObj.put("ten_god", hs.tenGod ?: "")
   
   // Trong buildChartData:
   put("xun_kong", JSONObject().apply {
       put("year_void", JSONArray(listOf(xunKong.yearVoid.first, xunKong.yearVoid.second)))
       put("day_void", JSONArray(listOf(xunKong.dayVoid.first, xunKong.dayVoid.second)))
       put("note", "Chi bị Tuần Không sẽ giảm lực lượng đáng kể...")
   })
   ```

6. [ ] **Test** — Verify Nhâm Thân → Tuần Không = (Tuất, Hợi)

## Files to Modify
- `app/src/main/java/com/skul9x/battu/data/BaZiConstants.kt` — Thêm hàm
- `app/src/main/java/com/skul9x/battu/data/Models.kt` — Thêm fields
- `app/src/main/java/com/skul9x/battu/core/BaZiLogic.kt` — Tính toán
- `app/src/main/java/com/skul9x/battu/ai/PromptBuilder.kt` — Serialize

## Test Criteria
- [x] getXunKong("Nhâm", "Thân") == ("Tuất", "Hợi")
- [x] getXunKong("Giáp", "Tuất") == ("Thân", "Dậu")
- [x] Tàng Can Tuất có tenGod: {Mậu="Thất Sát", Tân="Chính Ấn", Đinh="Chính Tài"}
- [x] JSON output chứa `xun_kong` và `ten_god` cho hidden stems

---
Next Phase: [phase-03-auxiliary-pillars.md](./phase-03-auxiliary-pillars.md)
