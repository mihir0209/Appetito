package ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R // Make sure this is your correct R file import
import kotlin.math.roundToInt


@Composable
fun HomeScreen(
    // The main NavController is added here to handle navigation to other screens.
    mainNavController: NavHostController
) {
    var menuOpen by remember { mutableStateOf(false) }

    val animationProgress by animateFloatAsState(
        targetValue = if (menuOpen) 1f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "menuAnimation"
    )

    val scale = lerp(1f, 0.7f, animationProgress)
    val translationXInDp = 280.dp
    val translationXInPx = with(LocalDensity.current) { translationXInDp.toPx() }
    val translationX = lerp(0f, translationXInPx, animationProgress)
    val cornerRadius = lerp(0f, 32f, animationProgress)
    val shadowElevation = lerp(0f, 24f, animationProgress)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFE724C).copy(alpha = 0.9f))
    ) {
        SideMenuFigma(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp),
            // The NavController is passed down to the side menu.
            navController = mainNavController
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    this.scaleX = scale
                    this.scaleY = scale
                    this.translationX = translationX
                    this.shadowElevation = shadowElevation
                    this.transformOrigin = TransformOrigin(0f, 0.5f)
                }
                .clip(RoundedCornerShape(cornerRadius.roundToInt().dp))
                .clickable(
                    enabled = menuOpen,
                    onClick = { menuOpen = false },
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        ) {
            HomeScreenContent(
                onMenuClick = { menuOpen = !menuOpen },
                isMenuOpen = menuOpen,
                animationProgress = animationProgress,
                // The NavController is also passed to the main content area.
                navController = mainNavController
            )
        }
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + fraction * (stop - start)
}

@Composable
fun HomeScreenContent(
    onMenuClick: () -> Unit,
    isMenuOpen: Boolean,
    animationProgress: Float,
    navController: NavHostController
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 24.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onMenuClick) {
                    if (isMenuOpen) {
                        Icon(painterResource(id = R.drawable.ic_close), contentDescription = "Close Menu", tint = Color.Black)
                    } else {
                        Icon(painterResource(id = R.drawable.ic_menu), contentDescription = "Menu", tint = Color.Black)
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Deliver to", color = Color.Gray, fontSize = 14.sp)
                    Text("4102 Pretty View Lane", color = Color(0xFFFE724C), fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.image_13),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        // Example: Clicking the profile image in the top bar navigates to the profile screen.
                        .clickable { navController.navigate(BottomNavTab.Profile.route) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Text("What would you like\nto order", fontSize = 32.sp, fontWeight = FontWeight.Bold, lineHeight = 40.sp, color = Color.Black, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Find for food or restaurant...", color = Color.Gray) },
                leadingIcon = { Icon(painterResource(id = R.drawable.ic_search), "Search", tint = Color(0xFFFE724C)) },
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFFFE724C).copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                            .padding(10.dp)
                    ) {
                        Icon(painterResource(id = R.drawable.ic_filter), "Filter", tint = Color(0xFFFE724C))
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedBorderColor = Color.Transparent, focusedBorderColor = Color(0xFFFE724C),
                    unfocusedContainerColor = Color(0xFFF0F0F0), focusedContainerColor = Color(0xFFF0F0F0),
                    unfocusedLabelColor = Color.Gray,
                    focusedLabelColor = Color(0xFFFE724C),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                CategoryChip("Burger", R.drawable.img_burger, selected = true)
                CategoryChip("Donat", R.drawable.img_burger)
                CategoryChip("Pizza", R.drawable.img_burger)
                CategoryChip("Mexican", R.drawable.img_burger)
                CategoryChip("Asian", R.drawable.img_burger)
            }
            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Featured Restaurants", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text("View All >", fontSize = 14.sp, color = Color(0xFFFE724C), modifier = Modifier.clickable {})
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 16.dp, end = 16.dp), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                RestaurantCard("McDonald's", R.drawable.mcdonalds_img, 4.9f, onClick = { navController.navigate("food_details/mcdonalds") })
                RestaurantCard("Starbucks", R.drawable.starbucks_img, 4.7f, onClick = { navController.navigate("food_details/starbucks") })
            }
            Spacer(modifier = Modifier.height(24.dp))

            Text("Popular Items", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                PopularItemCard("Cheese Burger", "Burger King", "$5.50", R.drawable.img_burger, onClick = { navController.navigate("food_details/cheese_burger") })
                PopularItemCard("Toffee's Cake", "Top Cake", "$8.25", R.drawable.cake_img, onClick = { navController.navigate("food_details/toffee_cake") })
            }
        }
        if (animationProgress > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFE724C).copy(alpha = 0.4f * animationProgress),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
    }
}


