package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R
import kotlinx.coroutines.delay
import kotlin.collections.sumOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen() {
    // This state management is perfect for a self-contained preview.
    var cartItems by remember { mutableStateOf(DemoDataProvider.cartItems) }

    // This state will trigger our animations.
    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Scaffold(
        // The TopAppBar provides a fixed header that does not scroll.
        topBar = {
            TopAppBar(
                title = { Text("Cart", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Back navigation */ }) {
                        Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black
                )
            )
        },
        // The bottomBar provides a robust "sticky footer" for the checkout button.
        bottomBar = {
            CheckoutBar(items = cartItems) {
                // TODO: Handle checkout logic
            }
        },
        containerColor = Color(0xFFF5F5F5) // A light gray background makes white cards pop.
    ) { innerPadding ->
        // LazyColumn is more performant than a simple Column with a scroll modifier.
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            itemsIndexed(
                items = cartItems,
                key = { _, item -> item.name } // A key helps Compose optimize updates.
            ) { index, item ->
                // This will animate each item fading and sliding in, one after another.
                AnimatedVisibility(
                    visible = isVisible,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = index * 100)) +
                            slideInVertically(
                                initialOffsetY = { 40 },
                                animationSpec = tween(durationMillis = 500, delayMillis = index * 100)
                            )
                ) {
                    CartItemCard(
                        item = item,
                        onIncrease = {
                            cartItems = cartItems.toMutableList().apply { this[index] = item.copy(quantity = item.quantity + 1) }
                        },
                        onDecrease = {
                            if (item.quantity > 1) {
                                cartItems = cartItems.toMutableList().apply { this[index] = item.copy(quantity = item.quantity - 1) }
                            } else {
                                // If quantity is 1, decrease removes the item.
                                cartItems = cartItems.toMutableList().apply { removeAt(index) }
                            }
                        },
                        onRemove = {
                            cartItems = cartItems.toMutableList().apply { removeAt(index) }
                        }
                    )
                }
            }

            // Price Summary and Promo Code section
            item {
                PriceSummaryCard(items = cartItems)
            }
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

@Composable
fun PriceSummaryCard(items: List<CartItem>) {
    val subtotal = items.sumOf { (it.price * it.quantity).toDouble() }.toFloat()
    val taxAndFees = 5.30f // Example values
    val delivery = 1.00f

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
            Divider(modifier = Modifier.padding(vertical = 16.dp))
            PriceRow(label = "Subtotal", value = subtotal)
            PriceRow(label = "Tax and Fees", value = taxAndFees)
            PriceRow(label = "Delivery", value = delivery)
        }
    }
}

@Composable
fun CheckoutBar(items: List<CartItem>, onCheckout: () -> Unit) {
    val subtotal = items.sumOf { (it.price * it.quantity).toDouble() }.toFloat()
    val total = subtotal + 5.30f + 1.00f // Replace with actual logic
    val itemCount = items.sumOf { it.quantity }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Total", fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.width(4.dp))
                Text("($itemCount items)", fontSize = 16.sp, color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Text("$${String.format("%.2f", total)}", fontSize = 22.sp, color = Color.Black, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = onCheckout,
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                modifier = Modifier.fillMaxWidth().height(52.dp)
            ) {
                Text("Checkout", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// A helper composable for the small +/- buttons to reduce repetition.
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

// A cleaner version of the price row.
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
    CartScreen()
}