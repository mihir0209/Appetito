package ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MainWithBottomNav(
    mainNavController: NavHostController
) {
    val nestedNavController = rememberNavController()

    // The Scaffold correctly uses the theme's background color by default.
    // The AppetitoBottomNavBar has already been made theme-aware.
    // No color changes are needed here.
    Scaffold(
        bottomBar = { AppetitoBottomNavBar(navController = nestedNavController) }
    ) { innerPadding ->
        NavHost(
            navController = nestedNavController,
            startDestination = BottomNavTab.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavTab.Home.route) {
                HomeScreen(nestedNavController = nestedNavController, mainNavController = mainNavController)
            }
            composable(BottomNavTab.Category.route) {
                CategoryScreen(nestedNavController = nestedNavController, mainNavController = mainNavController)
            }
            composable(BottomNavTab.Cart.route) {
                CartScreen(nestedNavController = nestedNavController, mainNavController = mainNavController)
            }
            composable(BottomNavTab.Favorites.route) {
                FavoritesFoodItemsScreen(nestedNavController = nestedNavController)
            }
            composable(BottomNavTab.Profile.route) {
                ProfileScreen(mainNavController = mainNavController)
            }
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Main Layout - Light Theme")
@Composable
fun MainWithBottomNavPreviewLight() {
    // CHANGE: Wrap preview in the theme to see it correctly.
    AppetitoTheme(useDarkTheme = false) {
        MainWithBottomNav(mainNavController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Main Layout - Dark Theme")
@Composable
fun MainWithBottomNavPreviewDark() {
    // CHANGE: Add a dark theme preview.
    AppetitoTheme(useDarkTheme = true) {
        MainWithBottomNav(mainNavController = rememberNavController())
    }
}