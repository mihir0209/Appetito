package ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MyOrdersUpcomingScreen(navController: NavHostController) {
    // THE FIX: Reads the dynamic list of placed orders
    val upcomingOrders = DemoDataProvider.placedUpcomingOrders
    val historyOrders = DemoDataProvider.historyOrders

    // --- State for the Swipeable Tabs (Pager) ---
    val pagerState = rememberPagerState(pageCount = { 2 })
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Use simple back for now
                        Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        },
        containerColor = Color(0xFFF5F5F5)
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage]), color = Color(0xFFFE724C))
                }
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(0) } },
                    text = { Text("Upcoming", color = if (pagerState.currentPage == 0) Color(0xFFFE724C) else Color.Gray) }
                )
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = { coroutineScope.launch { pagerState.animateScrollToPage(1) } },
                    text = { Text("History", color = if (pagerState.currentPage == 1) Color(0xFFFE724C) else Color.Gray) }
                )
            }

            // THE FIX: HorizontalPager makes the content swipeable
            HorizontalPager(state = pagerState) { page ->
                when (page) {
                    0 -> UpcomingOrdersList(orders = upcomingOrders)
                    1 -> HistoryOrdersList(orders = historyOrders)
                }
            }
        }
    }
}

@Composable
fun UpcomingOrdersList(orders: List<UpcomingOrder>) {
    if (orders.isEmpty()) {
        EmptyOrdersView()
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
            items(orders, key = { it.id }) { order ->
                UpcomingOrderCard(order)
            }
        }
    }
}

@Composable
fun HistoryOrdersList(orders: List<HistoryOrder>) {
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(orders, key = { it.id }) { order ->
            HistoryOrderCard(order)
        }
    }
}

@Composable
fun UpcomingOrderCard(order: UpcomingOrder) {
    var isCancelled by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.Center) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                // THE FIX: This now looks like the Cart items
                order.items.forEach { item ->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(bottom = 8.dp)) {
                        Image(
                            painter = painterResource(id = item.imageRes),
                            contentDescription = item.name,
                            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp))
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.Bold)
                            Text("${item.quantity}x", color = Color.Gray, fontSize = 12.sp)
                        }
                    }
                }
                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text("ETA: ${order.eta} min", fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                        Text(order.status, fontSize = 14.sp, color = Color.Gray)
                    }
                    Text("#${order.id}", fontSize = 14.sp, color = Color.Gray)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { /* TODO: Track Order */ }, shape = RoundedCornerShape(12.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)), modifier = Modifier.weight(1f).height(48.dp)) {
                        Text("Track Order", color = Color.White, fontSize = 15.sp)
                    }
                    OutlinedButton(
                        onClick = { isCancelled = true },
                        enabled = !isCancelled,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.weight(1f).height(48.dp)
                    ) {
                        Text("Cancel", color = Color.Gray, fontSize = 15.sp)
                    }
                }
            }
        }

        // THE FIX: The "Cancelled" overlay
        AnimatedVisibility(
            visible = isCancelled,
            enter = fadeIn(animationSpec = tween(300))
        ) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "CANCELLED",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}
@Composable
fun HistoryOrderCard(order: HistoryOrder) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = order.imageRes.takeIf { it != 0 } ?: R.drawable.profile_photo),
                    contentDescription = null,
                    modifier = Modifier.size(52.dp).clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(order.restaurant, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text(order.date, fontSize = 12.sp, color = Color.Gray)
                }
                Text("$${String.format("%.2f", order.price)}", fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${order.items} Items", fontSize = 14.sp, color = Color.Gray)
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { /* TODO: Re-Order */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Re-Order", color = Color.White, fontSize = 15.sp)
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = { /* TODO: Rate */ },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.height(48.dp)
                ) {
                    Text("Rate", color = Color.Gray, fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
private fun EmptyOrdersView() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_empty_box), // Add a suitable drawable
            contentDescription = "No Orders",
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text("No Upcoming Orders", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text("You haven't placed an order yet. Your upcoming orders will be shown here.", color = Color.Gray, textAlign = TextAlign.Center, modifier = Modifier.padding(top = 8.dp))
    }
}
// THE FIX: The preview now passes a dummy NavController, which satisfies the function's requirement.
@Preview(showBackground = true)
@Composable
fun MyOrdersUpcomingScreenPreview() {
    MyOrdersUpcomingScreen(navController = rememberNavController())
}