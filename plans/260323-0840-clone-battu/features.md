# Features: Bát Tự Calculation Engine

## Core Features to Clone

### 1. Four Pillars (Tứ Trụ) Calculation
**Module**: `BaZiLogic.kt`
**Description**: Calculate Year, Month, Day, and Hour pillars based on birth date/time

**Components**:
- Year Pillar (Niên Trụ): Based on Lập Xuân solar term
- Month Pillar (Nguyệt Trụ): Based on 12 solar terms
- Day Pillar (Nhật Trụ): Based on Julian Day Number
- Hour Pillar (Giờ Trụ): Based on 2-hour periods

**Dependencies**:
- Solar term data (solar-term.json)
- Can Chi constants
- JDN calculation

---

### 2. True Solar Time (TST) Correction
**Module**: `BaZiLogic.kt` → `calculateBaZi()`
**Description**: Adjust local time based on geographical longitude

**Formula**:
```
TST = LocalTime + (Longitude - 105°) × 4 minutes
```

**Features**:
- Configurable longitude (default: 105.8°E for Hanoi)
- Minute-level precision
- Timezone-aware calculation

---

### 3. Solar Term Integration
**Module**: `BaZiLogic.kt` + `solar-term.json`
**Description**: 24 Solar Terms (24 Tiết Khí) astronomical data

**Features**:
- Accurate solar term timestamps (1900-2100)
- Month pillar determination based on solar terms
- Year boundary at Lập Xuân (Start of Spring)
- Current/next solar term display

**Solar Terms Used**:
- Lập Xuân (Start of Spring) - Year boundary
- Tiểu Hàn, Kinh Trập, Thanh Minh, Lập Hạ, Mang Chủng, Tiểu Thử, Lập Thu, Bạch Lộ, Hàn Lộ, Lập Đông, Đại Tuyết (Month boundaries)

---

### 4. Ten Gods (Thập Thần) Calculation
**Module**: `Constants.kt` → `calculateTenGod()`
**Description**: Calculate relationships between Day Master and other stems

**Ten Gods**:
- Tỷ Kiên (Parallel)
- Kiếp Tài (Rob Wealth)
- Thực Thần (Eating God)
- Thương Quan (Hurting Officer)
- Thiên Tài (Indirect Wealth)
- Chính Tài (Direct Wealth)
- Thất Sát (Seven Killings)
- Chính Quan (Direct Officer)
- Thiên Ấn (Indirect Seal)
- Chính Ấn (Direct Seal)

**Calculation Logic**:
- Based on Five Elements relationships (sinh/khắc)
- Considers Yin-Yang polarity
- Applied to all stems (Year/Month/Day/Hour)

---

### 5. Element Balance Analysis
**Module**: `BaZiLogic.kt` → `calculateElementBalance()`
**Description**: Calculate strength of Five Elements in the chart

**Elements Scored**:
- Kim (Metal)
- Mộc (Wood)
- Thủy (Water)
- Hỏa (Fire)
- Thổ (Earth)

**Scoring System**:
- Heavenly Stems: 40 points each
- Hidden Stems (Tàng Can): Weighted by percentage
  - Primary hidden stem: 60-100%
  - Secondary: 30%
  - Tertiary: 10%

---

### 6. Hidden Stems (Tàng Can) System
**Module**: `Constants.kt` → `TANG_CAN`, `TANG_CAN_WEIGHT`
**Description**: Hidden stems within each Earthly Branch

**Features**:
- 1-3 hidden stems per branch
- Weighted strength percentages
- Used for element balance calculation
- Used for Ten Gods analysis

---

### 7. Yin-Yang Classification
**Module**: `Constants.kt` → `CAN_YIN_YANG`, `CHI_YIN_YANG`
**Description**: Yin-Yang polarity for all stems and branches

**Features**:
- Heavenly Stems: Dương/Âm classification
- Earthly Branches: Dương/Âm classification
- Used in Ten Gods calculation
- Used in compatibility analysis

---

### 8. Day Boundary Options
**Module**: `BaZiLogic.kt`
**Description**: Configurable day change time

**Options**:
- Standard: Day changes at 00:00 (midnight)
- Traditional: Day changes at 23:00 (Tý hour start)

**Use Case**: Different schools use different day boundaries

---

### 9. Solar-Lunar Calendar Conversion
**Module**: `LunarConverter.kt`, `LunarDateUtil.kt`
**Description**: Convert between solar and lunar dates

**Features**:
- Solar to Lunar conversion (1900-2049)
- Leap month detection
- Lunar table lookup method
- Can Chi calculation for lunar dates

---

### 10. Pillar Data Structure
**Module**: `Models.kt` → `Pillar`
**Description**: Complete pillar information

**Data Included**:
- Stem (Can): Name, Yin-Yang, Element
- Branch (Chi): Name, Yin-Yang, Element
- Hidden Stems: List with names
- Combined Can-Chi string

---

## Features NOT Needed (Tử Vi Specific)

### Excluded from Clone:
- ❌ Tử Vi 14 main stars placement
- ❌ 12 palaces (Mệnh, Phụ Mẫu, etc.)
- ❌ Tứ Hóa transformations
- ❌ Đại Vận (Major Cycles)
- ❌ Lưu Niên (Annual Stars)
- ❌ Lưu Nguyệt (Monthly Stars)
- ❌ AI interpretation (Gemini integration)
- ❌ Database storage (Room)
- ❌ UI components (Jetpack Compose)

---

## Target Application Features

### New Application Requirements:
1. **Bát Tự Calculation**: Core engine from source
2. **AI Analysis**: Send Bát Tự chart to AI for interpretation
3. **User Interface**: Simple input form + result display
4. **Export**: Text/JSON format of Bát Tự chart

### Technology Stack (To Be Determined):
- Backend: ?
- Frontend: ?
- AI Integration: ?
- Database: ?

---

## Summary

**Total Features to Clone**: 10 core features
**Estimated Complexity**: Medium
**Core Files Required**: 5 Kotlin files + 1 JSON data file
**Total LOC to Port**: ~850 lines
