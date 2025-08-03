package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onSplashFinished: () -> Unit
) {
    LaunchedEffect(Unit) {
        // The delay remains the same
        delay(2500)
        onSplashFinished()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // CHANGE: Use the theme's primary color for the background.
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.appetito_logo),
                contentDescription = "Appetito Logo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Appetito",
                fontSize = 40.sp,
                // CHANGE: Use the theme's onPrimary color for text on a primary background.
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showBackground = true, name = "Splash Screen - Light")
@Composable
fun SplashScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        SplashScreen(onSplashFinished = {})
    }
}

@Preview(showBackground = true, name = "Splash Screen - Dark")
@Composable
fun SplashScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        SplashScreen(onSplashFinished = {})
    }
}