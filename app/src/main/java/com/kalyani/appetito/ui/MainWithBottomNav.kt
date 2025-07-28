package ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavHostController

// Note: The parameters 'selectedTab' and 'navController' have been removed.
// This composable is now self-contained and manages its own state.
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainWithBottomNav(
    // We add the main NavController as a parameter to handle navigations
    // that need to go *outside* of the bottom nav scope (e.g., to a settings screen).
    mainNavController: NavHostController
) {
    // This NavController is ONLY for the screens inside the bottom navigation bar.
    val nestedNavController = rememberAnimatedNavController()

    // Scaffold provides the basic structure for a screen with a bottom bar.
    Scaffold(
        bottomBar = {
            // We pass the nestedNavController to the bottom bar so it knows
            // which item is selected and can navigate when an item is clicked.
            AppetitoBottomNavBar(navController = nestedNavController)
        }
    ) { innerPadding ->
        // This is the nested navigation host. It swaps the content of the screen.
        AnimatedNavHost(
            navController = nestedNavController,
            startDestination = BottomNavTab.Home.route, // The app starts on the Home tab.
            modifier = Modifier.padding(innerPadding) // Important: applies padding to avoid overlap with the bottom bar.
        ) {
            composable(BottomNavTab.Home.route) {
                // Here we call the REAL HomeScreen with the side-menu animation logic.
                // We pass the mainNavController so the HomeScreen can navigate to other pages
                // like MyOrders, AddAddress, etc., which are outside the bottom nav.
                HomeScreen(
                    nestedNavController = nestedNavController,
                    mainNavController = mainNavController
                )
            }
            composable(BottomNavTab.Category.route) {
                CategoryScreen()
            }
            composable(BottomNavTab.Cart.route) {
                CartScreen()
            }
            composable(BottomNavTab.Favorites.route) {
                FavoritesFoodItemsScreen()
            }
            composable(BottomNavTab.Profile.route) {
                ProfileScreen(mainNavController = mainNavController)
            }
        }
    }
}

// This is the new, working preview for this component.
// It creates a dummy NavController so Android Studio can render the UI.
@Preview(showBackground = true)
@Composable
fun MainWithBottomNavPreview() {
    // We use rememberNavController() here just for the preview's sake.
    val dummyNavController = rememberNavController()
    MainWithBottomNav(mainNavController = dummyNavController)
}