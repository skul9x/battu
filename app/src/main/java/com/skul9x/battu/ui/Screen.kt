package com.skul9x.battu.ui

/**
 * Navigation routes for BatTu app
 * 
 * Sealed class representing all possible screens in the application
 */
sealed class Screen(val route: String) {
    /**
     * Input Screen - User enters birth date/time information
     */
    data object Input : Screen("input")
    
    /**
     * Chart Screen - Displays the Four Pillars (Tứ Trụ) chart
     * @param userId Optional user ID for saved charts
     */
    data object Chart : Screen("chart") {
        fun createRoute(userId: String? = null): String {
            return if (userId != null) "chart/$userId" else "chart"
        }
    }
    
    /**
     * Result Screen - Shows AI interpretation results
     */
    data object Result : Screen("result")
    
    /**
     * History Screen - List of previously calculated charts
     * (Phase 05 - Future implementation)
     */
    data object History : Screen("history")
    
    /**
     * Settings Screen - App preferences
     * (Phase 05 - Future implementation)
     */
    data object Settings : Screen("settings")
}

/**
 * Bottom navigation items
 * Used for main navigation bar
 */
data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: String // Using emoji for now, can be replaced with vector icons
)

/**
 * Default bottom navigation items
 */
val bottomNavItems = listOf(
    BottomNavItem(Screen.Input, "Nhập liệu", "📝"),
    BottomNavItem(Screen.Chart, "Lá Số", "🎴"),
    BottomNavItem(Screen.Result, "Luận Giải", "🤖"),
    BottomNavItem(Screen.History, "Lịch Sử", "📚")
)
