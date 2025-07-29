package ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import kotlinx.coroutines.delay

data class AddOn(val name: String, val price: Float)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailsScreen(
    itemId: String?,
    navController: NavHostController
) {
    val foodItem = remember(itemId) { DemoDataProvider.getItemById(itemId) }

    var quantity by remember { mutableStateOf(1) }
    val addOns = remember { listOf(AddOn("Pepper Julienned", 2.30f), AddOn("Baby Spinach", 4.70f), AddOn("Masroom", 2.50f)) }
    var selectedAddOns by remember { mutableStateOf(setOf<AddOn>()) }
    var isAddedToCart by remember { mutableStateOf(false) }
    var isContentVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isContentVisible = true }
    LaunchedEffect(quantity, selectedAddOns) { if (isAddedToCart) { isAddedToCart = false } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(painterResource(id = R.drawable.ic_back), "Back", tint = MaterialTheme.colorScheme.onSurface)
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Handle favorite */ }) {
                        Icon(painterResource(id = R.drawable.ic_favorite), "Favorite", tint = MaterialTheme.colorScheme.primary)
                    }
                },
                // This transparent color is intentional for the design
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        },
        bottomBar = {
            if (foodItem != null) {
                AddToCartBar(
                    foodItem = foodItem,
                    quantity = quantity,
                    selectedAddOns = selectedAddOns,
                    isAddedToCart = isAddedToCart,
                    onAddToCart = {
                        DemoDataProvider.addToCart(foodItem, quantity, selectedAddOns)
                        isAddedToCart = true
                    }
                )
            }
        },
        // CHANGE: Use theme background
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            if (foodItem == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Sorry, item not found!", color = MaterialTheme.colorScheme.onBackground)
                }
            } else {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Image(
                        painter = painterResource(id = foodItem.imageRes),
                        contentDescription = foodItem.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp),
                        contentScale = ContentScale.Crop
                    )

                    AnimatedVisibility(
                        visible = isContentVisible,
                        enter = fadeIn(tween(500, 200)) + slideInVertically(tween(500, 200), initialOffsetY = { it / 2 })
                    ) {
                        Column {
                            FoodItemHeader(foodItem = foodItem, quantity = quantity, onQuantityChange = { newQuantity -> quantity = newQuantity })
                            Text(text = foodItem.description, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 15.sp, lineHeight = 22.sp, modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                            Text(text = "Choice of Add On", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground, modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp))
                            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                                addOns.forEach { addOn ->
                                    AddOnRow(addOn = addOn, isSelected = addOn in selectedAddOns, onSelect = { isSelected ->
                                        selectedAddOns = if (isSelected) selectedAddOns + addOn else selectedAddOns - addOn
                                    })
                                }
                            }
                            Spacer(Modifier.height(120.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun FoodItemHeader(foodItem: FoodItemDetails, quantity: Int, onQuantityChange: (Int) -> Unit) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(text = foodItem.name, fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = null, tint = Color(0xFFFFC529), modifier = Modifier.size(20.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("${foodItem.rating}", fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onBackground, fontSize = 14.sp)
            Text(" (${foodItem.reviewCount}+ Reviews)", color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp, modifier = Modifier.padding(start = 4.dp))
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "$${String.format("%.2f", foodItem.price)}", color = MaterialTheme.colorScheme.primary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = { if (quantity > 1) onQuantityChange(quantity - 1) },
                    shape = CircleShape,
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_minus), contentDescription = "Decrease", tint = MaterialTheme.colorScheme.onSurface)
                }
                Text(text = quantity.toString(), fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                Button(
                    onClick = { onQuantityChange(quantity + 1) },
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = "Increase", tint = MaterialTheme.colorScheme.onPrimary)
                }
            }
        }
    }
}

@Composable
fun AddOnRow(addOn: AddOn, isSelected: Boolean, onSelect: (Boolean) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onSelect(!isSelected) }
    ) {
        Image(painter = painterResource(id = R.drawable.food_placeholder), contentDescription = null, modifier = Modifier
            .size(52.dp)
            .clip(RoundedCornerShape(12.dp)))
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = addOn.name, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
            Text(text = "+ $${String.format("%.2f", addOn.price)}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Checkbox(
            checked = isSelected,
            onCheckedChange = onSelect,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.outline
            )
        )
    }
}

@Composable
private fun AddToCartBar(
    foodItem: FoodItemDetails,
    quantity: Int,
    selectedAddOns: Set<AddOn>,
    isAddedToCart: Boolean,
    onAddToCart: () -> Unit
) {
    val totalAddOnPrice = selectedAddOns.sumOf { it.price.toDouble() }.toFloat()
    val totalPrice = (foodItem.price + totalAddOnPrice) * quantity
    var showAnimation by remember { mutableStateOf(false) }

    LaunchedEffect(isAddedToCart) {
        if (isAddedToCart) {
            showAnimation = true
            delay(2000)
            showAnimation = false
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Total Price", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(text = "$${String.format("%.2f", totalPrice)}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
            }
            AnimatedContent(
                targetState = showAnimation,
                transitionSpec = { fadeIn(tween(200)) togetherWith fadeOut(tween(200)) },
                label = "AddToCartButtonAnimation"
            ) { added ->
                Button(
                    onClick = { onAddToCart() },
                    enabled = !added,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier
                        .height(52.dp)
                        .width(180.dp)
                ) {
                    if (added) {
                        Icon(painterResource(id = R.drawable.ic_check), "Added", tint = MaterialTheme.colorScheme.onSecondary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Added!", color = MaterialTheme.colorScheme.onSecondary, fontWeight = FontWeight.Bold)
                    } else {
                        Icon(painterResource(id = R.drawable.ic_cart), "Add to Cart", tint = MaterialTheme.colorScheme.onPrimary)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Add to Cart", color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Food Details - Light")
@Composable
fun FoodDetailsScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        FoodDetailsScreen(itemId = "cheese_burger", navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Food Details - Dark")
@Composable
fun FoodDetailsScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        FoodDetailsScreen(itemId = "cheese_burger", navController = rememberNavController())
    }
}