@Composable
fun SideMenuFigma(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(id = R.drawable.image_13), "Profile", modifier = Modifier
                .size(64.dp)
                .clip(CircleShape))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Farian Wick", fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = Color.Black)
                Text("farianwick@gmail.com", fontSize = 14.sp, color = Color.Gray)
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        // Each menu item now uses the NavController to navigate.
        SideMenuItemFigma("My Orders", R.drawable.ic_order, onClick = { navController.navigate("orders") })
        SideMenuItemFigma("My Profile", R.drawable.ic_profile, onClick = { navController.navigate(BottomNavTab.Profile.route) })
        SideMenuItemFigma("Delivery Address", R.drawable.ic_location, onClick = { navController.navigate("add_address") })
        SideMenuItemFigma("Payment Methods", R.drawable.ic_wallet, onClick = { /* navController.navigate("payment_methods") */ })
        SideMenuItemFigma("Contact Us", R.drawable.ic_message, onClick = { /* navController.navigate("contact_us") */ })
        SideMenuItemFigma("Settings", R.drawable.ic_settings, onClick = { /* navController.navigate("settings") */ })
        SideMenuItemFigma("Helps & FAQs", R.drawable.ic_help, onClick = { /* navController.navigate("help") */ })
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /* Handle logout */ },
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painterResource(id = R.drawable.ic_logout), "Logout", tint = Color.White)
            Spacer(modifier = Modifier.width(12.dp))
            Text("Log Out", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SideMenuItemFigma(
    title: String,
    iconRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick) // The entire row is now clickable.
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(id = iconRes), contentDescription = title, tint = Color.Gray, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(18.dp))
        Text(title, fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun CategoryChip(name: String, iconRes: Int, selected: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(70.dp)) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .shadow(if (selected) 8.dp else 2.dp, CircleShape)
                .clip(CircleShape)
                .background(if (selected) Color(0xFFFE724C) else Color(0xFFF0F0F0))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(id = iconRes), contentDescription = name, tint = if (selected) Color.White else Color(0xFFFE724C), modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(name, color = if (selected) Color(0xFFFE724C) else Color.Gray, fontSize = 14.sp, textAlign = TextAlign.Center, fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal)
    }
}

@Composable
fun RestaurantCard(
    name: String,
    imageRes: Int,
    rating: Float,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick, // The Card itself is now clickable.
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier.width(280.dp).height(200.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box {
            Image(painter = painterResource(id = imageRes), contentDescription = name, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)))))
            Column(modifier = Modifier.align(Alignment.BottomStart).padding(16.dp)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                Text("Burger • Chicken • Fast Food", color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text("Free delivery", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("10-15 mins", color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align(Alignment.TopEnd).padding(12.dp).background(Color.White, RoundedCornerShape(12.dp)).padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(Icons.Default.Star, "Star", tint = Color(0xFFFFC529), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("$rating", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun PopularItemCard(
    name: String,
    restaurant: String,
    price: String,
    imageRes: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF0F0F0), RoundedCornerShape(16.dp))
            .clickable(onClick = onClick) // The entire Row is now clickable.
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = imageRes), contentDescription = name, modifier = Modifier.size(60.dp).clip(RoundedCornerShape(12.dp)))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = Color.Black)
            Text(restaurant, fontSize = 14.sp, color = Color.Gray)
        }
        Text(price, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFE724C))
    }
}

@Preview(showBackground = true, device = "id:pixel_4")
@Composable
fun HomeScreenPreview() {
    // We pass a dummy NavController for the preview to work.
    val dummyNavController = rememberNavController()
    HomeScreen(mainNavController = dummyNavController)
}