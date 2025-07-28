package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
// THE FIX: The function now correctly accepts a NavHostController.
fun CategoryScreen(navController: NavHostController) {
    val categories = listOf(
        CategoryItemData("Chicken Hawaiian", "Chicken, Cheese and pineapple", 10.35f, 4.5f, 25, R.drawable.chicken_hawaiian),
        CategoryItemData("Margherita Pizza", "Fresh tomatoes, mozzarella, basil", 8.99f, 4.7f, 42, R.drawable.pizza_2),
        CategoryItemData("Pepperoni Special", "Pepperoni, cheese, Italian herbs", 12.50f, 4.6f, 38, R.drawable.pizza_3),
        CategoryItemData("Veggie Supreme", "Bell peppers, olives, mushrooms", 11.25f, 4.4f, 29, R.drawable.pizza_4)
    )

    var isContentVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isContentVisible = true }

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Image(
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(500.dp)
                .offset(x = 100.dp, y = (-150).dp)
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        // THE FIX: The back button is now functional.
                        IconButton(onClick = {
                            // This navigates back to the Home screen, the start of the tabs.
                            navController.navigate(BottomNavTab.Home.route) {
                                popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                launchSingleTop = true
                            }
                        }) {
                            Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back", tint = Color.Black)
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {
                    Column(modifier = Modifier.padding(start = 26.dp, end = 26.dp, top = 20.dp)) {
                        Text("Fast", fontSize = 45.sp, fontWeight = FontWeight.Bold, color = Color(0xFF272D2F))
                        Text("Food", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFE724C))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("80 types of pizza", fontSize = 19.sp, color = Color(0xFF9796A1))
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Sort by:", fontSize = 14.sp, color = Color(0xFF111719))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Popular", fontSize = 14.sp, color = Color(0xFFFE724C), fontWeight = FontWeight.Medium)
                                Icon(painter = painterResource(id = R.drawable.ic_dropdown), contentDescription = null, tint = Color(0xFFFE724C), modifier = Modifier.size(16.dp).padding(start = 4.dp))
                            }
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(modifier = Modifier.size(8.dp).background(Color(0xFFFE724C), CircleShape))
                                Box(modifier = Modifier.size(8.dp).background(Color(0xFFE0E0E0), CircleShape))
                                Box(modifier = Modifier.size(8.dp).background(Color(0xFFE0E0E0), CircleShape))
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                itemsIndexed(categories) { index, item ->
                    AnimatedVisibility(
                        visible = isContentVisible,
                        enter = fadeIn(animationSpec = tween(500, delayMillis = index * 100)) +
                                slideInVertically(
                                    initialOffsetY = { 60 },
                                    animationSpec = tween(500, delayMillis = index * 100)
                                )
                    ) {
                        CategoryCard(item, onClick = {
                            // Example: Navigate to food details when a card is clicked
                            // Note: We would need the *main* NavController for this to work.
                            // For now, the action is commented out.
                            // mainNavController.navigate("food_details/${item.name}")
                        })
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
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
fun CategoryCard(item: CategoryItemData, onClick: () -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier.padding(horizontal = 26.dp).fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                )
                Box(modifier = Modifier.align(Alignment.TopStart).padding(16.dp).background(Color.White, RoundedCornerShape(20.dp)).padding(horizontal = 12.dp, vertical = 6.dp)) {
                    Text("$${String.format("%.2f", item.price)}", fontSize = 16.sp, color = Color(0xFFFE724C), fontWeight = FontWeight.Bold)
                }
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    modifier = Modifier.align(Alignment.TopEnd).padding(12.dp).background(Color(0xFFFE724C), CircleShape).size(40.dp)
                ) {
                    Icon(painter = painterResource(id = if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_favorite), contentDescription = "Favorite", tint = Color.White, modifier = Modifier.size(20.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.BottomStart).padding(16.dp).background(Color.White, RoundedCornerShape(16.dp)).padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(item.rating.toString(), fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = "Rating", tint = Color(0xFFFFC529), modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("(${item.reviews}+)", fontSize = 12.sp, color = Color(0xFF9796A1))
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = item.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF323643))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.description, fontSize = 14.sp, color = Color(0xFF9796A1), lineHeight = 20.sp)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    // THE FIX: The preview now passes a dummy NavController.
    CategoryScreen(navController = rememberNavController())
}