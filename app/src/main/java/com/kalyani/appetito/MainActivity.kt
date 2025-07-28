package com.kalyani.appetito

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import ui.*

@OptIn(ExperimentalAnimationApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppetitoApp()
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppetitoApp() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = "splash"
    ) {
        // ... (all other routes are correct) ...
        composable("splash") { SplashScreen(onSplashFinished = { navController.navigate("welcome") { popUpTo("splash") { inclusive = true } } }) }
        composable("welcome") { WelcomeScreen(onSkip = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onSignIn = { navController.navigate("login") }, onGoogle = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onFacebook = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onEmailOrPhone = { navController.navigate("signup") }) }
        composable("login") { LoginScreen(onLoginSuccess = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onNavigateToSignUp = { navController.navigate("signup") }, onForgotPassword = { navController.navigate("reset_password") }, onFacebookLogin = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onGoogleLogin = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }) }
        composable("signup") { SignUpScreen(onSignUp = { navController.navigate("verification") }, onNavigateToLogin = { navController.navigate("login") }, onFacebookSignUp = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onGoogleSignUp = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }) }
        composable("verification") { VerificationCodeScreen(onVerify = { navController.navigate("main_app") { popUpTo("welcome") { inclusive = true } } }, onResend = { }) }
        composable("reset_password") { ResetPasswordScreen(onSendNewPassword = { navController.popBackStack() }) }
        composable("main_app") { MainWithBottomNav(mainNavController = navController) }
        composable("orders") { MyOrdersUpcomingScreen() }
        composable("add_address") { AddNewAddressScreen() }
        composable("reviews") { ReviewsScreen() }
        composable("review_restaurant") { ReviewRestaurantScreen() }
        composable("rating") { RatingScreen() }

        // THE FINAL FIX IS HERE:
        composable("food_details/{itemId}") { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            // Pass both the itemId AND the navController to the screen.
            FoodDetailsScreen(
                itemId = itemId,
                navController = navController
            )
        }
    }
}