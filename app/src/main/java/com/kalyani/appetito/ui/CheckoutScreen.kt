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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Checkout", fontWeight = FontWeight.Bold) },
                navigationIcon = { IconButton(onClick = { navController.popBackStack() }) { Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back") } },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            PlaceOrderBar(onClick = {
                navController.navigate("order_success")
            })
        },
        containerColor = Color(0xFFF5F5F5)
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
                            Text("4102 Pretty View Lane", fontWeight = FontWeight.SemiBold)
                            Text("Los Angeles, CA", color = Color.Gray, fontSize = 14.sp)
                        }
                        TextButton(onClick = { /* TODO */ }) { Text("Change", color = Color(0xFFFE724C)) }
                    }
                }
            }
            item {
                CheckoutSection(title = "Order Summary") {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        // This now works because PriceRow is public
                        PriceRow(label = "Subtotal", value = 54.60f)
                        PriceRow(label = "Tax and Fees", value = 5.30f)
                        PriceRow(label = "Delivery", value = 1.00f)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        PriceRow(label = "Total", value = 60.90f, isTotal = true)
                    }
                }
            }
            item {
                CheckoutSection(title = "Payment Method") {
                    Column {
                        PaymentOptionRow(text = "Credit/Debit Card", iconRes = R.drawable.ic_credit_card, selected = selectedPaymentOption == PaymentOption.CARD, onClick = { selectedPaymentOption = PaymentOption.CARD })
                        HorizontalDivider()
                        PaymentOptionRow(text = "UPI", iconRes = R.drawable.ic_upi, selected = selectedPaymentOption == PaymentOption.UPI, onClick = { selectedPaymentOption = PaymentOption.UPI })
                        HorizontalDivider()
                        PaymentOptionRow(text = "Net Banking", iconRes = R.drawable.ic_net_banking, selected = selectedPaymentOption == PaymentOption.NET_BANKING, onClick = { selectedPaymentOption = PaymentOption.NET_BANKING })
                        HorizontalDivider()
                        PaymentOptionRow(text = "Cash on Delivery", iconRes = R.drawable.ic_cash_on_delivery, selected = selectedPaymentOption == PaymentOption.COD, onClick = { selectedPaymentOption = PaymentOption.COD })
                    }
                }
            }
        }
    }

}

@Composable
fun PriceRow(label: String, value: Float, isTotal: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 16.sp, color = if (isTotal) Color.Black else Color.Gray, fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "$${String.format("%.2f", value)}",
            fontSize = if (isTotal) 20.sp else 16.sp,
            color = Color.Black,
            fontWeight = if (isTotal) FontWeight.Bold else FontWeight.SemiBold
        )
    }
}

@Composable
private fun CheckoutSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, fontSize = 18.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        Card(
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
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
        Icon(painter = painterResource(id = iconRes), contentDescription = text, tint = Color.Unspecified, modifier = Modifier.height(24.dp))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text, modifier = Modifier.weight(1f), fontWeight = FontWeight.SemiBold)
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFE724C))
        )
    }
}

@Composable
private fun PlaceOrderBar(onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(52.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C))
        ) {
            Text("PLACE ORDER", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CheckoutScreenPreview() {
    CheckoutScreen(navController = rememberNavController())
}
