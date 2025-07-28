package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    // This parameter is added to allow MainActivity to navigate away from the splash screen.
    onSplashFinished: () -> Unit
) {
    // This block runs a side-effect (the delay) once when the composable enters the screen.
    LaunchedEffect(Unit) {
        delay(2500) // Wait for 2.5 seconds
        onSplashFinished() // Call the function to trigger navigation.
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFE724C))
    ) {
        // Logo/Image placeholder
        Box(
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            // Replace with actual image resource
            Image(painter = painterResource(id = R.drawable.appetito_logo), contentDescription = "Logo")
        }
        // App Name
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 350.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Appetito",
                fontSize = 40.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// The preview remains unchanged so you can still view the UI in isolation.
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    // We call the original function with an empty lambda for the preview.
    SplashScreen(onSplashFinished = {})
}