# Phase 07: Testing & Verification
Status: ⬜ Pending
Dependencies: Phase 01, 02, 03, 04, 05, 06

## Objective
Viết test tổng hợp cho toàn bộ tính năng mới. Verify bằng lá số Nguyễn Thị Ngọc (23/10/1992, 17:30, Nữ) đối chiếu với ảnh tham chiếu hocvienlyso.org.

## Test File
**[NEW]** `app/src/test/java/com/skul9x/battu/AdvancedFeaturesTest.kt`

## Test Cases

### 1. Phục Ngâm
```kotlin
@Test fun testPhucNgam_ThanThan() {
    // Thân-Thân → Phục Ngâm, NOT Bán Hợp
    val pillars = mapOf(
        "Năm" to createPillar("Thân"),
        "Ngày" to createPillar("Thân"),
        ...
    )
    val interactions = logic.calculateInteractions(pillars)
    assertTrue(interactions.any { it.typeName == "Phục Ngâm" })
    assertFalse(interactions.any { it.typeName.contains("Bán Hợp") && 
        it.description.contains("Thân - Thân") })
}
```

### 2. Tuần Không
```kotlin
@Test fun testXunKong_NhamThan() {
    val (v1, v2) = BaZiConstants.getXunKong("Nhâm", "Thân")
    assertEquals("Tuất", v1)
    assertEquals("Hợi", v2)
}

@Test fun testXunKong_GiapTuat() {
    val (v1, v2) = BaZiConstants.getXunKong("Giáp", "Tuất")
    assertEquals("Thân", v1)
    assertEquals("Dậu", v2)
}
```

### 3. Thai Nguyên
```kotlin
@Test fun testFetalOrigin_CanhTuat() {
    // Tháng Canh Tuất → Thai Nguyên = Tân Sửu
    val result = logic.calculateFetalOrigin(6, 10) // Canh=6, Tuất=10
    assertEquals("Tân", result.stem)
    assertEquals("Sửu", result.branch)
}
```

### 4. Mệnh Cung
```kotlin
@Test fun testLifePalace_Ngoc() {
    // Đối chiếu ảnh: Mệnh Cung = Canh Tuất
    val result = logic.calculateLifePalace(...)
    assertEquals("Canh", result.stem)
    assertEquals("Tuất", result.branch)
}
```

### 5. Đào Hoa + Thiên Y
```kotlin
@Test fun testShenSha_Ngoc_DaoHoa() {
    // Ngày Thân → Đào Hoa = Dậu → Giờ Dậu → match
    val result = logic.calculateBaZi(ngocInput)
    assertTrue(result.shenShaList.any { it.name == "Đào Hoa" && it.pillar == "Giờ" })
}

@Test fun testShenSha_Ngoc_ThienY() {
    // Tháng Tuất → Thiên Y = Dậu → Giờ Dậu → match
    val result = logic.calculateBaZi(ngocInput)
    assertTrue(result.shenShaList.any { it.name == "Thiên Y" && it.pillar == "Giờ" })
}
```

### 6. Thập Thần Tàng Can
```kotlin
@Test fun testHiddenStem_TenGod_Tuat() {
    // Nhật chủ Nhâm, Chi Tuất: Mậu=Thất Sát, Tân=Chính Ấn, Đinh=Chính Tài
    val result = logic.calculateBaZi(ngocInput)
    val monthHS = result.month.hiddenStems
    assertEquals("Thất Sát", monthHS[0].tenGod) // Mậu
    assertEquals("Chính Ấn", monthHS[1].tenGod) // Tân
    assertEquals("Chính Tài", monthHS[2].tenGod) // Đinh
}
```

### 7. Lưu Niên
```kotlin
@Test fun testAnnualPillar_2026() {
    // 2026: (2026-4)%10=2→Bính, (2026-4)%12=6→Ngọ → Bính Ngọ
    val canIdx = ((2026 - 4) % 10 + 10) % 10
    val chiIdx = ((2026 - 4) % 12 + 12) % 12
    assertEquals("Bính", BaZiConstants.THIEN_CAN[canIdx])
    assertEquals("Ngọ", BaZiConstants.DIA_CHI[chiIdx])
}
```

### 8. Regression Tests
```kotlin
@Test fun testExistingTests_NoRegression() {
    // Chạy toàn bộ test hiện có đảm bảo không break
}
```

## Execution Commands

```bash
# Run ALL tests
cd /home/skul9x/Desktop/Test_code/BatTu
./gradlew test --tests "com.skul9x.battu.*" 2>&1 | tail -50

# Run ONLY new tests
./gradlew test --tests "com.skul9x.battu.AdvancedFeaturesTest" 2>&1 | tail -50

# Run specific test
./gradlew test --tests "com.skul9x.battu.AdvancedFeaturesTest.testXunKong_NhamThan"
```

## Ngọc Test Input
```kotlin
val ngocInput = UserInput(
    name = "Nguyễn Thị Ngọc",
    solarDay = 23,
    solarMonth = 10,
    solarYear = 1992,
    hour = 17,  // 17:30 → giờ Dậu
    gender = Gender.NU
)
```

## Expected Results (Đối chiếu ảnh tham chiếu)
| Feature | Expected | Source |
|---------|----------|--------|
| Tuần Không (Nhật) | Tuất, Hợi | Ảnh tham chiếu |
| Tuần Không (Niên) | Tuất, Hợi | Ảnh tham chiếu |
| Thai Nguyên | Tân Sửu | Ảnh tham chiếu |
| Mệnh Cung | Canh Tuất | Ảnh tham chiếu |
| Đào Hoa | Giờ (Dậu) | Ảnh tham chiếu |
| Thiên Y | Giờ (Dậu) | Ảnh tham chiếu |
| Thập Ác Đại Bại | Năm + Ngày | Ảnh tham chiếu |
| Phục Ngâm | Năm-Ngày (Thân-Thân) | Logic analysis |
| Lưu Niên 2026 | Bính Ngọ | Ảnh tham chiếu |

## Files to Create
- `app/src/test/java/com/skul9x/battu/AdvancedFeaturesTest.kt` (NEW)

## Post-Test
- [ ] All tests GREEN
- [ ] Generate sample JSON prompt cho Ngọc → manual review
- [ ] Update `.brain/session.json` và `brain.json`
- [ ] `/save-brain` để lưu context

---
**🏁 End of Plan. Ready for `/code phase-01`!**
