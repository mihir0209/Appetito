package ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SystemAppearance(isLight: Boolean) {
    val systemUiController = rememberSystemUiController()
    SideEffect {
        // This line tells the system to use dark icons on a light background
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = isLight
        )
        // You can also control the navigation bar if needed
        systemUiController.setNavigationBarColor(
            color = Color.Transparent,
            darkIcons = isLight
        )
    }
}