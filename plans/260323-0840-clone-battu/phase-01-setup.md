# Phase 01: Project Setup & Dependencies
Status: ⬜ Pending
Dependencies: Không

## Objective
Cấu hình project Android hiện có thành Jetpack Compose app với đầy đủ dependencies cần thiết.

## Context
Project `/home/skul9x/Desktop/Test_code/BatTu` đã có khung sẵn:
- Package: `com.skul9x.battu`
- Min SDK: 24, Target SDK: 36
- Hiện tại dùng XML layout (AppCompatActivity) → Cần chuyển sang Compose

## Requirements

### Functional
- [ ] Project build thành công với Compose
- [ ] MainActivity chạy được với Compose UI
- [ ] Tất cả dependencies hoạt động

### Non-Functional
- [ ] Build time hợp lý (< 2 phút)
- [ ] Min SDK 24 compatibility

## Implementation Steps

### 1. Cấu hình Gradle plugins
- [ ] Thêm Kotlin plugin vào `build.gradle.kts` (root)
- [ ] Thêm `kotlin-android` và `kotlinx-serialization` plugin vào app module

### 2. Thêm Dependencies vào `app/build.gradle.kts`
- [ ] **Compose BOM + Core:**
  ```kotlin
  implementation(platform("androidx.compose:compose-bom:2024.12.01"))
  implementation("androidx.compose.material3:material3")
  implementation("androidx.compose.ui:ui")
  implementation("androidx.compose.ui:ui-tooling-preview")
  debugImplementation("androidx.compose.ui:ui-tooling")
  ```
- [ ] **Activity Compose:**
  ```kotlin
  implementation("androidx.activity:activity-compose:1.9.3")
  ```
- [ ] **ViewModel + Lifecycle:**
  ```kotlin
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
  implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.7")
  ```
- [ ] **Navigation Compose:**
  ```kotlin
  implementation("androidx.navigation:navigation-compose:2.8.5")
  ```
- [ ] **Kotlinx Serialization:**
  ```kotlin
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.3")
  ```
- [ ] **Gemini AI SDK:**
  ```kotlin
  implementation("com.google.ai.client.generativeai:generativeai:0.9.0")
  ```
- [ ] **Room Database:**
  ```kotlin
  implementation("androidx.room:room-runtime:2.6.1")
  implementation("androidx.room:room-ktx:2.6.1")
  kapt("androidx.room:room-compiler:2.6.1")
  ```

### 3. Enable Compose trong build.gradle.kts
- [ ] Thêm `buildFeatures { compose = true }`
- [ ] Set `composeOptions { kotlinCompilerExtensionVersion = "1.5.15" }`
- [ ] Thêm `kotlinOptions { jvmTarget = "11" }`

### 4. Chuyển MainActivity sang Compose
- [ ] Xóa `setContentView(R.layout.activity_main)` 
- [ ] Thêm `setContent { BatTuApp() }`
- [ ] Tạo `@Composable fun BatTuApp()` placeholder

### 5. Tạo folder structure
- [ ] `com.skul9x.battu.core/` — Core logic
- [ ] `com.skul9x.battu.data/` — Models, Database
- [ ] `com.skul9x.battu.ui/screens/` — Screens
- [ ] `com.skul9x.battu.ui/components/` — Reusable UI
- [ ] `com.skul9x.battu.ui/theme/` — Theme
- [ ] `com.skul9x.battu.ui/viewmodel/` — ViewModels
- [ ] `com.skul9x.battu.ai/` — AI integration

### 6. Cấu hình API Key
- [ ] Thêm `GEMINI_API_KEY=` vào `local.properties`
- [ ] Config `buildConfigField` trong build.gradle để inject key
- [ ] Đảm bảo `local.properties` nằm trong `.gitignore`

### 7. Cấu hình Internet Permission
- [ ] Thêm `<uses-permission android:name="android.permission.INTERNET"/>` vào `AndroidManifest.xml`

### 8. Build & Verify
- [ ] `./gradlew assembleDebug` thành công
- [ ] App chạy trên emulator/device hiển thị Compose UI

## Files to Create/Modify
| File | Action | Mô tả |
|------|--------|-------|
| `build.gradle.kts` (root) | MODIFY | Thêm Kotlin & serialization plugins |
| `app/build.gradle.kts` | MODIFY | Compose, dependencies, buildConfig |
| `MainActivity.kt` | MODIFY | Chuyển sang Compose |
| `AndroidManifest.xml` | MODIFY | Thêm INTERNET permission |
| `local.properties` | MODIFY | Thêm API key placeholder |

## Test Criteria
- [ ] `./gradlew assembleDebug` build success
- [ ] App launch hiển thị "Bát Tự" text trên Compose screen
- [ ] Không có dependency conflict

## Notes
- Giữ lại XML layout cũ để backup, nhưng không sử dụng
- SDK versions cần check compatibility với Compose BOM version

---
Next Phase: [phase-02-core-logic.md](./phase-02-core-logic.md)
