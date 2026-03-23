# 🎨 DESIGN: Phase 01 - Core Data Structure

Ngày tạo: 2026-03-23
Dựa trên: phase-01-core-data.md + Research từ các nguồn chuyên môn Bát Tự

---

## 1. VẤN ĐỀ CẦN GIẢI QUYẾT

### 1.1. Lỗi hiện tại trong hệ thống

**❌ Lỗi 1: Nạp Âm bị sai**
- **Hiện tại:** Map đơn giản theo element của Can + Chi → Sai hoàn toàn
- **Ví dụ sai:** Nhâm Thân (Canh Kim + Thủy) → "Thủy Kim" 
- **Đúng phải là:** Nhâm Thân → "Kiếm Phong Kim" (theo bảng 60 Giáp Tý)

**❌ Lỗi 2: Thập Thần Địa Chi lấy sai Tàng Can**
- **Hiện tại:** Random pick hoặc lấy sai thứ tự Tàng Can
- **Ví dụ sai:** Thìn (Mậu 60%, Ất 30%, Quý 10%) → Lấy Thiên Tài (từ Ất hoặc Quý)
- **Đúng phải là:** Thìn → Lấy Thiên Ấn (từ Mậu - Bản Khí 60%)

**❌ Lỗi 3: Không có trọng số Tàng Can**
- **Hiện tại:** Chỉ list các Can, không biết Can nào mạnh hơn
- **Đúng phải là:** Mỗi Can có % sức mạnh (Bản Khí 60%, Trung Khí 30%, Dư Khí 10%)

---

## 2. THIẾT KẾ DỮ LIỆU MỚI

### 2.1. Bảng tra cứu Nạp Âm (60 Giáp Tý)

**Cấu trúc:**
```
Mỗi cặp Can-Chi → 1 Nạp Âm cố định
Ví dụ:
- Giáp Tý, Ất Sửu → Hải Trung Kim (Vàng trong biển)
- Bính Dần, Đinh Mão → Lô Trung Hỏa (Lửa trong lò)
- Nhâm Thân, Quý Dậu → Kiếm Phong Kim (Vàng đầu kiếm)
```

**Dữ liệu cần lưu:**
```kotlin
data class NapAm(
    val name: String,        // "Kiếm Phong Kim"
    val element: Element,    // KIM
    val description: String  // "Vàng đầu kiếm - sắc bén, cứng rắn"
)

// Map tĩnh: (Can, Chi) → NapAm
val NAP_AM_TABLE: Map<Pair<String, String>, NapAm>
```

**Bảng đầy đủ 60 Giáp Tý:**

| STT | Can Chi | Nạp Âm | Element |
|-----|---------|--------|---------|
| 1-2 | Giáp Tý, Ất Sửu | Hải Trung Kim | KIM |
| 3-4 | Bính Dần, Đinh Mão | Lô Trung Hỏa | HỎA |
| 5-6 | Mậu Thìn, Kỷ Tỵ | Đại Lâm Mộc | MỘC |
| 7-8 | Canh Ngọ, Tân Mùi | Lộ Bàng Thổ | THỔ |
| 9-10 | Nhâm Thân, Quý Dậu | Kiếm Phong Kim | KIM |
| 11-12 | Giáp Tuất, Ất Hợi | Sơn Đầu Hỏa | HỎA |
| 13-14 | Bính Tý, Đinh Sửu | Giản Hạ Thủy | THỦY |
| 15-16 | Mậu Dần, Kỷ Mão | Thành Đầu Thổ | THỔ |
| 17-18 | Canh Thìn, Tân Tỵ | Bạch Lạp Kim | KIM |
| 19-20 | Nhâm Ngọ, Quý Mùi | Dương Liễu Mộc | MỘC |
| 21-22 | Giáp Thân, Ất Dậu | Tuyền Trung Thủy | THỦY |
| 23-24 | Bính Tuất, Đinh Hợi | Ốc Thượng Thổ | THỔ |
| 25-26 | Mậu Tý, Kỷ Sửu | Tích Lịch Hỏa | HỎA |
| 27-28 | Canh Dần, Tân Mão | Tùng Bách Mộc | MỘC |
| 29-30 | Nhâm Thìn, Quý Tỵ | Trường Lưu Thủy | THỦY |
| 31-32 | Giáp Ngọ, Ất Mùi | Sa Trung Kim | KIM |
| 33-34 | Bính Thân, Đinh Dậu | Sơn Hạ Hỏa | HỎA |
| 35-36 | Mậu Tuất, Kỷ Hợi | Bình Địa Mộc | MỘC |
| 37-38 | Canh Tý, Tân Sửu | Bích Thượng Thổ | THỔ |
| 39-40 | Nhâm Dần, Quý Mão | Kim Bạch Kim | KIM |
| 41-42 | Giáp Thìn, Ất Tỵ | Phúc Đăng Hỏa | HỎA |
| 43-44 | Bính Ngọ, Đinh Mùi | Thiên Hà Thủy | THỦY |
| 45-46 | Mậu Thân, Kỷ Dậu | Đại Dịch Thổ | THỔ |
| 47-48 | Canh Tuất, Tân Hợi | Thoa Xuyến Kim | KIM |
| 49-50 | Nhâm Tý, Quý Sửu | Tang Đố Mộc | MỘC |
| 51-52 | Giáp Dần, Ất Mão | Đại Khê Thủy | THỦY |
| 53-54 | Bính Thìn, Đinh Tỵ | Sa Trung Thổ | THỔ |
| 55-56 | Mậu Ngọ, Kỷ Mùi | Thiên Thượng Hỏa | HỎA |
| 57-58 | Canh Thân, Tân Dậu | Thạch Lựu Mộc | MỘC |
| 59-60 | Nhâm Tuất, Quý Hợi | Đại Hải Thủy | THỦY |

