package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

private data class PaymentCard(val type: String, val last4Digits: String, val iconRes: Int)
private val savedCards = listOf(
    PaymentCard("Visa", "4242", R.drawable.ic_visa),
    PaymentCard("Mastercard", "5512", R.drawable.ic_mastercard)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentMethodsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment Methods", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                // CHANGE: Use theme colors for the TopAppBar.
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        // CHANGE: Use the theme's background color.
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // CHANGE: Use onBackground color for the section title.
                Text(
                    "Your Saved Cards",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            items(savedCards) { card ->
                Card(
                    shape = RoundedCornerShape(12.dp),
                    // CHANGE: Use the theme's surface color for the card.
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = card.iconRes),
                            contentDescription = card.type,
                            modifier = Modifier.height(30.dp)
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        // CHANGE: Use onSurface color for the card number text.
                        Text(
                            "**** **** **** ${card.last4Digits}",
                            modifier = Modifier.weight(1f),
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        TextButton(onClick = { /* TODO: Edit Card */ }) {
                            // CHANGE: Use primary color for the "Edit" button text.
                            Text("Edit", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = { /* TODO: Navigate to add new card screen */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    // CHANGE: Use theme colors for the button.
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("ADD NEW CARD", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Payment Methods - Light")
@Composable
fun PaymentMethodsScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        PaymentMethodsScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Payment Methods - Dark")
@Composable
fun PaymentMethodsScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        PaymentMethodsScreen(navController = rememberNavController())
    }
}