package com.skul9x.battu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.skul9x.battu.ui.Screen
import com.skul9x.battu.ui.bottomNavItems
import com.skul9x.battu.ui.screens.*
import com.skul9x.battu.ui.theme.BatTuTheme
import com.skul9x.battu.ui.viewmodel.BatTuViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BatTuTheme {
                BatTuApp()
            }
        }
    }
}

@Composable
fun BatTuApp() {
    val navController = rememberNavController()
    val viewModel: BatTuViewModel = viewModel()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            // Only show bottom bar on main screens
            val showBottomBar = bottomNavItems.any { it.screen.route == currentDestination?.route }
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Text(item.icon) }, // Using emoji for icons
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    // Pop up to the start destination of the graph to
                                    // avoid building up a large stack of destinations
                                    // on the back stack as users select items
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    // Avoid multiple copies of the same destination when
                                    // reselecting the same item
                                    launchSingleTop = true
                                    // Restore state when reselecting a previously selected item
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Input.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Input.route) {
                InputScreen(navController = navController, viewModel = viewModel)
            }
            
            composable(Screen.Chart.route) {
                ChartScreen(navController = navController, viewModel = viewModel)
            }
            
            composable(Screen.Result.route) {
                ResultScreen(
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
            
            composable(Screen.Settings.route) {
                SettingsScreen(
                    onNavigateBack = { navController.popBackStack() },
                    viewModel = viewModel
                )
            }
            
            composable(Screen.History.route) {
                HistoryScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}