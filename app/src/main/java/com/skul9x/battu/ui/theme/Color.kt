package com.skul9x.battu.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Ngũ Hành (Five Elements) Color System
 * Based on traditional Eastern philosophy and BaZi chart visualization
 */

// === NGŨ HÀNH COLORS (Primary) ===
/** Mộc (Wood) - Xanh lá */
val ElementWood = Color(0xFF4CAF50)
val ElementWoodLight = Color(0xFF81C784)
val ElementWoodDark = Color(0xFF388E3C)

/** Hỏa (Fire) - Đỏ */
val ElementFire = Color(0xFFF44336)
val ElementFireLight = Color(0xFFE57373)
val ElementFireDark = Color(0xFFD32F2F)

/** Thổ (Earth) - Vàng nâu */
val ElementEarth = Color(0xFFFF9800)
val ElementEarthLight = Color(0xFFFFB74D)
val ElementEarthDark = Color(0xFFF57C00)

/** Kim (Metal) - Trắng xám */
val ElementMetal = Color(0xFF9E9E9E)
val ElementMetalLight = Color(0xFFBDBDBD)
val ElementMetalDark = Color(0xFF616161)

/** Thủy (Water) - Xanh dương */
val ElementWater = Color(0xFF2196F3)
val ElementWaterLight = Color(0xFF64B5F6)
val ElementWaterDark = Color(0xFF1976D2)

// === EASTERN TRADITIONAL THEME (Light Mode) ===
/** Background - Giấy xuyến chỉ (Rice paper) */
val BackgroundCream = Color(0xFFFFFBF0)
val BackgroundPaper = Color(0xFFFAF8F3)
val SurfaceBeige = Color(0xFFF5F1E8)

/** Text Colors */
val TextPrimaryInk = Color(0xFF2C2416)
val TextSecondaryBrown = Color(0xFF5D4E37)
val TextTertiary = Color(0xFF8B7355)

/** Accent Colors */
val AccentGold = Color(0xFFD4AF37)
val AccentRed = Color(0xFFDC143C)
val DividerBrown = Color(0xFFD7CCC8)

// === DARK MODE (Optional - Deep Charcoal/Gold) ===
val BackgroundDark = Color(0xFF1C1B1F)
val SurfaceDark = Color(0xFF2B2930)
val TextDarkPrimary = Color(0xFFE6E1E5)
val AccentGoldDark = Color(0xFFFFD700)

// === MATERIAL 3 SEMANTIC COLORS ===
val Primary = ElementFire
val OnPrimary = Color.White
val PrimaryContainer = ElementFireLight
val OnPrimaryContainer = ElementFireDark

val Secondary = AccentGold
val OnSecondary = Color.White
val SecondaryContainer = Color(0xFFFFE082)
val OnSecondaryContainer = Color(0xFF5D4E37)

val Tertiary = ElementWood
val OnTertiary = Color.White
val TertiaryContainer = ElementWoodLight
val OnTertiaryContainer = ElementWoodDark

val Error = Color(0xFFB3261E)
val OnError = Color.White
val ErrorContainer = Color(0xFFF9DEDC)
val OnErrorContainer = Color(0xFF410E0B)

val Background = BackgroundCream
val OnBackground = TextPrimaryInk

val Surface = BackgroundPaper
val OnSurface = TextPrimaryInk
val SurfaceVariant = SurfaceBeige
val OnSurfaceVariant = TextSecondaryBrown

val Outline = DividerBrown
val OutlineVariant = Color(0xFFE8DED3)

/**
 * Helper function to get element color by name
 * @param element "Mộc", "Hỏa", "Thổ", "Kim", "Thủy"
 * @return Corresponding Color
 */
fun getElementColor(element: String): Color {
    return when (element) {
        "Mộc", "Wood" -> ElementWood
        "Hỏa", "Fire" -> ElementFire
        "Thổ", "Earth" -> ElementEarth
        "Kim", "Metal" -> ElementMetal
        "Thủy", "Water" -> ElementWater
        else -> Color.Gray
    }
}

/**
 * Helper function to get element color with light/dark variant
 * @param element Element name
 * @param variant "light", "normal", "dark"
 */
fun getElementColorVariant(element: String, variant: String = "normal"): Color {
    val baseColor = when (element) {
        "Mộc", "Wood" -> Triple(ElementWoodLight, ElementWood, ElementWoodDark)
        "Hỏa", "Fire" -> Triple(ElementFireLight, ElementFire, ElementFireDark)
        "Thổ", "Earth" -> Triple(ElementEarthLight, ElementEarth, ElementEarthDark)
        "Kim", "Metal" -> Triple(ElementMetalLight, ElementMetal, ElementMetalDark)
        "Thủy", "Water" -> Triple(ElementWaterLight, ElementWater, ElementWaterDark)
        else -> Triple(Color.LightGray, Color.Gray, Color.DarkGray)
    }
    
    return when (variant) {
        "light" -> baseColor.first
        "dark" -> baseColor.third
        else -> baseColor.second
    }
}
