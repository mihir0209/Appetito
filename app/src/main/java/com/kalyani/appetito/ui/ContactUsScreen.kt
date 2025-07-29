package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactUsScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contact Us", fontWeight = FontWeight.Bold) },
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // CHANGE: Use theme colors for text.
            Text(
                "How can we help you?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                "Our team is ready to assist you. Please choose a contact method below.",
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            ContactOptionCard(
                iconRes = R.drawable.ic_chat,
                title = "Chat with Us",
                subtitle = "Get instant support via live chat.",
                onClick = { /* TODO: Open live chat */ }
            )

            ContactOptionCard(
                iconRes = R.drawable.ic_email,
                title = "Email Us",
                subtitle = "Send us an email at support@appetito.com",
                onClick = { /* TODO: Open email client */ }
            )
        }
    }
}

@Composable
private fun ContactOptionCard(iconRes: Int, title: String, subtitle: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        // CHANGE: Use the theme's surface color for the card.
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = null,
                // CHANGE: Use the theme's primary color for the icon tint.
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                // CHANGE: Use onSurface for primary text on the card.
                Text(title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface)
                // CHANGE: Use onSurfaceVariant for secondary text.
                Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                // CHANGE: Use onSurfaceVariant for the arrow icon.
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@Preview(showBackground = true, name = "Contact Us Screen - Light")
@Composable
fun ContactUsScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        ContactUsScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Contact Us Screen - Dark")
@Composable
fun ContactUsScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        ContactUsScreen(navController = rememberNavController())
    }
}