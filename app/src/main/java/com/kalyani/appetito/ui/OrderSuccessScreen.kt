package ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

@Composable
fun OrderSuccessScreen(navController: NavHostController) {
    var isVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val scale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0.8f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "SuccessImageScale"
    )

    // The Column will use the Scaffold's background color, which is theme-aware.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_order_success),
            contentDescription = "Order Successful",
            modifier = Modifier
                .size(150.dp)
                .scale(scale)
        )
        Spacer(modifier = Modifier.height(32.dp))
        // Text color will be inherited from the theme's onBackground/onSurface color.
        Text(
            "Thank You!",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Your Order has been placed successfully. You can track the delivery in the 'My Orders' section.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            // CHANGE: Use onSurfaceVariant for secondary text.
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            onClick = {
                navController.navigate("main_app") {
                    popUpTo(navController.graph.id) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            // CHANGE: Use theme colors for the button.
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text("BACK TO HOME", fontWeight = FontWeight.Bold)
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Order Success - Light")
@Composable
fun OrderSuccessScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        OrderSuccessScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Order Success - Dark")
@Composable
fun OrderSuccessScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        OrderSuccessScreen(navController = rememberNavController())
    }
}