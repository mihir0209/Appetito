package ui

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

@Composable
fun AppetitoBottomNavBar(
    // The NavController for the nested graph is now the only parameter needed.
    navController: NavController
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF9796A1), // Default color for unselected items
        tonalElevation = 8.dp
    ) {
        // This line gets the current route from the NavController.
        // It's a "state" so the UI will automatically update when it changes.
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        // We iterate through our enum values to create each item.
        BottomNavTab.values().forEach { tab ->
            NavigationBarItem(
                // The item is 'selected' if its route matches the current route.
                selected = currentRoute == tab.route,
                // The onClick lambda now handles navigation directly.
                onClick = {
                    // Prevent navigating to the same screen again.
                    if (currentRoute != tab.route) {
                        navController.navigate(tab.route) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large back stack.
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
                // Custom colors to match a typical app design.
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFE724C),
                    selectedTextColor = Color(0xFFFE724C),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent // Often, the color change is enough.
                )
            )
        }
    }
}

// The enum is updated to include the 'route' string, which is crucial for navigation.
enum class BottomNavTab(val route: String, val label: String, val iconRes: Int) {
    Home("home_route", "Home", R.drawable.ic_home),
    Category("category_route", "Category", R.drawable.ic_category),
    Cart("cart_route", "Cart", R.drawable.ic_cart),
    Favorites("favorites_route", "Favorites", R.drawable.ic_favorite),
    Profile("profile_route", "Profile", R.drawable.ic_profile)
}

// The preview is updated to work with the new function signature.
@Preview(showBackground = true)
@Composable
fun AppetitoBottomNavBarPreview() {
    val dummyNavController = rememberNavController()
    AppetitoBottomNavBar(navController = dummyNavController)
}