@file:OptIn(ExperimentalAnimationApi::class) // Opt-in at file level

package ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi // Import for OptIn
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with // For the 'with' infix function in transitionSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R

@Preview(showBackground = true)
@Composable
fun FavoritesFoodItemsScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    val favoriteFoods = remember { sampleFavoriteFoods() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // ... (Top bar code) ...
        IconButton(onClick = { /* TODO: Back navigation */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color(0xFF111719)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Favorites",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF111719),
            modifier = Modifier.align(Alignment.CenterHorizontally)

        )
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.height(22.dp))
        // Tabs
        Row(
            modifier = Modifier
                .padding(horizontal = 22.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(27.5.dp))
                .background(Color(0xFFF2EAEA)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // THESE CALLS (lines 72 and 78 in your error list) WILL BE FIXED
            // ONCE THE TabButton DEFINITION CONFLICT IS RESOLVED
            TabButton(
                text = "Food Items",
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.weight(1f)
            )
            TabButton(
                text = "Restaurants", // Corrected typo
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(
            targetState = selectedTab,
            transitionSpec = {
                fadeIn(animationSpec = tween<Float>(durationMillis = 300)) with
                        fadeOut(animationSpec = tween<Float>(durationMillis = 300))
            },
            label = "TabContentAnimation"
        ) { tab ->
            if (tab == 0) {
                Column {
                    favoriteFoods.forEach { food ->
                        AnimatedVisibility( // This is line 92 in your error list
                            visible = true,
                            enter = fadeIn(animationSpec = tween<Float>(durationMillis = 400)),
                            exit = fadeOut(animationSpec = tween<Float>(durationMillis = 200)),
                        ) {
                            FavoriteFoodCard(food)
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                    Text("No favorite restaurants yet.", color = Color(0xFF9796A1))
                }
            }
        }
    }
}

// MAKE SURE THIS TabButton DEFINITION (around line 123 in your error list)
// IS THE *ONLY* ONE IN THIS FILE AND NOT CONFLICTING WITH AN IMPORTED ONE.
@Composable
fun TabButton(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(23.5.dp),
        colors = if (selected) ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C))
        else ButtonDefaults.buttonColors(containerColor = Color.Transparent, contentColor = Color(0xFFFE724C)),
        modifier = modifier
            .height(47.dp)
            .padding(horizontal = 4.dp)
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color(0xFFFE724C),
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}


data class FavoriteFood(
    val name: String,
    val description: String,
    val price: Float,
    val rating: Float,
    val reviews: Int,
    val imageRes: Int
)

@Composable
fun FavoriteFoodCard(food: FavoriteFood) {
    Box(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
            // .background(Color.White) // Shadow works best if the surface has a color
            .shadow( // shadow modifier should now resolve
                elevation = 4.dp, // Reduced elevation for a softer look
                shape = RoundedCornerShape(18.dp),
                clip = false // Allow shadow to extend beyond bounds before clipping by parent
            )
            .clip(RoundedCornerShape(18.dp)) // Clip content after shadow
            .background(Color.White) // Apply background after shadow and clipping of content
    ) {
        Column {
            Image(
                painter = painterResource(id = food.imageRes), // R.drawable should resolve
                contentDescription = food.name, // Better content description
                modifier = Modifier
                    .fillMaxWidth()
                    .height(165.dp)
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)) // Clip only top if content flows out
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = food.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 14.dp, end = 14.dp)
            )
            Text(
                text = food.description,
                fontSize = 14.sp,
                color = Color(0xFF5B5B5E),
                modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 8.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 14.dp, end = 14.dp, bottom = 12.dp) // Added end padding
            ) {
                Text(
                    text = "$${String.format("%.2f", food.price)}",
                    fontSize = 18.sp,
                    color = Color(0xFFFE724C),
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.weight(1f)) // Push ratings to the end
                Icon(
                    painter = painterResource(id = R.drawable.ic_star), // R.drawable should resolve
                    contentDescription = "Star rating",
                    tint = Color(0xFFFFC529),
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = food.rating.toString(),
                    fontSize = 12.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 4.dp)
                )
                Text(
                    text = "(${food.reviews}+)",
                    fontSize = 9.sp,
                    color = Color(0xFF9796A1),
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

fun sampleFavoriteFoods() = listOf(
    FavoriteFood(
        name = "Chicken Hawaiian",
        description = "Chicken, Cheese and pineapple",
        price = 9.20f,
        rating = 4.5f,
        reviews = 25,
        imageRes = R.drawable.chicken_hawaiian // R.drawable should resolve
    ),
    FavoriteFood(
        name = "Red N Hot Pizza",
        description = "Chicken, Chili",
        price = 10.35f,
        rating = 4.5f,
        reviews = 25,
        imageRes = R.drawable.chicken_hawaiian // R.drawable should resolve
    ),
    FavoriteFood(
        name = "Chicken Hawaiian", // Note: Duplicate name, consider unique keys if used in Lazy lists
        description = "Chicken, Cheese and pineapple",
        price = 8.28f,
        rating = 4.5f,
        reviews = 25,
        imageRes = R.drawable.chicken_hawaiian // R.drawable should resolve
    )
)

