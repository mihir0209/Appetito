package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R


private enum class PaymentOption { CARD, UPI, NET_BANKING, COD }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(navController: NavHostController) {
    var selectedPaymentOption by remember { mutableStateOf(PaymentOption.CARD) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var showAddressSheet by remember { mutableStateOf(false) }

    val selectedAddressState = DemoDataProvider.selectedAddress
    val addresses = DemoDataProvider.savedAddresses
    val cartItems = DemoDataProvider.cartItems

    val subtotal = cartItems.sumOf { (it.price * it.quantity).toDouble() }.toFloat()
    val deliveryFee = 1.00f
    val taxAndFees = subtotal * 0.08f
    val total = subtotal + deliveryFee + taxAndFees

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Checkout", fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    },
                    // CHANGE: Use theme colors
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            },
            bottomBar = {
                PlaceOrderBar(
                    totalPrice = total,
                    onClick = {
                        DemoDataProvider.placeOrder()
                        navController.navigate("order_success") {
                            // Clear back stack to prevent going back to checkout
                            popUpTo("main_app") { inclusive = false }
                        }
                    }
                )
            },
            // CHANGE: Use theme background color
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    CheckoutSection(title = "Delivery Address") {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(selectedAddressState.value.fullAddress, fontWeight = FontWeight.SemiBold, color = MaterialTheme.colorScheme.onSurface)
                                Text(selectedAddressState.value.cityState, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
                            }
                            TextButton(onClick = { showAddressSheet = true }) {
                                Text("Change", color = MaterialTheme.colorScheme.primary)
                            }
                        }
                    }
                }
                item {
                    CheckoutSection(title = "Order Summary") {
                        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                            PriceRow(label = "Subtotal", value = subtotal)
                            PriceRow(label = "Tax and Fees", value = taxAndFees)
                            PriceRow(label = "Delivery", value = deliveryFee)
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                            PriceRow(label = "Total", value = total, isTotal = true)
                        }
                    }
                }
                item {
                    CheckoutSection(title = "Payment Method") {
                        Column {
                            PaymentOptionRow(text = "Credit/Debit Card", iconRes = R.drawable.ic_credit_card, selected = selectedPaymentOption == PaymentOption.CARD, onClick = { selectedPaymentOption = PaymentOption.CARD })
                            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                            PaymentOptionRow(text = "UPI", iconRes = R.drawable.ic_upi, selected = selectedPaymentOption == PaymentOption.UPI, onClick = { selectedPaymentOption = PaymentOption.UPI })
                            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                            PaymentOptionRow(text = "Net Banking", iconRes = R.drawable.ic_net_banking, selected = selectedPaymentOption == PaymentOption.NET_BANKING, onClick = { selectedPaymentOption = PaymentOption.NET_BANKING })
                            HorizontalDivider(color = MaterialTheme.colorScheme.surfaceVariant)
                            PaymentOptionRow(text = "Cash on Delivery", iconRes = R.drawable.ic_cash_on_delivery, selected = selectedPaymentOption == PaymentOption.COD, onClick = { selectedPaymentOption = PaymentOption.COD })
                        }
                    }
                }
            }
        }

        if (showAddressSheet) {
            ModalBottomSheet(
                onDismissRequest = { showAddressSheet = false },
                sheetState = sheetState,
                // CHANGE: Use theme colors for the sheet
                containerColor = MaterialTheme.colorScheme.surface
            ) {
                AddressSheetContent(
                    addresses = addresses,
                    selectedAddress = selectedAddressState.value,
                    onAddressSelected = { newAddress ->
                        selectedAddressState.value = newAddress
                        // Consider using coroutine scope to hide the sheet smoothly
                        showAddressSheet = false
                    },
                    onAddNewAddress = {
                        showAddressSheet = false
                        navController.navigate("add_address")
                    }
                )
            }
        }
    }
}

@Composable
fun PriceRow(label: String, value: Float, isTotal: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // CHANGE: Use theme colors
        Text(
            label,
            fontSize = 16.sp,
            color = if (isTotal) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "$${String.format("%.2f", value)}",
            fontSize = if (isTotal) 20.sp else 16.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}

@Composable
private fun CheckoutSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(
            title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp),
            color = MaterialTheme.colorScheme.onBackground // CHANGE
        )
        Card(
            shape = RoundedCornerShape(12.dp),
            // CHANGE: Use theme surface color
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                content()
            }
        }
    }
}

@Composable
private fun PaymentOptionRow(text: String, iconRes: Int, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = text,
            // Leaving tint Unspecified is correct for pre-colored brand icons.
            tint = Color.Unspecified,
            modifier = Modifier.height(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text,
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface // CHANGE
        )
        RadioButton(
            selected = selected,
            onClick = onClick,
            // CHANGE: Use theme primary color
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
        )
    }
}

@Composable
private fun PlaceOrderBar(totalPrice: Float, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        // CHANGE: Use theme surface color
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Total", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant) // CHANGE
                Text(
                    text = "$${String.format("%.2f", totalPrice)}",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface // CHANGE
                )
            }
            Button(
                onClick = onClick,
                modifier = Modifier
                    .height(52.dp)
                    .padding(start = 16.dp),
                shape = RoundedCornerShape(12.dp),
                // CHANGE: Use theme colors for button
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text("PLACE ORDER", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}


// --- Previews ---

@Preview(showBackground = true, name = "Checkout Screen - Light")
@Composable
fun CheckoutScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        CheckoutScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Checkout Screen - Dark")
@Composable
fun CheckoutScreenPreviewDark() {
    // Add some items to the cart for a more realistic dark mode preview
    DemoDataProvider.cartItems.clear()
    DemoDataProvider.cartItems.add(
        CartItem(id = "cheese_burger", name = "Cheese Burger", description = "Classic beef burger", price = 5.50f, quantity = 2, imageRes = R.drawable.img_burger)
    )
    AppetitoTheme(useDarkTheme = true) {
        CheckoutScreen(navController = rememberNavController())
    }
}