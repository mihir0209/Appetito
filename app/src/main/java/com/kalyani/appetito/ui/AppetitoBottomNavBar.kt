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
    navController: NavController
) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color(0xFF9796A1), // Default color for unselected items
        tonalElevation = 8.dp
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavTab.values().forEach { tab ->
            NavigationBarItem(
                selected = currentRoute == tab.route,
                onClick = {
                    if (currentRoute != tab.route) {
                        navController.navigate(tab.route) {
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
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFE724C),
                    selectedTextColor = Color(0xFFFE724C),
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}

enum class BottomNavTab(val route: String, val label: String, val iconRes: Int) {
    Home("home_route", "Home", R.drawable.ic_home),
    Category("category_route", "Category", R.drawable.ic_category),
    Cart("cart_route", "Cart", R.drawable.ic_cart),
    Favorites("favorites_route", "Favorites", R.drawable.ic_favorite),
    Profile("profile_route", "Profile", R.drawable.ic_profile)
}

@Preview(showBackground = true)
@Composable
fun AppetitoBottomNavBarPreview() {
    val dummyNavController = rememberNavController()
    AppetitoBottomNavBar(navController = dummyNavController)
}