---

### 2.2. Bảng Tàng Can có trọng số

**Cấu trúc:**
```kotlin
data class HiddenStem(
    val stem: String,           // "Mậu"
    val percentage: Int,        // 60
    val type: HiddenStemType    // BAN_KHI
)

enum class HiddenStemType {
    BAN_KHI,    // Bản Khí (Main) - 60%
    TRUNG_KHI,  // Trung Khí (Middle) - 30%
    DU_KHI      // Dư Khí (Residual) - 10%
}
```

**Bảng đầy đủ 12 Địa Chi:**

| Địa Chi | Tàng Can | Tỷ lệ | Loại |
|---------|----------|-------|------|
| **Tý** | Quý | 100% | Bản Khí |
| **Sửu** | Kỷ | 60% | Bản Khí |
|  | Quý | 30% | Trung Khí |
|  | Tân | 10% | Dư Khí |
| **Dần** | Giáp | 60% | Bản Khí |
|  | Bính | 30% | Trung Khí |
|  | Mậu | 10% | Dư Khí |
| **Mão** | Ất | 100% | Bản Khí |
| **Thìn** | Mậu | 60% | Bản Khí |
|  | Ất | 30% | Trung Khí |
|  | Quý | 10% | Dư Khí |
| **Tỵ** | Bính | 60% | Bản Khí |
|  | Mậu | 30% | Trung Khí |
|  | Canh | 10% | Dư Khí |
| **Ngọ** | Đinh | 70% | Bản Khí |
|  | Kỷ | 30% | Trung Khí |
| **Mùi** | Kỷ | 60% | Bản Khí |
|  | Đinh | 30% | Trung Khí |
|  | Ất | 10% | Dư Khí |
| **Thân** | Canh | 60% | Bản Khí |
|  | Nhâm | 30% | Trung Khí |
|  | Mậu | 10% | Dư Khí |
| **Dậu** | Tân | 100% | Bản Khí |
| **Tuất** | Mậu | 60% | Bản Khí |
|  | Tân | 30% | Trung Khí |
|  | Đinh | 10% | Dư Khí |
| **Hợi** | Nhâm | 70% | Bản Khí |
|  | Giáp | 30% | Trung Khí |

---

### 2.3. Quy tắc tính Thập Thần từ Nhật Chủ

**Logic:**
```
Bước 1: Xác định Nhật Chủ (Thiên Can ngày sinh)
Bước 2: Với mỗi Thiên Can khác trong Tứ Trụ:
  - So sánh Ngũ Hành (Sinh/Khắc/Ngang)
  - So sánh Âm Dương (Cùng/Khác)
  → Xác định 1 trong 10 Thần

Bước 3: Với mỗi Địa Chi:
  - LẤY BẢN KHÍ (60-70%) làm đại diện
  - Tính Thập Thần của Bản Khí đó
```

