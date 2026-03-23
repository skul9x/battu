# Phase 02: Clone Core Bát Tự Logic
Status: ⬜ Pending
Dependencies: Phase 01

## Objective
Trích xuất và clone thuật toán lập Tứ Trụ (Bát Tự) từ `TuViAndroid-BatTu` sang app mới. Đảm bảo logic tính toán 100% chính xác.

## Source Files (Cần Clone)

| Source File | Path (cũ) | LOC | Action |
|-------------|-----------|-----|--------|
| `BaZiLogic.kt` | `com.example.tviai.core` | 251 | Clone + đổi package |
| `Constants.kt` | `com.example.tviai.core` | 512 | Clone + lọc chỉ Bát Tự |
| `LunarConverter.kt` | `com.example.tviai.core` | 80 | Clone |
| `LunarDateUtil.kt` | `com.example.tviai.core.util` | 169 | Clone |
| `Models.kt` | `com.example.tviai.data` | 124 | Clone + lọc + Serializable |
| `solar-term.json` | `assets/` | 278KB | Copy nguyên |

## Requirements

### Functional
- [ ] Tính đúng Trụ Năm (Year Pillar) dựa trên Lập Xuân
- [ ] Tính đúng Trụ Tháng (Month Pillar) dựa trên Tiết Khí
- [ ] Tính đúng Trụ Ngày (Day Pillar) dựa trên Julian Day Number
- [ ] Tính đúng Trụ Giờ (Hour Pillar) dựa trên TST
- [ ] True Solar Time (TST) correction hoạt động
- [ ] Thập Thần (Ten Gods) tính chính xác
- [ ] Ngũ Hành balance tính chính xác
- [ ] Tàng Can hiển thị đúng

### Non-Functional
- [ ] Tất cả calculation < 100ms
- [ ] Hỗ trợ range năm 1900-2049 (giới hạn lunar table)

## Implementation Steps

### 1. Clone Data Models
- [ ] Tạo `com.skul9x.battu.data.Models.kt` từ source
- [ ] **Giữ lại (Bát Tự):**
  - `Gender` enum
  - `UserInput` data class (giản hóa: bỏ `viewingYear`, `readingStyle`, `viewingMode`)
  - `Pillar` data class
  - `TenGods` data class
  - `BaZiData` data class
- [ ] **Loại bỏ (Tử Vi):**
  - `CungInfo`, `UserInfoResult`, `LasoData`, `ReadingStyle`, `ViewingMode`
- [ ] Thêm `@Serializable` annotation cho JSON output

**Target Models:**
```kotlin
@Serializable
data class UserInput(
    val name: String,
    val solarDay: Int,
    val solarMonth: Int,
    val solarYear: Int,
    val hour: Int,
    val gender: Gender,
    val isLunar: Boolean = false,
    val longitude: Double? = 105.8,
    val dayBoundaryAt23: Boolean = true
)

@Serializable
data class Pillar(
    val stem: String,          // Thiên Can
    val stemYinYang: String,   // Âm/Dương
    val stemElement: String,   // Ngũ Hành Can
    val branch: String,        // Địa Chi
    val branchYinYang: String, // Âm/Dương Chi
    val branchElement: String, // Ngũ Hành Chi
    val hiddenStems: List<String> // Tàng Can
)

@Serializable
data class TenGods(
    val dayMaster: String,
    val yearStemGod: String,
    val yearBranchGod: String,
    val monthStemGod: String,
    val monthBranchGod: String,
    val hourStemGod: String,
    val hourBranchGod: String
)

@Serializable
data class BaZiData(
    val birthInfo: String,
    val year: Pillar,
    val month: Pillar,
    val day: Pillar,
    val hour: Pillar,
    val tenGods: TenGods,
    val currentTerm: String,
    val nextTerm: String,
    val nextTermTime: String,
    val elementBalance: Map<String, Int>
)
```

### 2. Clone Constants (Lọc Bát Tự)
- [ ] Tạo `com.skul9x.battu.core.Constants.kt`
- [ ] **Giữ lại (~200 LOC):**
  - `THIEN_CAN` (10 can)
  - `DIA_CHI` (12 chi)
  - `NGU_HANH_CAN` (ngũ hành can)
  - `NGU_HANH_CUNG` (ngũ hành chi)
  - `CAN_YIN_YANG` (âm dương can)
  - `CHI_YIN_YANG` (âm dương chi)
  - `TANG_CAN` (tàng can 12 chi)
  - `TANG_CAN_WEIGHT` (trọng số tàng can)
  - `NAP_AM_MAP` (60 Giáp Tý nạp âm)
  - `napAmToNguHanh()` function
  - `sinhKhac()` function
  - `calculateTenGod()` function
- [ ] **Loại bỏ:** Tất cả Tử Vi constants (CHINH_TINH, STAR_SCORES, CACH_CUC, PHU_TINH_BRIGHTNESS, v.v.)

### 3. Clone Core Logic
- [ ] Copy `BaZiLogic.kt` → `com.skul9x.battu.core.BaZiLogic.kt`
- [ ] Đổi package name
- [ ] Đổi import từ `com.example.tviai.*` → `com.skul9x.battu.*`
- [ ] Giữ nguyên toàn bộ logic (không sửa thuật toán)

### 4. Clone Lunar Calendar Utils
- [ ] Copy `LunarConverter.kt` → `com.skul9x.battu.core.LunarConverter.kt`
- [ ] Copy `LunarDateUtil.kt` → `com.skul9x.battu.core.util.LunarDateUtil.kt`
- [ ] Đổi package names

### 5. Copy Solar Term Data
- [ ] Copy `solar-term.json` → `app/src/main/assets/solar-term.json`
- [ ] Verify JSON valid

### 6. Integration Test
- [ ] Tạo `BaZiLogicTest.kt` trong test folder
- [ ] Test case: Ngày 01/01/2000, giờ Tý → Verify Tứ Trụ
- [ ] Test case: Người sinh gần Lập Xuân → Verify Year Pillar chuyển đúng
- [ ] Test case: Người sinh giờ 23h → Verify Day boundary logic
- [ ] Cross-check với app cũ bằng cùng input

## Files to Create/Modify
| File | Action | Package |
|------|--------|---------|
| `Models.kt` | NEW | `com.skul9x.battu.data` |
| `Constants.kt` | NEW | `com.skul9x.battu.core` |
| `BaZiLogic.kt` | NEW | `com.skul9x.battu.core` |
| `LunarConverter.kt` | NEW | `com.skul9x.battu.core` |
| `LunarDateUtil.kt` | NEW | `com.skul9x.battu.core.util` |
| `solar-term.json` | NEW | `assets/` |
| `BaZiLogicTest.kt` | NEW | `com.skul9x.battu` (test) |

## Test Criteria
- [ ] Tứ Trụ cho ngày 01/01/2000 00:00 tại Hà Nội = đúng theo lịch vạn niên
- [ ] Thập Thần tính đúng cho mọi input
- [ ] Element balance tổng hợp = 5 hành
- [ ] Solar term boundary dates chuyển đúng tháng
- [ ] Unit tests pass 100%

## Notes
- **KHÔNG SỬA thuật toán** trong BaZiLogic.kt. Chỉ đổi package name.
- Constants.kt từ source là 512 LOC nhưng ~60% là Tử Vi (sao, cung, cách cục). Chỉ clone ~200 LOC Bát Tự.
- `solar-term.json` support range 1900-2049.

---
Next Phase: [phase-03-ui.md](./phase-03-ui.md)
