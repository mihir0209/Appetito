package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
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
import com.kalyani.appetito.R

@Preview(showBackground = true)
@Composable
fun MyOrdersUpcomingScreen() {
    val upcomingOrders = DemoDataProvider.upcomingOrders
    val historyOrders = DemoDataProvider.historyOrders
    var selectedTab by remember { mutableStateOf(0) } // 0 = Upcoming, 1 = History

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
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
                text = "My Orders",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF111719),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        // Tabs
        Row(
            modifier = Modifier
                .padding(horizontal = 22.dp)
                .height(55.dp)
                .clip(RoundedCornerShape(27.5.dp))
                .background(Color(0xFFF2EAEA)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OrderTabButton(
                text = "Upcoming",
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                modifier = Modifier.weight(1f)
            )
            OrderTabButton(
                text = "History",
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (selectedTab == 0) {
            // Upcoming Orders
            upcomingOrders.forEach { order ->
                UpcomingOrderCard(order)
                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            // History Orders
            Text(
                text = "Lasted Orders",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF111719),
                modifier = Modifier.padding(start = 22.dp, bottom = 8.dp)
            )
            historyOrders.forEach { order ->
                HistoryOrderCard(order)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun OrderTabButton(text: String, selected: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(23.5.dp),
        colors = if (selected) ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C))
        else ButtonDefaults.buttonColors(containerColor = Color.White),
        modifier = modifier.height(47.dp)
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color(0xFFFE724C),
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

@Composable
fun UpcomingOrderCard(order: UpcomingOrder) {
    Box(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = order.imageRes.takeIf { it != 0 } ?: R.drawable.image_13),
                    contentDescription = null,
                    modifier = Modifier.size(65.dp).clip(RoundedCornerShape(18.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(order.restaurant, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    Text("${order.items} Items", fontSize = 12.sp, color = Color(0xFF9796A1))
                }
                Text("#${order.id}", fontSize = 16.sp, color = Color(0xFFFE724C), fontWeight = FontWeight.Normal)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Estimated Arrival", fontSize = 12.sp, color = Color(0xFF9796A1))
                Spacer(modifier = Modifier.width(16.dp))
                Text("${order.eta} min", fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.weight(1f))
                Text(order.status, fontSize = 14.sp, color = Color(0xFF111719), fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Button(
                    onClick = { /* TODO: Track Order */ },
                    shape = RoundedCornerShape(28.5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                    modifier = Modifier.weight(1f).height(43.dp)
                ) {
                    Text("Track Order", color = Color.White, fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { /* TODO: Cancel */ },
                    shape = RoundedCornerShape(100.dp),
                    modifier = Modifier.weight(1f).height(43.dp)
                ) {
                    Text("Cancel", color = Color(0xFF111719), fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun HistoryOrderCard(order: HistoryOrder) {
    Box(
        modifier = Modifier
            .padding(horizontal = 22.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(18.dp))
            .background(Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = order.imageRes.takeIf { it != 0 } ?: R.drawable.profile_photo),
                    contentDescription = null,
                    modifier = Modifier.size(46.dp).clip(RoundedCornerShape(18.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(order.restaurant, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                    Text(order.date, fontSize = 12.sp, color = Color(0xFF9796A1))
                }
                Text("$${String.format("%.2f", order.price)}", fontSize = 16.sp, color = Color(0xFFFE724C), fontWeight = FontWeight.Normal)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${order.items} Items", fontSize = 12.sp, color = Color(0xFF9796A1))
                Spacer(modifier = Modifier.width(16.dp))
                Text(order.status, fontSize = 12.sp, color = Color(0xFF4EE476), fontWeight = FontWeight.Normal)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* TODO: Re-Order */ },
                    shape = RoundedCornerShape(28.5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                    modifier = Modifier.height(43.dp)
                ) {
                    Text("Re-Order", color = Color.White, fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { /* TODO: Rate */ },
                    shape = RoundedCornerShape(100.dp),
                    modifier = Modifier.height(43.dp)
                ) {
                    Text("Rate", color = Color(0xFF111719), fontSize = 15.sp)
                }
            }
        }
    }
}

// Orders now come from DemoDataProvider
