package ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import com.kalyani.appetito.R

// Data classes for our items
data class FavoriteFood(val name: String, val description: String, val price: Float, val rating: Float, val reviews: Int, val imageRes: Int)
data class FavoriteRestaurant(val name: String, val categories: String, val deliveryTime: String, val imageRes: Int)

// Sample data providers
fun sampleFavoriteFoods() = listOf(
    FavoriteFood("Chicken Hawaiian", "Chicken, Cheese and pineapple", 9.20f, 4.5f, 25, R.drawable.chicken_hawaiian),
    FavoriteFood("Red N Hot Pizza", "Chicken, Chili", 10.35f, 4.5f, 75, R.drawable.pizza_3),
    FavoriteFood("Veggie Supreme", "Bell peppers, olives, mushrooms", 11.25f, 4.4f, 29, R.drawable.pizza_4)
)
fun sampleFavoriteRestaurants() = listOf(
    FavoriteRestaurant("McDonald's", "Burger • Chicken • Fast Food", "10-15 mins", R.drawable.mcdonalds_img),
    FavoriteRestaurant("Starbucks", "Coffee • Bakery • Drinks", "5-10 mins", R.drawable.starbucks_img)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesFoodItemsScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val favoriteFoods = remember { sampleFavoriteFoods() }
    val favoriteRestaurants = remember { sampleFavoriteRestaurants() }

    var isContentVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isContentVisible = true }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Favorites", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Back navigation */ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF5F5F5) // Light gray background
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            // Custom TabRow for switching between Food Items and Restaurants
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color(0xFFFE724C)
                    )
                }
            ) {
                Tab(
                    selected = selectedTabIndex == 0,
                    onClick = { selectedTabIndex = 0 },
                    text = { Text("Food Items", color = if (selectedTabIndex == 0) Color(0xFFFE724C) else Color.Gray) }
                )
                Tab(
                    selected = selectedTabIndex == 1,
                    onClick = { selectedTabIndex = 1 },
                    text = { Text("Restaurants", color = if (selectedTabIndex == 1) Color(0xFFFE724C) else Color.Gray) }
                )
            }

            // AnimatedContent smoothly switches between the two lists
            AnimatedContent(
                targetState = selectedTabIndex,
                transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) },
                label = "FavoritesTabAnimation"
            ) { tabIndex ->
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (tabIndex == 0) {
                        itemsIndexed(favoriteFoods) { index, food ->
                            AnimatedVisibility(
                                visible = isContentVisible,
                                enter = fadeIn(tween(500, index * 100)) + slideInVertically(initialOffsetY = { 40 }, animationSpec = tween(500, index * 100))
                            ) {
                                FavoriteFoodCard(food)
                            }
                        }
                    } else {
                        itemsIndexed(favoriteRestaurants) { index, restaurant ->
                            AnimatedVisibility(
                                visible = isContentVisible,
                                enter = fadeIn(tween(500, index * 100)) + slideInVertically(initialOffsetY = { 40 }, animationSpec = tween(500, index * 100))
                            ) {
                                FavoriteRestaurantCard(restaurant)
                            }
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun FavoriteFoodCard(food: FavoriteFood) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().clickable { /* TODO: Navigate to food details */ }
    ) {
        Column {
            Image(
                painter = painterResource(id = food.imageRes),
                contentDescription = food.name,
                // FIX: ContentScale.Crop fills the space without distorting the image
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth().height(160.dp)
            )
            Column(modifier = Modifier.padding(12.dp)) {
                Text(food.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(food.description, fontSize = 14.sp, color = Color.Gray, maxLines = 1)
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("$${String.format("%.2f", food.price)}", fontSize = 20.sp, color = Color(0xFFFE724C), fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = "Rating", tint = Color(0xFFFFC529), modifier = Modifier.size(16.dp))
                    Text(food.rating.toString(), fontSize = 14.sp, color = Color.Black, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(start = 4.dp))
                    Text(" (${food.reviews}+)", fontSize = 12.sp, color = Color.Gray, modifier = Modifier.padding(start = 4.dp))
                }
            }
        }
    }
}

@Composable
fun FavoriteRestaurantCard(restaurant: FavoriteRestaurant) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth().clickable { /* TODO: Navigate to restaurant details */ }
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = restaurant.imageRes),
                contentDescription = restaurant.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(restaurant.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(restaurant.categories, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Text(restaurant.deliveryTime, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun FavoritesFoodItemsScreenPreview() {
    FavoritesFoodItemsScreen()
}