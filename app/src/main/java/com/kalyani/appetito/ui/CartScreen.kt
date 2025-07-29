package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R
import kotlin.collections.sumOf

// In ui/CartScreen.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(nestedNavController: NavHostController, mainNavController: NavHostController) {
    // THE FIX: Directly observe the central cartItems list. No local copies.
    val cartItems = DemoDataProvider.cartItems
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cart", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { nestedNavController.navigate(BottomNavTab.Home.route) { popUpTo(nestedNavController.graph.startDestinationId); launchSingleTop = true } }) {
                        Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        // ... (TopBar is the same) ...
        bottomBar = { CheckoutBar(items = cartItems, onCheckout = { mainNavController.navigate("checkout") }) },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        // THE FIX: The entire content is now conditional.
        if (cartItems.isEmpty()) {
            EmptyCartView(
                onAddItemsClicked = {
                    nestedNavController.navigate(BottomNavTab.Home.route) {
                        popUpTo(nestedNavController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(items = cartItems, key = { it.id }) { item ->
                    AnimatedVisibility(visible = isVisible, enter = fadeIn(tween(500)) + slideInVertically(initialOffsetY = { 40 })) {
                        CartItemCard(
                            item = item,
                            onIncrease = { DemoDataProvider.increaseCartItemQuantity(item) },
                            onDecrease = { DemoDataProvider.decreaseCartItemQuantity(item) },
                            onRemove = { DemoDataProvider.removeCartItem(item) }
                        )
                    }
                }
                item { PriceSummaryCard(items = cartItems) }
            }
        }
    }
}
@Composable
private fun EmptyCartView(onAddItemsClicked: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_cart), // Add a suitable drawable
            contentDescription = "Empty Cart",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("Your Cart is Empty", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("Looks like you haven't added anything to your cart yet.", color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp, bottom = 24.dp))
        Button(
            onClick = onAddItemsClicked,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
            modifier = Modifier.fillMaxWidth(0.8f).height(52.dp)
        ) {
            Text("ADD ITEMS", fontWeight = FontWeight.Bold)
        }
    }
}
@Composable
fun CartItemCard(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = item.imageRes),
                contentDescription = item.name,
                modifier = Modifier.size(80.dp).clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(item.name, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Text(item.description, fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.height(8.dp))
                Text("$${String.format("%.2f", item.price)}", fontSize = 16.sp, color = Color(0xFFFE724C), fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = onRemove, modifier = Modifier.size(32.dp)) {
                    Icon(painter = painterResource(id = R.drawable.ic_close), contentDescription = "Remove", tint = Color.Gray)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    SmallIconButton(onClick = onDecrease, iconRes = R.drawable.ic_minus)
                    Text(text = item.quantity.toString(), fontSize = 16.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp).widthIn(min = 20.dp), textAlign = TextAlign.Center)
                    SmallIconButton(onClick = onIncrease, iconRes = R.drawable.ic_plus, isPrimary = true)
                }
            }
        }
    }
}

// In ui/CartScreen.kt

@Composable
fun PriceSummaryCard(items: List<CartItem>) {
    val subtotal = items.sumOf { (it.price * it.quantity).toDouble() }.toFloat()

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Promo Code", color = Color.Gray) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedBorderColor = Color.LightGray.copy(alpha = 0.5f)
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = { /* TODO */ }, shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color.Black)) {
                    Text("Apply", color = Color.White)
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // This is the only price row now
            PriceRow(label = "Subtotal", value = subtotal)

            Spacer(modifier = Modifier.height(8.dp))

            // THE FIX: The formal sentence is added here.
            Text(
                text = "Delivery fees and taxes will be calculated at checkout.",
                fontSize = 12.sp,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
        }
    }
}

@Composable
fun CheckoutBar(items: List<CartItem>, onCheckout: () -> Unit) {
    // No need to calculate total here anymore, simplifying the component.
    val itemCount = items.sumOf { it.quantity }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // THE FIX: The total price is removed to avoid confusion.
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Total Items",
                    fontSize = 16.sp,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "$itemCount",
                    fontSize = 22.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onCheckout,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("Proceed to Checkout", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun SmallIconButton(onClick: () -> Unit, iconRes: Int, isPrimary: Boolean = false) {
    Button(
        onClick = onClick,
        shape = CircleShape,
        colors = if (isPrimary) ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C))
        else ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFE724C)),
        border = if (!isPrimary) BorderStroke(1.dp, Color(0xFFFE724C).copy(alpha = 0.5f)) else null,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier.size(28.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = if (isPrimary) Color.White else Color(0xFFFE724C),
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
private fun PriceRow(label: String, value: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "$${String.format("%.2f", value)}",
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}


@Preview(showBackground = true)
@Composable
fun CartScreenPreview() {
    CartScreen(nestedNavController = rememberNavController(), mainNavController = rememberNavController())
}