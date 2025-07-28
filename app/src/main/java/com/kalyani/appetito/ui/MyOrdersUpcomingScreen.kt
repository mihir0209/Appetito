package ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyOrdersUpcomingScreen(navController: NavHostController) {
    val upcomingOrders = DemoDataProvider.upcomingOrders
    val historyOrders = DemoDataProvider.historyOrders
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Orders", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    // THE FIX: This button now navigates to the correct top-level route.
                    IconButton(onClick = {
                        // Navigate back to the main app screen container.
                        navController.navigate("main_app") {
                            // Pop up to the "welcome" screen to clear the auth flow
                            // This ensures the back stack is clean.
                            popUpTo("welcome") {
                                inclusive = true
                            }
                        }
                    }) {
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
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = Color(0xFFFE724C)
                    )
                }
            ) {
                Tab(selected = selectedTab == 0, onClick = { selectedTab = 0 }, text = { Text("Upcoming", color = if (selectedTab == 0) Color(0xFFFE724C) else Color.Gray) })
                Tab(selected = selectedTab == 1, onClick = { selectedTab = 1 }, text = { Text("History", color = if (selectedTab == 1) Color(0xFFFE724C) else Color.Gray) })
            }

            AnimatedContent(
                targetState = selectedTab,
                transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) },
                label = "OrderTabAnimation"
            ) { tabIndex ->
                LazyColumn(
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    if (tabIndex == 0) {
                        items(upcomingOrders) { order -> UpcomingOrderCard(order) }
                    } else {
                        items(historyOrders) { order -> HistoryOrderCard(order) }
                    }
                }
            }
        }
    }
}


@Composable
fun UpcomingOrderCard(order: UpcomingOrder) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = order.imageRes.takeIf { it != 0 } ?: R.drawable.image_13),
                    contentDescription = null,
                    modifier = Modifier.size(60.dp).clip(RoundedCornerShape(12.dp))
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(order.restaurant, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                    Text("${order.items} Items", fontSize = 14.sp, color = Color.Gray)
                }
                Text("#${order.id}", fontSize = 14.sp, color = Color.Gray)
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(modifier = Modifier.weight(1f)) {
                    Text("Estimated Arrival", fontSize = 12.sp, color = Color.Gray)
                    Text("${order.eta} min", fontSize = 15.sp, color = Color.Black, fontWeight = FontWeight.SemiBold)
                }
                Text(order.status, fontSize = 14.sp, color = Color(0xFF111719), fontWeight = FontWeight.Medium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = { /* TODO: Track Order */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("Track Order", color = Color.White, fontSize = 15.sp)
                }
                OutlinedButton(
                    onClick = { /* TODO: Cancel */ },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f).height(48.dp)
                ) {
                    Text("Cancel", color = Color.Gray, fontSize = 15.sp)
                }
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


// THE FIX: The preview now passes a dummy NavController, which satisfies the function's requirement.
@Preview(showBackground = true)
@Composable
fun MyOrdersUpcomingScreenPreview() {
    MyOrdersUpcomingScreen(navController = rememberNavController())
}