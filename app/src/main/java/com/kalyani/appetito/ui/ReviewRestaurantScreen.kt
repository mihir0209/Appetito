package ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewRestaurantScreen(navController: NavHostController) {
    var rating by remember { mutableStateOf(4) }
    var review by remember { mutableStateOf("") }
    val ratingText = when (rating) {
        1 -> "Poor"
        2 -> "Fair"
        3 -> "Average"
        4 -> "Good"
        5 -> "Excellent"
        else -> ""
    }
    // Using a sample restaurant from the data provider
    val restaurant = DemoDataProvider.featuredRestaurants.first()

    Scaffold(
        // The Scaffold will apply the theme's background color
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()), // Make the column scrollable
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.CenterStart)
                        .shadow(elevation = 8.dp, shape = CircleShape)
                        .clickable { navController.popBackStack() },
                    // CHANGE: Use theme surface color
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            // CHANGE: Use onSurface color
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Image(
                    painter = painterResource(id = restaurant.imageRes),
                    contentDescription = "${restaurant.name} Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "How was your last order from ${restaurant.name}?",
                fontSize = 26.sp,
                // CHANGE: Use onBackground color
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = ratingText,
                fontSize = 22.sp,
                // CHANGE: Use primary color
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    // CHANGE: Use a theme color for the unselected star
                    val starColor by animateColorAsState(
                        targetValue = if (i <= rating) Color(0xFFFFC529) else MaterialTheme.colorScheme.surfaceVariant,
                        label = "starColorAnimation"
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = "Star $i",
                        tint = starColor,
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp)
                            .clickable { rating = i }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextField(
                value = review,
                onValueChange = { review = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Write your review...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                // CHANGE: Use theme colors for TextField
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.outline,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.weight(1f, fill = true)) // Pushes the button to the bottom

            Button(
                onClick = { /* TODO: Submit review logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(50),
                // CHANGE: Use theme colors for button
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "SUBMIT",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}


// --- Previews ---

@Preview(showBackground = true, name = "Review Restaurant - Light")
@Composable
fun ReviewRestaurantScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        ReviewRestaurantScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Review Restaurant - Dark")
@Composable
fun ReviewRestaurantScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        ReviewRestaurantScreen(navController = rememberNavController())
    }
}