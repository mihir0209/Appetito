package com.kalyani.appetito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.*

@Composable
fun AppetitoApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash",
        enterTransition = { fadeIn(animationSpec = tween(400)) },
        exitTransition = { fadeOut(animationSpec = tween(400)) }
    ) {
        composable("splash") { SplashScreen(onSplashFinished = { navController.navigate("welcome") { popUpTo("splash") { inclusive = true } } }) }
        composable("welcome") { WelcomeScreen(onSkip = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onSignIn = { navController.navigate("login") }, onGoogle = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onFacebook = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onEmailOrPhone = { navController.navigate("signup") }) }
        composable("login") { LoginScreen(onLoginSuccess = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onNavigateToSignUp = { navController.navigate("signup") }, onForgotPassword = { navController.navigate("reset_password") }, onFacebookLogin = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onGoogleLogin = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }) }
        composable("signup") { SignUpScreen(onSignUp = { navController.navigate("verification") }, onNavigateToLogin = { navController.navigate("login") }, onFacebookSignUp = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onGoogleSignUp = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }) }
        composable("verification") { VerificationCodeScreen(onVerify = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onResend = { }) }
        composable("reset_password") { ResetPasswordScreen(onSendNewPassword = { navController.popBackStack() }) }
        composable("main_app") { MainWithBottomNav(mainNavController = navController) }
        composable("orders") { MyOrdersUpcomingScreen(navController = navController) }
        composable("add_address") { AddNewAddressScreen(navController = navController) }
        composable("reviews") { ReviewsScreen(navController = navController) }
        composable("review_restaurant") { ReviewRestaurantScreen(navController = navController) }
        composable("rating") { RatingScreen(navController = navController) }
        composable("food_details/{itemId}") { backStackEntry -> val itemId = backStackEntry.arguments?.getString("itemId"); FoodDetailsScreen(itemId = itemId, navController = navController) }
        composable("payment_methods") { PaymentMethodsScreen(navController = navController) }
        composable("contact_us") { ContactUsScreen(navController = navController) }
        composable("settings") { SettingsScreen(navController = navController) }
        composable("help") { HelpsFaqsScreen(navController = navController) }
        composable("checkout") { CheckoutScreen(navController = navController) }
        composable("order_success") {
            OrderSuccessScreen(navController = navController)
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // THE FIX: The entire app is now wrapped in our theme.
            val context = LocalContext.current
            val themeViewModel: ThemeViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                    return ThemeViewModel(context) as T
                }
            })
            val currentTheme by themeViewModel.theme

            val useDarkTheme = when (currentTheme) {
                Theme.Light -> false
                Theme.Dark -> true
                Theme.System -> isSystemInDarkTheme()
            }

            AppetitoTheme(useDarkTheme = useDarkTheme) {
                AppetitoApp()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppetitoApp()
}