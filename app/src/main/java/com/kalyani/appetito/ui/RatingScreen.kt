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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            // Keep white icon as it's on a dark image background
                            tint = Color.White
                        )
                    }
                },
                // Transparent TopAppBar is a key part of the design
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                // The LazyColumn now correctly inherits the Scaffold's themed background color
                .padding(top = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pizzahut_background),
                        contentDescription = "Pizza Background",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Box(
                        modifier = Modifier
                            .offset(y = 70.dp)
                            // CHANGE: Use theme surface color for the logo background
                            .background(MaterialTheme.colorScheme.surface, CircleShape)
                            .padding(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.pizza_hut_logo),
                            contentDescription = "Pizza Hut Logo",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = "Verified",
                            // Status color remains green
                            tint = Color(0xFF00C444),
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(36.dp)
                                // CHANGE: Use theme surface color for the checkmark background
                                .background(MaterialTheme.colorScheme.surface, CircleShape)
                                .padding(2.dp)
                        )
                    }
                }
            }

            item {
                Column(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(80.dp)) // Spacer for overlapping logo
                    Text(text = "Pizza Hut", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "4102 Pretty View Lanenda", fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "● Order Delivered", fontSize = 15.sp, color = Color(0xFF53D776), fontWeight = FontWeight.Medium) // Status color remains green
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(text = "Please Rate Delivery Service", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                        for (i in 1..5) {
                            // CHANGE: Use theme surfaceVariant for unselected stars
                            val starColor by animateColorAsState(targetValue = if (i <= rating) Color(0xFFFFC529) else MaterialTheme.colorScheme.surfaceVariant, label = "starColorAnimation")
                            Icon(imageVector = Icons.Filled.Star, contentDescription = "Star $i", tint = starColor, modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                                .clickable { rating = i })
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = ratingText, fontSize = 18.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(24.dp))
                    OutlinedTextField(
                        value = review,
                        onValueChange = { review = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        placeholder = { Text("Write review", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                        shape = RoundedCornerShape(16.dp),
                        // CHANGE: Use theme colors for text field
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline
                        )
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(
                        onClick = { /* TODO: Submit rating logic */ },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp),
                        shape = RoundedCornerShape(30.dp),
                        // CHANGE: Use theme colors for button
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(text = "SUBMIT", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Rating Screen - Light")
@Composable
fun RatingScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        RatingScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Rating Screen - Dark")
@Composable
fun RatingScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        RatingScreen(navController = rememberNavController())
    }
}