**Bảng tra cứu Thập Thần:**

| Quan hệ Ngũ Hành | Âm Dương | Thập Thần |
|------------------|----------|-----------|
| Ngang vai (cùng hành) | Cùng | Tỷ Kiên |
| Ngang vai (cùng hành) | Khác | Kiếp Tài |
| Ta sinh | Cùng | Thực Thần |
| Ta sinh | Khác | Thương Quan |
| Sinh ta | Cùng | Thiên Ấn (Kiêu Thần) |
| Sinh ta | Khác | Chính Ấn |
| Ta khắc | Cùng | Thiên Tài |
| Ta khắc | Khác | Chính Tài |
| Khắc ta | Cùng | Thiên Quan (Thất Sát) |
| Khắc ta | Khác | Chính Quan |

**Ví dụ cụ thể:**
```
Nhật Chủ: Giáp (Dương Mộc)
Địa Chi: Thìn (Tàng Can: Mậu 60%, Ất 30%, Quý 10%)

Bước 1: Lấy Bản Khí = Mậu (Dương Thổ)
Bước 2: Giáp Mộc vs Mậu Thổ
  - Quan hệ: Mộc khắc Thổ (Ta khắc)
  - Âm Dương: Giáp Dương, Mậu Dương (Cùng)
  → Thập Thần = Thiên Tài ✓

(Không lấy Ất hay Quý vì chúng chỉ là Trung/Dư khí)
```

---

## 3. CẤU TRÚC FILE CODE

### 3.1. File mới: `BaZiConstants.kt`

```kotlin
package com.skul9x.battu.data

object BaZiConstants {
    
    // 1. Bảng Nạp Âm 60 Giáp Tý
    val NAP_AM_TABLE: Map<Pair<String, String>, NapAm> = mapOf(
        ("Giáp" to "Tý") to NapAm("Hải Trung Kim", Element.KIM, "Vàng trong biển"),
        ("Ất" to "Sửu") to NapAm("Hải Trung Kim", Element.KIM, "Vàng trong biển"),
        // ... 58 cặp còn lại
    )
    
    // 2. Bảng Tàng Can 12 Địa Chi
    val HIDDEN_STEMS_TABLE: Map<String, List<HiddenStem>> = mapOf(
        "Tý" to listOf(
            HiddenStem("Quý", 100, HiddenStemType.BAN_KHI)
        ),
        "Sửu" to listOf(
            HiddenStem("Kỷ", 60, HiddenStemType.BAN_KHI),
            HiddenStem("Quý", 30, HiddenStemType.TRUNG_KHI),
            HiddenStem("Tân", 10, HiddenStemType.DU_KHI)
        ),
        // ... 10 Chi còn lại
    )
    
    // 3. Helper: Lấy Bản Khí của Địa Chi
    fun getMainHiddenStem(branch: String): String {
        return HIDDEN_STEMS_TABLE[branch]
            ?.first { it.type == HiddenStemType.BAN_KHI }
            ?.stem ?: branch
    }
    
    // 4. Helper: Tính Thập Thần
    fun calculateTenGod(dayStem: String, targetStem: String): String {
        val dayElement = getElementOfStem(dayStem)
        val dayYinYang = getYinYangOfStem(dayStem)
        val targetElement = getElementOfStem(targetStem)
        val targetYinYang = getYinYangOfStem(targetStem)
        
        val relation = getElementRelation(dayElement, targetElement)
        val sameYinYang = (dayYinYang == targetYinYang)
        
        return TEN_GOD_MAP[Pair(relation, sameYinYang)] ?: "Unknown"
    }
}
```

---

### 3.2. Update file: `Models.kt`

**Thêm data classes mới:**
```kotlin
data class NapAm(
    val name: String,
    val element: Element,
    val description: String
)

data class HiddenStem(
    val stem: String,
    val percentage: Int,
    val type: HiddenStemType
)

enum class HiddenStemType {
    BAN_KHI,    // Main 60-100%
    TRUNG_KHI,  // Middle 30%
    DU_KHI      // Residual 10%
}
```

