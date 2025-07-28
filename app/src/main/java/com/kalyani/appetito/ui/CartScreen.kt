package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.kalyani.appetito.R
import kotlin.collections.sumOf


@Preview(showBackground = true)
@Composable
fun CartScreen() {
    var cartItems by remember { mutableStateOf(DemoDataProvider.cartItems) }

    val subtotal = cartItems.sumOf { (it.price * it.quantity).toDouble() }.toFloat()

    val taxAndFees = 5.30f
    val delivery = 1.00f
    val total = subtotal + taxAndFees + delivery
    val itemCount = cartItems.sumOf { it.quantity }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)
    ) {
        // Scrollable cart + promo + price breakdown
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 120.dp) // leave space for bottom bar
                .verticalScroll(rememberScrollState())
        ) {
            // Top bar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 32.dp, start = 22.dp, end = 22.dp, bottom = 16.dp)
            ) {
                IconButton(onClick = { /* TODO: Back navigation */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = "Back",
                        tint = Color(0xFF111719)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Cart",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF111719)
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            // Cart items
            cartItems.forEachIndexed { idx, item ->
                CartItemRow(
                    item = item,
                    onIncrease = {
                        val updatedItems = cartItems.toMutableList().apply {
                            this[idx] = this[idx].copy(quantity = this[idx].quantity + 1)
                        }
                        cartItems = updatedItems
                    },
                    onDecrease = {
                        if (item.quantity > 1) {
                            val updatedItems = cartItems.toMutableList().apply {
                                this[idx] = this[idx].copy(quantity = this[idx].quantity - 1)
                            }
                            cartItems = updatedItems
                        } else {
                            val updatedItems = cartItems.toMutableList().apply {
                                removeAt(idx)
                            }
                            cartItems = updatedItems
                        }
                    },
                    onRemove = {
                        val updatedItems = cartItems.toMutableList().apply {
                            removeAt(idx)
                        }
                        cartItems = updatedItems
                    }
                )
                Spacer(modifier = Modifier.height(25.dp))
            }

            // Promo code section
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 22.dp)
            ) {
                TextField(
                    value = "",
                    onValueChange = { /* TODO: Save code entry */ },
                    placeholder = { Text("Promo Code", color = Color(0xFFBEBEBE)) },
                    shape = RoundedCornerShape(28.5.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFF8F8FB),
                        unfocusedContainerColor = Color(0xFFF8F8FB),
                        disabledContainerColor = Color(0xFFF8F8FB),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Color(0xFFFE724C)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = { /* TODO: Apply promo */ },
                    shape = RoundedCornerShape(28.5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                    modifier = Modifier.height(44.dp)
                ) {
                    Text("Apply", color = Color.White, fontSize = 16.sp)
                }
            }

            Divider(
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 12.dp),
                color = Color(0xFFF1F2F3)
            )

            PriceRow(label = "Subtotal", value = subtotal)
            PriceRow(label = "Tax and Fees", value = taxAndFees)
            PriceRow(label = "Delivery", value = delivery)
        }

        // Total and Checkout: bottom sticky bar
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                )
                .padding(vertical = 16.dp, horizontal = 22.dp)
                .shadow(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total",
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$${String.format("%.2f", total)} USD",
                    fontSize = 19.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    " USD",
                    fontSize = 15.sp,
                    color = Color(0xFF9796A1),
                    modifier = Modifier.padding(start = 2.dp)
                )
                Text(
                    "  (${itemCount} items)",
                    fontSize = 14.sp,
                    color = Color(0xFFBEBEBE),
                    modifier = Modifier.padding(start = 6.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { /* TODO: Checkout */ },
                shape = RoundedCornerShape(28.5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(57.dp)
            ) {
                Text(
                    "Checkout",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.2.sp
                )
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit,
    onRemove: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = item.imageRes),
            contentDescription = item.name,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(14.dp))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                item.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                item.description,
                fontSize = 14.sp,
                color = Color(0xFF8C8A9D)
            )
            Text(
                "$${String.format("%.2f", item.price)}",
                fontSize = 15.sp,
                color = Color(0xFFFE724C),
                fontWeight = FontWeight.Medium
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        // Quantity controls and Remove button
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(
                onClick = onRemove,
                modifier = Modifier.size(28.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Remove ${item.name}",
                    tint = Color(0xFFFE724C)
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = onDecrease,
                    shape = CircleShape,
                    border = BorderStroke(1.dp, Color(0xFFFE724C)),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_minus),
                        contentDescription = "Decrease quantity of ${item.name}",
                        tint = Color(0xFFFE724C)
                    )
                }
                Text(
                    text = item.quantity.toString().padStart(2, '0'),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .widthIn(min = 18.dp),
                    textAlign = TextAlign.Center
                )
                Button(
                    onClick = onIncrease,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_plus),
                        contentDescription = "Increase quantity of ${item.name}",
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun PriceRow(label: String, value: Float) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 22.dp, vertical = 4.dp)
    ) {
        Text(label, fontSize = 16.sp, color = Color.Black)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "$${String.format("%.2f", value)}",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.Medium
        )
        Text(
            " USD",
            fontSize = 14.sp,
            color = Color(0xFF9796A1),
            modifier = Modifier.padding(start = 2.dp)
        )
    }
    Spacer(modifier = Modifier.height(22.dp))
}
