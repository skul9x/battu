# Project Analysis: TuViAndroid-BatTu

## Programming Stack

### Backend/Core
- **Language**: Kotlin
- **Framework**: Android Native (Jetpack Compose)
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Architecture
- **Build System**: Gradle (Kotlin DSL)

### Frontend
- **UI Framework**: Jetpack Compose (Modern Android UI)
- **Theme**: Material Design 3 with Dark Mode support

### Database
- **Database**: Room Persistence Library
- **Data Storage**: DataStore (Preferences)

### External Services
- **AI Integration**: Google Gemini API (Generative AI SDK)
- **Network**: Retrofit/OkHttp

### Dependencies
- Hilt (Dependency Injection)
- Kotlin Coroutines
- Jetpack Navigation

---

## Code Structure

### Core Modules (Business Logic)

#### 1. **BaZiLogic.kt** (251 LOC)
- **Purpose**: Core Bát Tự (Four Pillars) calculation engine
- **Key Functions**:
  - `calculateBaZi()`: Main calculation entry point
  - True Solar Time (TST) correction based on longitude
  - Year/Month/Day/Hour pillar calculation
  - Solar term (24 Tiết Khí) integration
  - Ten Gods (Thập Thần) calculation
  - Element balance analysis

#### 2. **Constants.kt** (512 LOC)
- **Purpose**: Comprehensive data constants for Bát Tự and Tử Vi
- **Contains**:
  - Thiên Can (10 Heavenly Stems)
  - Địa Chi (12 Earthly Branches)
  - Ngũ Hành (Five Elements) mappings
  - Tàng Can (Hidden Stems) with weights
  - Âm Dương (Yin-Yang) classifications
  - Ten Gods calculation logic
  - Nạp Âm (60 Jiazi cycle) mappings

#### 3. **LunarConverter.kt** (80 LOC)
- **Purpose**: Solar-Lunar calendar conversion
- **Key Functions**:
  - `convertSolarToLunar()`: Date conversion
  - `getCanChiNam()`: Year pillar calculation
  - `getCanChiThang()`: Month pillar calculation
  - `getChiGio()`: Hour branch calculation

#### 4. **LunarDateUtil.kt** (169 LOC)
- **Purpose**: Low-level lunar calendar utilities
- **Functions**: Julian Day Number (JDN) calculations, lunar table lookup

#### 5. **TuViLogic.kt** (58,128 bytes)
- **Purpose**: Tử Vi Đẩu Số calculation (not needed for Bát Tự only)

#### 6. **GeminiClient.kt** (76,094 bytes)
- **Purpose**: AI integration for interpretation (not needed for core calculation)

### Data Layer

#### Models.kt
- `UserInput`: Input data structure
- `Pillar`: Pillar data (stem, branch, elements, hidden stems)
- `BaZiData`: Complete Bát Tự result
- `TenGods`: Ten Gods relationships

### External Data Files

#### solar-term.json (278KB, 5,859 lines)
- **Purpose**: 24 Solar Terms astronomical data (1900-2100)
- **Format**: JSON with UTC timestamps for each solar term
- **Critical**: Required for accurate month pillar calculation

---

## External Dependencies

### Required for Bát Tự Calculation
1. **solar-term.json**: Solar term data file
2. **Kotlin Standard Library**: Date/time handling
3. **JSON parsing**: org.json.JSONObject

### NOT Required (Android/UI specific)
- Jetpack Compose
- Room Database
- Gemini AI SDK
- Hilt DI
- DataStore

---

## Project Size Estimation

### Source Project (TuViAndroid-BatTu)
- **Total Files**: 35 Kotlin files
- **Total LOC**: ~7,435 lines

### Core Bát Tự Engine (Extractable)
- **BaZiLogic.kt**: 251 LOC
- **Constants.kt**: 512 LOC (partial - Bát Tự related only ~200 LOC)
- **LunarConverter.kt**: 80 LOC
- **LunarDateUtil.kt**: 169 LOC
- **Models.kt**: ~50 LOC (data classes only)
- **solar-term.json**: 278KB data file

**Estimated Core Engine**: ~750-850 LOC + 278KB data file

---

## Key Algorithms Identified

### 1. True Solar Time (TST) Correction
```
TST = LocalTime + (Longitude - 105°) × 4 minutes
```
- Adjusts for geographical longitude
- Default: 105.8°E (Hanoi)

### 2. Year Pillar Calculation
- Based on Lập Xuân (Start of Spring) solar term
- If birth before Lập Xuân → use previous year

### 3. Month Pillar Calculation
- Based on 12 solar terms (not lunar months)
- Month changes at specific solar terms (Lập Xuân, Kinh Trập, etc.)

### 4. Day Pillar Calculation
- Julian Day Number (JDN) based
- Optional 23:00 day boundary

### 5. Hour Pillar Calculation
- 2-hour periods (12 branches)
- Can calculated from Day Can using fixed formula

### 6. Ten Gods (Thập Thần) Calculation
- Based on Five Elements relationships
- Day Master vs other stems
- Considers Yin-Yang polarity

### 7. Element Balance
- Stem elements: 40 points each
- Hidden stems: weighted by Tàng Can percentages

---

## Architecture Pattern

**Current**: Android MVVM with Clean Architecture
- UI Layer (Compose)
- ViewModel Layer (State Management)
- Data Layer (Repository)
- Core Layer (Business Logic)

**Extractable**: Pure Kotlin calculation engine
- No Android dependencies
- Can be ported to any platform
- Stateless calculation functions

---

## Complexity Assessment

**Size**: Medium (~850 LOC core engine)
**Complexity**: Medium-High
- Astronomical calculations (solar terms)
- Complex mapping tables
- Multiple calculation steps with dependencies

**Migration Difficulty**: Low-Medium
- Core logic is well-isolated
- Minimal external dependencies
- Pure calculation functions (no state)
