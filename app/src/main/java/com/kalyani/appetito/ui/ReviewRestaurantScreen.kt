package ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R // Make sure to use your actual R file import

@Preview(showBackground = true)
@Composable
fun ReviewRestaurantScreen() {
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

    Scaffold(
        containerColor = Color.White
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top section with back button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            ) {
                // Back Button with shadow
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(44.dp)
                        .align(Alignment.CenterStart)
                        .shadow(elevation = 8.dp, shape = CircleShape),
                    onClick = { /* Handle back press */ },
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Restaurant Logo
            Card(
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pizza_hut_logo), // Placeholder logo
                    contentDescription = "Pizza Hut Logo",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(16.dp)
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Main question text
            Text(
                text = "How was your last order from Pizza Hut ?",
                fontSize = 26.sp,
                color = Color.Black,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center,
                lineHeight = 38.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Rating text (e.g., "Good")
            Text(
                text = ratingText,
                fontSize = 22.sp,
                color = Color(0xFFFE724C),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Stars for rating
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    val starColor by animateColorAsState(
                        targetValue = if (i <= rating) Color(0xFFFFC529) else Color(0xFFE8E8E8),
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

            // Review input field with only a bottom line
            TextField(
                value = review,
                onValueChange = { review = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Write", color = Color.Gray.copy(alpha = 0.5f)) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color(0xFFFE724C),
                    unfocusedIndicatorColor = Color.LightGray,
                    cursorColor = Color(0xFFFE724C)
                )
            )

            // This spacer pushes the submit button to the bottom
            Spacer(modifier = Modifier.weight(1f))

            // Submit button
            Button(
                onClick = { /* TODO: Submit review logic */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(60.dp),
                shape = RoundedCornerShape(50), // Pill shape
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C))
            ) {
                Text(
                    text = "SUBMIT",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White
                )
            }
        }
    }
}