**Update `Pillar` class:**
```kotlin
data class Pillar(
    val stem: String,
    val branch: String,
    val napAm: NapAm,                    // ← MỚI: Từ bảng tra
    val hiddenStems: List<HiddenStem>,   // ← MỚI: Có trọng số
    val tenGodOfStem: String,
    val tenGodOfBranch: String           // ← SỬA: Lấy từ Bản Khí
)
```

---

## 4. CHECKLIST KIỂM TRA

### Tính năng: Nạp Âm Lookup

Phase 01 HOÀN THÀNH khi:

✅ **Cơ bản:**
- [ ] Tạo được `BaZiConstants.kt` với bảng 60 Giáp Tý đầy đủ
- [ ] Hàm `getNapAm(stem, branch)` trả về đúng Nạp Âm
- [ ] Test case: Nhâm Thân → "Kiếm Phong Kim" (KIM)
- [ ] Test case: Giáp Tý → "Hải Trung Kim" (KIM)

✅ **Nâng cao:**
- [ ] Nạp Âm có cả `name`, `element`, `description`
- [ ] Xử lý edge case: Can/Chi không hợp lệ → return null

---

### Tính năng: Tàng Can với trọng số

✅ **Cơ bản:**
- [ ] Bảng 12 Địa Chi có đầy đủ Tàng Can
- [ ] Mỗi Tàng Can có `percentage` và `type`
- [ ] Hàm `getMainHiddenStem(branch)` trả về Bản Khí
- [ ] Test case: Thìn → "Mậu" (60%, BAN_KHI)

✅ **Nâng cao:**
- [ ] Tổng % của mỗi Chi = 100%
- [ ] Thứ tự: Bản Khí → Trung Khí → Dư Khí

---

### Tính năng: Thập Thần từ Bản Khí

✅ **Cơ bản:**
- [ ] Hàm `calculateTenGod(dayStem, targetStem)` hoạt động
- [ ] Test case: Giáp + Mậu (Thìn) → "Thiên Tài"
- [ ] Test case: Giáp + Quý (Tý) → "Chính Ấn"

✅ **Nâng cao:**
- [ ] Thập Thần Địa Chi LUÔN lấy từ Bản Khí
- [ ] Không còn lấy sai Trung/Dư khí

---

## 5. TEST CASES

### TC-01: Nạp Âm Lookup - Happy Path
```
Given: Có cặp Can-Chi hợp lệ
When:  getNapAm("Nhâm", "Thân")
Then:  ✓ Return NapAm(name="Kiếm Phong Kim", element=KIM, ...)
```

### TC-02: Nạp Âm Lookup - Invalid Input
```
Given: Can hoặc Chi không tồn tại
When:  getNapAm("ABC", "Thân")
Then:  ✓ Return null hoặc throw exception
```

### TC-03: Tàng Can - Lấy Bản Khí
```
Given: Địa Chi "Thìn" (Mậu 60%, Ất 30%, Quý 10%)
When:  getMainHiddenStem("Thìn")
Then:  ✓ Return "Mậu"
       ✓ Không return "Ất" hay "Quý"
```

### TC-04: Thập Thần - Từ Bản Khí Địa Chi
```
Given: Nhật Chủ = Giáp (Dương Mộc)
       Địa Chi = Thìn (Bản Khí = Mậu Dương Thổ)
When:  calculateTenGod("Giáp", getMainHiddenStem("Thìn"))
Then:  ✓ Return "Thiên Tài" (Mộc khắc Thổ, cùng Dương)
```

### TC-05: Thập Thần - Edge Case Âm Dương
```
Given: Nhật Chủ = Đinh (Âm Hỏa)
       Target = Canh (Dương Kim)
When:  calculateTenGod("Đinh", "Canh")
Then:  ✓ Return "Chính Tài" (Hỏa khắc Kim, khác Âm Dương)
```

---

## 6. TIẾP THEO

Sau khi hoàn thành Phase 01:

1️⃣ **Phase 02:** Tính Mùa sinh, Trường Sinh, Element Balance
2️⃣ **Phase 03:** Thần Sát, Hợp/Xung/Hình, Đại Vận
3️⃣ **Phase 04:** Update Prompt JSON cho AI

---

*Tạo bởi AWF 4.0.2 - Design Phase*
*Research sources: lichngaytot.com, vothanhnha.com, phongthuykhaitoan.com*
