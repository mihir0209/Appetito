package ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// THE FIX 1: Add the NavController parameter. This is our standard pattern.
fun RatingScreen(navController: NavHostController) {
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

    // THE FIX 2: Use Scaffold for a consistent layout structure.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { /* Title is empty for this design */ },
                // THE FIX 3: Use the standard IconButton. It's clean and consistent.
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White // White icon for the image background
                        )
                    }
                },
                // Make the TopAppBar transparent to see the image behind it.
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        // Use LazyColumn for performance and to prevent content from drawing under the system bars.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 0.dp) // We want the image to go to the very top
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- Header Section with overlapping logo ---
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pizzahut_background),
                        contentDescription = "Pizza Background",
                        modifier = Modifier.fillMaxWidth().height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    // The TopAppBar from the Scaffold will draw over this image.

                    Box(
                        modifier = Modifier
                            .offset(y = 70.dp) // This pulls the logo down to overlap the content below
                            .size(140.dp)
                            .background(Color.White, CircleShape)
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pizza_hut_logo),
                            contentDescription = "Pizza Hut Logo",
                            modifier = Modifier.fillMaxSize().clip(CircleShape)
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
            }

            // --- Content Section ---
            item {
                // Add a spacer to account for the overlapping logo
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(80.dp))
                    Text(text = "Pizza Hut", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "4102 Pretty View Lanenda", fontSize = 15.sp, color = Color(0xFF9796A1))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "● Order Delivered", fontSize = 15.sp, color = Color(0xFF53D776), fontWeight = FontWeight.Medium)
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Please Rate Delivery Service", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = Color(0xFF111719), textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        for (i in 1..5) {
                            val starColor by animateColorAsState(targetValue = if (i <= rating) Color(0xFFFFC529) else Color(0xFFF0F0F0), label = "starColorAnimation")
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "Star $i", tint = starColor, modifier = Modifier.size(48.dp).padding(4.dp).clickable { rating = i })
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = ratingText, fontSize = 18.sp, color = Color.Black.copy(alpha = 0.7f), fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = review,
                        onValueChange = { review = it },
                        modifier = Modifier.fillMaxWidth().height(120.dp),
                        placeholder = { Text("Write review", color = Color.Gray.copy(alpha = 0.8f)) },
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = Color(0xFFFE724C), unfocusedBorderColor = Color(0xFFE8E8E8))
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { /* TODO: Submit rating logic */ },
                        modifier = Modifier.fillMaxWidth().height(60.dp),
                        shape = RoundedCornerShape(30.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C))
                    ) {
                        Text(text = "SUBMIT", fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RatingScreenPreview() {
    // THE FIX 4: Update the preview to pass the dummy NavController.
    RatingScreen(navController = rememberNavController())
}