# Migration Strategy

## Strategy: Direct Rewrite

### Approach
Clone the Bát Tự calculation engine from TuViAndroid-BatTu to a new application while preserving the core algorithms and business logic.

### Rationale
- **Proven Logic**: Source code is tested and working
- **Minimal Risk**: Direct copy reduces bugs
- **Fast Development**: No need to redesign algorithms
- **Easy Verification**: Can compare outputs with source

---

## What to Clone

### Core Files (Must Clone)
1. ✅ **BaZiLogic.kt** (251 LOC)
   - Main calculation engine
   - All algorithms intact
   
2. ✅ **Constants.kt** (Partial - ~200 LOC)
   - Bát Tự related constants only
   - Remove Tử Vi specific data
   - Keep: Can, Chi, Elements, Tàng Can, Ten Gods
   
3. ✅ **LunarConverter.kt** (80 LOC)
   - Solar-Lunar conversion
   - Can Chi helpers
   
4. ✅ **LunarDateUtil.kt** (169 LOC)
   - JDN calculation
   - Lunar table lookup
   
5. ✅ **Models.kt** (Partial - ~50 LOC)
   - UserInput, Pillar, BaZiData, TenGods
   - Remove Tử Vi specific models
   
6. ✅ **solar-term.json** (278KB)
   - Complete data file
   - No modifications needed

**Total to Clone**: ~750 LOC + 278KB data

---

## What NOT to Clone

### Excluded Components
- ❌ TuViLogic.kt (Tử Vi specific)
- ❌ GeminiClient.kt (Will create new AI integration)
- ❌ UI Layer (Jetpack Compose - platform specific)
- ❌ ViewModel Layer (Android specific)
- ❌ Database Layer (Room - will use different storage)
- ❌ All Tử Vi constants (stars, palaces, etc.)

---

## Migration Phases

### Phase 1: Setup Project Structure
**Goal**: Create new project with proper structure

**Tasks**:
- Create project directory
- Setup build system (Gradle/Maven/npm - TBD)
- Create folder structure
- Initialize version control

**Deliverables**:
- Empty project skeleton
- Build configuration
- README.md

---

### Phase 2: Clone Core Engine
**Goal**: Copy and adapt calculation engine

**Tasks**:
- Copy BaZiLogic.kt
- Copy Constants.kt (filtered)
- Copy LunarConverter.kt
- Copy LunarDateUtil.kt
- Copy Models.kt (filtered)
- Copy solar-term.json
- Remove Android dependencies
- Adjust imports

**Deliverables**:
- Standalone calculation engine
- No compilation errors

---

### Phase 3: Platform Adaptation
**Goal**: Adapt code for target platform

**Tasks**:
- Replace Android JSON parser (if needed)
- Adjust file I/O for solar-term.json
- Update date/time handling (if needed)
- Add platform-specific utilities

**Deliverables**:
- Platform-compatible code
- Compiles and runs

---

### Phase 4: Create API Layer
**Goal**: Expose calculation functions

**Tasks**:
- Create input validation
- Create output formatting
- Add error handling
- Create JSON serialization

**Deliverables**:
- Clean API interface
- JSON input/output support

---

### Phase 5: Build UI Layer
**Goal**: Create user interface

**Tasks**:
- Design input form
- Design result display
- Implement UI components
- Connect to calculation engine

**Deliverables**:
- Working UI
- User can input data and see results

---

### Phase 6: AI Integration
**Goal**: Add AI interpretation

**Tasks**:
- Choose AI service (OpenAI/Gemini/Claude)
- Create AI client
- Format Bát Tự data for AI
- Display AI analysis

**Deliverables**:
- AI interpretation feature
- Formatted analysis display

---

### Phase 7: Testing & Verification
**Goal**: Ensure correctness

**Tasks**:
- Unit tests for calculations
- Compare outputs with source app
- Test edge cases
- User acceptance testing

**Deliverables**:
- Test suite
- Verified accuracy

---

### Phase 8: Deployment
**Goal**: Make app available

**Tasks**:
- Build production version
- Deploy to hosting (if web)
- Create installer (if desktop)
- Write user documentation

**Deliverables**:
- Deployed application
- User guide

---

## Code Modification Strategy

### Minimal Changes Approach

#### Keep Unchanged:
- All calculation algorithms
- All formulas
- All data constants
- All business logic

#### Modify Only:
- Import statements
- Platform-specific I/O
- JSON parsing (if needed)
- Date/time utilities (if needed)

#### Example Modifications:

**Before (Android)**:
```kotlin
import org.json.JSONObject
import java.util.Calendar
import java.util.TimeZone
```

**After (Platform-specific)**:
```kotlin
// Web (Kotlin/JS)
import kotlinx.serialization.json.*
import kotlin.js.Date

// JVM (Kotlin/JVM)
import com.google.gson.JsonObject
import java.time.LocalDateTime
import java.time.ZoneId
```

---

## Risk Mitigation

### Risk 1: Platform Incompatibility
**Mitigation**: 
- Test on target platform early
- Use platform-agnostic libraries
- Abstract platform-specific code

### Risk 2: Calculation Errors
**Mitigation**:
- Copy code exactly
- Compare outputs with source
- Create comprehensive test cases

### Risk 3: Missing Dependencies
**Mitigation**:
- Document all dependencies
- Find platform equivalents
- Test incrementally

### Risk 4: Data File Loading
**Mitigation**:
- Test solar-term.json loading early
- Handle file not found errors
- Provide fallback data

---

## Success Criteria

### Must Have:
✅ All 10 core features working
✅ Calculation accuracy matches source
✅ No data loss or corruption
✅ Handles all input edge cases

### Should Have:
✅ Clean, maintainable code
✅ Good error messages
✅ Fast performance
✅ User-friendly interface

### Nice to Have:
✅ Automated tests
✅ Documentation
✅ Export features
✅ Multiple languages

---

## Timeline Estimation

### Small Project (Web/Desktop Simple UI)
- Phase 1-2: 1 day
- Phase 3-4: 1 day
- Phase 5-6: 2 days
- Phase 7-8: 1 day
**Total**: ~5 days

### Medium Project (Full-featured App)
- Phase 1-2: 2 days
- Phase 3-4: 2 days
- Phase 5-6: 3 days
- Phase 7-8: 2 days
**Total**: ~9 days

### Large Project (Multi-platform)
- Phase 1-2: 3 days
- Phase 3-4: 3 days
- Phase 5-6: 5 days
- Phase 7-8: 4 days
**Total**: ~15 days

---

## Next Steps

### User Decision Required:

1. **Target Platform**:
   - [ ] Web Application (HTML + Backend)
   - [ ] Desktop Application (Electron/Tauri)
   - [ ] Mobile Application (Android/iOS)
   - [ ] CLI Tool (Terminal)

2. **Backend Language** (if applicable):
   - [ ] Kotlin/JVM (minimal changes)
   - [ ] Python (rewrite in Python)
   - [ ] Node.js/TypeScript (rewrite in JS/TS)
   - [ ] Go (rewrite in Go)

3. **Frontend Framework** (if applicable):
   - [ ] Plain HTML/CSS/JS
   - [ ] React
   - [ ] Vue
   - [ ] Svelte

4. **AI Service**:
   - [ ] OpenAI (GPT-4)
   - [ ] Google Gemini
   - [ ] Anthropic Claude
   - [ ] Other

### After User Decides:
→ Generate detailed implementation plan
→ Create phase-specific task files
→ Begin Phase 1 execution
