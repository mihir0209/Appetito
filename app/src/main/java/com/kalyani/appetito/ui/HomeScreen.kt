package ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
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
import com.kalyani.appetito.R
import kotlin.math.roundToInt

@Composable
fun HomeScreen(
    nestedNavController: NavHostController,
    mainNavController: NavHostController
) {
    var menuOpen by remember { mutableStateOf(false) }
    val animationProgress by animateFloatAsState(targetValue = if (menuOpen) 1f else 0f, animationSpec = tween(durationMillis = 400), label = "menuAnimation")
    val scale = lerp(1f, 0.7f, animationProgress)
    val translationXInDp = 280.dp
    val translationXInPx = with(LocalDensity.current) { translationXInDp.toPx() }
    val translationX = lerp(0f, translationXInPx, animationProgress)
    val cornerRadius = lerp(0f, 32f, animationProgress)
    val shadowElevation = lerp(0f, 24f, animationProgress)

    // CHANGE: Use theme primary color for the background behind the animated screen.
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary.copy(alpha = 0.9f))) {
        SideMenuFigma(
            modifier = Modifier
                .fillMaxHeight()
                .width(280.dp),
            mainNavController = mainNavController,
            nestedNavController = nestedNavController
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    this.scaleX = scale; this.scaleY = scale; this.translationX = translationX
                    this.shadowElevation = shadowElevation; this.transformOrigin = TransformOrigin(0f, 0.5f)
                }
                .clip(RoundedCornerShape(cornerRadius.roundToInt().dp))
                // GESTURE IMPLEMENTATION: Combines swipe-to-open and swipe-to-close gestures.
                .pointerInput(Unit) {
                    detectHorizontalDragGestures(
                        onDragStart = { },
                        onHorizontalDrag = { change, dragAmount ->
                            if (dragAmount > 5) { // Swipe left-to-right to open
                                menuOpen = true
                                change.consume()
                            } else if (dragAmount < -5) { // Swipe right-to-left to close
                                menuOpen = false
                                change.consume()
                            }
                        }
                    )
                }
                .clickable(enabled = menuOpen, onClick = { menuOpen = false }, indication = null, interactionSource = remember { MutableInteractionSource() })
        ) {
            HomeScreenContent(onMenuClick = { menuOpen = !menuOpen }, nestedNavController = nestedNavController, mainNavController = mainNavController)
        }
    }
}

