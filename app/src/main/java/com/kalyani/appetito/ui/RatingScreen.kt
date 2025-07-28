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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R // Make sure to use your actual R file import

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun RatingScreen() {
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

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // This Box contains the top image and the overlapping logo
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    // The height of this box determines where the logo will sit
                    .height(260.dp)
            ) {
                // Background Image
                Image(
                    painter = painterResource(id = R.drawable.pizzahut_background), // Placeholder image
                    contentDescription = "Pizza Background",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    contentScale = ContentScale.Crop
                )

                // Back Button
                IconButton(
                    onClick = { /* Handle back press */ },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(start = 16.dp, top = 16.dp)
                        .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                // Logo Box aligned to the bottom of the container
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .size(140.dp)
                        .background(Color.White, CircleShape)
                        .padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pizza_hut_logo), // Placeholder logo
                        contentDescription = "Pizza Hut Logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                    )
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = "Verified",
                        tint = Color(0xFF00C444),
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(36.dp)
                            .background(Color.White, CircleShape)
                            .padding(2.dp)
                    )
                }
            }

            // This spacer provides the necessary gap between the logo and the text below it
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Pizza Hut",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "4102 Pretty View Lanenda",
                fontSize = 15.sp,
                color = Color(0xFF9796A1)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "● Order Delivered",
                fontSize = 15.sp,
                color = Color(0xFF53D776),
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Please Rate Delivery Service",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF111719)
            )
            Spacer(modifier = Modifier.height(24.dp))
            // Stars
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (i in 1..5) {
                    val starColor by animateColorAsState(
                        targetValue = if (i <= rating) Color(0xFFFFC529) else Color(0xFFF0F0F0),
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = ratingText,
                fontSize = 18.sp,
                color = Color.Black.copy(alpha = 0.7f),
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Review text field
            OutlinedTextField(
                value = review,
                onValueChange = { review = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .height(120.dp),
                placeholder = { Text("Write review", color = Color.Gray.copy(alpha = 0.8f)) },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFE724C),
                    unfocusedBorderColor = Color(0xFFE8E8E8)
                )
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Submit button
            Button(
                onClick = { /* TODO: Submit rating logic */ },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
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