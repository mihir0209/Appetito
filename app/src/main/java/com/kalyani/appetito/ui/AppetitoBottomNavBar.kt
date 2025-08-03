package ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

@Composable
fun AppetitoBottomNavBar(
    navController: NavController
) {
    NavigationBar(
        // CHANGE: Use theme's surface color for the container.
        containerColor = MaterialTheme.colorScheme.surface,
        // The content color will be determined by the NavigationBarItem colors.
        tonalElevation = 8.dp // Elevation can remain for a shadow effect.
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavTab.values().forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = {
                    if (currentRoute != tab.route) {
                        navController.navigate(tab.route) {
                            // Standard navigation logic to prevent building up a large back stack.
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = tab.iconRes),
                        contentDescription = tab.label
                    )
                },
                label = { Text(tab.label) },
                // CHANGE: Use theme colors for all item states.
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    // Keeping the indicator transparent as per the original design.
                    indicatorColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

// Enum remains unchanged as it defines the static properties of the tabs.
enum class BottomNavTab(val route: String, val label: String, val iconRes: Int) {
    Home("home_route", "Home", R.drawable.ic_home),
    Category("category_route", "Category", R.drawable.ic_category),
    Cart("cart_route", "Cart", R.drawable.ic_cart),
    Favorites("favorites_route", "Favorites", R.drawable.ic_favorite),
    Profile("profile_route", "Profile", R.drawable.ic_profile)
}

// --- Previews ---

@Preview(showBackground = true, name = "Bottom Nav Bar - Light Theme")
@Composable
fun AppetitoBottomNavBarPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        val dummyNavController = rememberNavController()
        AppetitoBottomNavBar(navController = dummyNavController)
    }
}

@Preview(showBackground = true, name = "Bottom Nav Bar - Dark Theme")
@Composable
fun AppetitoBottomNavBarPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        val dummyNavController = rememberNavController()
        AppetitoBottomNavBar(navController = dummyNavController)
    }
}