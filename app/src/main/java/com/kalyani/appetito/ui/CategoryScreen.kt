package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R

@Preview(showBackground = true)
@Composable
fun CategoryScreen() {
    val categories = listOf(
        CategoryItemData(
            name = "Chicken Hawaiian",
            description = "Chicken, Cheese and pineapple",
            price = 10.35f,
            rating = 4.5f,
            reviews = 25,
            imageRes = R.drawable.chicken_hawaiian
        ),
        CategoryItemData(
            name = "Margherita Pizza",
            description = "Fresh tomatoes, mozzarella, basil",
            price = 8.99f,
            rating = 4.7f,
            reviews = 42,
            imageRes = R.drawable.pizza_2
        ),
        CategoryItemData(
            name = "Pepperoni Special",
            description = "Pepperoni, cheese, Italian herbs",
            price = 12.50f,
            rating = 4.6f,
            reviews = 38,
            imageRes = R.drawable.pizza_3
        ),
        CategoryItemData(
            name = "Veggie Supreme",
            description = "Bell peppers, olives, mushrooms",
            price = 11.25f,
            rating = 4.4f,
            reviews = 29,
            imageRes = R.drawable.pizza_4
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        // Background pizza image covering top portion
        Image(
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .align(Alignment.TopCenter)
        )

        // Gradient overlay on background image
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .align(Alignment.TopCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Black.copy(alpha = 0.3f),
                            Color.Transparent,
                            Color.White
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Back button
            IconButton(
                onClick = { /* Handle back navigation */ },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Title section
            Text(
                text = "Fast",
                fontSize = 45.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF272D2F),
                modifier = Modifier.padding(start = 26.dp)
            )
            Text(
                text = "Food",
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFE724C),
                modifier = Modifier.padding(start = 26.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "80 type of pizza",
                fontSize = 19.sp,
                color = Color(0xFF9796A1),
                modifier = Modifier.padding(start = 26.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sort by section with slider dots
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Short by:",
                        fontSize = 14.sp,
                        color = Color(0xFF111719)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Popular",
                        fontSize = 14.sp,
                        color = Color(0xFFFE724C),
                        fontWeight = FontWeight.Medium
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dropdown),
                        contentDescription = null,
                        tint = Color(0xFFFE724C),
                        modifier = Modifier
                            .size(16.dp)
                            .padding(start = 4.dp)
                    )
                }

                // Slider dots
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFFE724C), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFE0E0E0), CircleShape)
                    )
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .background(Color(0xFFE0E0E0), CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Category cards
            categories.forEach { item ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(animationSpec = tween(400)),
                    exit = fadeOut(animationSpec = tween(200))
                ) {
                    CategoryCard(item)
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

data class CategoryItemData(
    val name: String,
    val description: String,
    val price: Float,
    val rating: Float,
    val reviews: Int,
    val imageRes: Int
)

@Composable
fun CategoryCard(item: CategoryItemData) {
    var isFavorite by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .fillMaxWidth()
            .clickable { /* Handle item click */ },
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box {
            Column {
                // Food image
                Box {
                    Image(
                        painter = painterResource(id = item.imageRes),
                        contentDescription = item.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                    )

                    // Price badge - top left
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .padding(16.dp)
                            .background(Color.White, RoundedCornerShape(20.dp))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "$${String.format("%.2f", item.price)}",
                            fontSize = 16.sp,
                            color = Color(0xFFFE724C),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Heart icon - top right
                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(12.dp)
                            .background(
                                Color(0xFFFE724C),
                                CircleShape
                            )
                            .size(40.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id = if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_favorite
                            ),
                            contentDescription = "Favorite",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Rating badge - bottom left
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                            .background(Color.White, RoundedCornerShape(16.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = item.rating.toString(),
                            fontSize = 14.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star),
                            contentDescription = "Rating",
                            tint = Color(0xFFFFC529),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "(${item.reviews}+)",
                            fontSize = 12.sp,
                            color = Color(0xFF9796A1)
                        )
                    }
                }

                // Content section
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = item.name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF323643)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = item.description,
                        fontSize = 14.sp,
                        color = Color(0xFF9796A1),
                        lineHeight = 20.sp
                    )
                }
            }
        }
    }
}