private fun lerp(start: Float, stop: Float, fraction: Float): Float = start + fraction * (stop - start)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    onMenuClick: () -> Unit,
    nestedNavController: NavHostController,
    mainNavController: NavHostController
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showAddressSheet by remember { mutableStateOf(false) }
    val addresses = DemoDataProvider.savedAddresses
    val selectedAddressState = DemoDataProvider.selectedAddress
    val selectedAddress = selectedAddressState.value
    val featuredRestaurants = DemoDataProvider.featuredRestaurants
    val popularItems = DemoDataProvider.popularItems

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background).verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onMenuClick) { Icon(painterResource(id = R.drawable.ic_menu), contentDescription = "Menu", tint = MaterialTheme.colorScheme.onBackground) }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier.clickable { showAddressSheet = true },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Deliver to", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 12.sp)
                        Text(selectedAddress.fullAddress, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                    Icon(painter = painterResource(id = R.drawable.ic_expand), contentDescription = "Change Address", tint = MaterialTheme.colorScheme.primary, modifier = Modifier
                        .size(20.dp)
                        .padding(start = 4.dp))
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(painter = painterResource(id = R.drawable.image_13), contentDescription = "Profile", modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable { nestedNavController.navigate(BottomNavTab.Profile.route) })
            }

            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = "What would you like to order?", fontSize = 28.sp, fontWeight = FontWeight.Bold, lineHeight = 36.sp, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    value = "", onValueChange = {},
                    placeholder = { Text("Find for food or restaurant...", color = MaterialTheme.colorScheme.onSurfaceVariant) },
                    leadingIcon = { Icon(painterResource(id = R.drawable.ic_search), "Search", tint = MaterialTheme.colorScheme.primary) },
                    trailingIcon = { Box(modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                        .padding(10.dp)) { Icon(painterResource(id = R.drawable.ic_filter), "Filter", tint = MaterialTheme.colorScheme.primary) } },
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.Transparent,
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp), horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                CategoryChip("Burger", R.drawable.img_burger, selected = true); CategoryChip("Donat", R.drawable.img_donat); CategoryChip("Pizza", R.drawable.img_pizza); CategoryChip("Mexican", R.drawable.img_mexican); CategoryChip("Asian", R.drawable.img_asian)
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text("Featured Restaurants", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.weight(1f))
                    Text("View All >", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.clickable { /* TODO */ })
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier
                    .horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    featuredRestaurants.forEach { restaurant ->
                        RestaurantCard(
                            name = restaurant.name,
                            description = restaurant.description,
                            deliveryInfo = restaurant.deliveryInfo,
                            imageRes = restaurant.imageRes,
                            rating = restaurant.rating,
                            onClick = { mainNavController.navigate("food_details/${restaurant.id}") }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Text("Popular Items", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Spacer(modifier = Modifier.height(16.dp))
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    popularItems.forEach { item ->
                        PopularItemCard(
                            name = item.name,
                            restaurant = item.restaurantName,
                            price = "$${String.format("%.2f", item.price)}",
                            imageRes = item.imageRes,
                            onClick = { mainNavController.navigate("food_details/${item.id}") }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

        if (showAddressSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAddressSheet = false },
                sheetState = sheetState,
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                AddressSheetContent(
                    addresses = addresses,
                    selectedAddress = selectedAddress,
                    onAddressSelected = { newAddress ->
                        selectedAddressState.value = newAddress
                        showAddressSheet = false
                    },
                    onAddNewAddress = {
                        showAddressSheet = false
                        mainNavController.navigate("add_address")
                    }
                )
            }
        }
    }
}

@Composable
private fun AddressListItem(address: Address, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_location),
            contentDescription = null,
            tint = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = address.fullAddress,
            modifier = Modifier.weight(1f),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurface
        )
        RadioButton(
            selected = isSelected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
fun SideMenuFigma(modifier: Modifier = Modifier, mainNavController: NavHostController, nestedNavController: NavHostController) {
    val user = DemoDataProvider.user
    Column(modifier = modifier.background(MaterialTheme.colorScheme.surface).padding(24.dp)) {
        Spacer(modifier = Modifier.height(40.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(painterResource(id = user.profileImageRes), "Profile", modifier = Modifier
                .size(64.dp)
                .clip(CircleShape))
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(user.name, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, color = MaterialTheme.colorScheme.onSurface)
                Text(user.email, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Spacer(modifier = Modifier.height(48.dp))
        SideMenuItemFigma("My Orders", R.drawable.ic_order, onClick = { mainNavController.navigate("orders") })
        SideMenuItemFigma("My Profile", R.drawable.ic_profile, onClick = { nestedNavController.navigate(BottomNavTab.Profile.route) })
        SideMenuItemFigma("Delivery Address", R.drawable.ic_location, onClick = { mainNavController.navigate("add_address") })
        SideMenuItemFigma("Payment Methods", R.drawable.ic_wallet, onClick = { mainNavController.navigate("payment_methods") })
        SideMenuItemFigma("Contact Us", R.drawable.ic_message, onClick = { mainNavController.navigate("contact_us") })
        SideMenuItemFigma("Settings", R.drawable.ic_settings, onClick = { mainNavController.navigate("settings") })
        SideMenuItemFigma("Helps & FAQs", R.drawable.ic_help, onClick = { mainNavController.navigate("help") })
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { mainNavController.navigate("login") { popUpTo("main_app") { inclusive = true }; launchSingleTop = true } },
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(painterResource(id = R.drawable.ic_logout), "Logout", tint = MaterialTheme.colorScheme.onPrimary)
            Spacer(modifier = Modifier.width(12.dp))
            Text("Log Out", color = MaterialTheme.colorScheme.onPrimary, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun SideMenuItemFigma(title: String, iconRes: Int, onClick: () -> Unit) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable(onClick = onClick)
        .padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(painter = painterResource(id = iconRes), contentDescription = title, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(18.dp))
        Text(title, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun CategoryChip(name: String, imageRes: Int, selected: Boolean = false) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.width(70.dp)) {
        Card(
            modifier = Modifier.size(70.dp),
            shape = CircleShape,
            elevation = CardDefaults.cardElevation(defaultElevation = if (selected) 8.dp else 2.dp),
            colors = CardDefaults.cardColors(containerColor = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(painter = painterResource(id = imageRes), contentDescription = name, modifier = Modifier.size(35.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            name,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
        )
    }
}

@Composable
fun RestaurantCard(name: String, description: String, deliveryInfo: String, imageRes: Int, rating: Float, onClick: () -> Unit) {
    Card(onClick = onClick, shape = RoundedCornerShape(24.dp), modifier = Modifier
        .width(280.dp)
        .height(200.dp), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Box {
            Image(painter = painterResource(id = imageRes), contentDescription = name, modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Box(modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.7f)))))
            Column(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp)) {
                Text(name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.White)
                Text(description, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
                Spacer(modifier = Modifier.height(4.dp))
                Text(deliveryInfo, color = Color.White.copy(alpha = 0.9f), fontSize = 12.sp)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Icon(Icons.Default.Star, "Star", tint = Color(0xFFFFC529), modifier = Modifier.size(12.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("$rating", color = MaterialTheme.colorScheme.onSurface, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
fun PopularItemCard(name: String, restaurant: String, price: String, imageRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = imageRes), contentDescription = name, modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(12.dp)))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
            Text(restaurant, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Text(price, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
    }
}

@Preview(showBackground = true, name = "Home Screen - Light")
@Composable
fun HomeScreenPreviewLight() {
    val dummyNavController = rememberNavController()
    AppetitoTheme(useDarkTheme = false) {
        HomeScreen(nestedNavController = dummyNavController, mainNavController = dummyNavController)
    }
}

@Preview(showBackground = true, name = "Home Screen - Dark")
@Composable
fun HomeScreenPreviewDark() {
    val dummyNavController = rememberNavController()
    AppetitoTheme(useDarkTheme = true) {
        HomeScreen(nestedNavController = dummyNavController, mainNavController = dummyNavController)
    }
}