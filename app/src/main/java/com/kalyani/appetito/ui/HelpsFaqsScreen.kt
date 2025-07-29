package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

private data class FaqItem(val question: String, val answer: String)

private val faqs = listOf(
    FaqItem("How do I track my order?", "You can track your order in real-time from the 'My Orders' section. You'll see the status of your food from preparation to delivery."),
    FaqItem("Can I cancel my order?", "Orders can be canceled within the first 2 minutes of placing them. After that, the restaurant has already started preparing your food and cancellation is not possible."),
    FaqItem("How do I change my delivery address?", "You can manage your saved addresses in the 'Delivery Address' section of the side menu. You can add, edit, or delete addresses there.")
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpsFaqsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Helps & FAQs", fontWeight = FontWeight.Bold) },
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
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(faqs) { faq ->
                FaqCard(faq = faq)
            }
        }
    }
}

@Composable
private fun FaqCard(faq: FaqItem) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = MaterialTheme.shapes.medium,
        // CHANGE: Use the theme's surface color for the card.
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = faq.question,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f),
                    // CHANGE: Use onSurface color for the question.
                    color = MaterialTheme.colorScheme.onSurface
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    // CHANGE: Use a theme-aware tint for the icon.
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            AnimatedVisibility(visible = isExpanded) {
                Column {
                    // CHANGE: Use a theme-aware color for the divider.
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )
                    // CHANGE: Use onSurfaceVariant for the answer text.
                    Text(text = faq.answer, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Helps & FAQs - Light")
@Composable
fun HelpsFaqsScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        HelpsFaqsScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Helps & FAQs - Dark")
@Composable
fun HelpsFaqsScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        HelpsFaqsScreen(navController = rememberNavController())
    }
}