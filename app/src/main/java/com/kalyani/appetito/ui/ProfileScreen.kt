package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(
    mainNavController: NavHostController
) {
    var showContent by remember { mutableStateOf(false) }
    // CHANGE: Reduced animation delay
    LaunchedEffect(Unit) {
        delay(100)
        showContent = true
    }

    val user = DemoDataProvider.user

    Column(
        modifier = Modifier
            .fillMaxSize()
            // CHANGE: Use theme background color
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(user = user, showContent = showContent)

        AnimatedVisibility(
            visible = showContent,
            // CHANGE: Reduced animation duration
            enter = fadeIn(animationSpec = tween(durationMillis = 500, delayMillis = 150)),
            exit = fadeOut(animationSpec = tween())
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Account Information Card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    // CHANGE: Use theme surface color
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        ProfileInfoRow(icon = R.drawable.ic_profile, label = "Full Name", value = user.name)
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                        ProfileInfoRow(icon = R.drawable.ic_message, label = "E-mail", value = user.email)
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                        ProfileInfoRow(icon = R.drawable.ic_phone, label = "Phone Number", value = user.phone)
                    }
                }

                // General Options Card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 8.dp)) {
                        ProfileOptionRow(icon = R.drawable.ic_order, text = "My Orders", onClick = { mainNavController.navigate("orders") })
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                        ProfileOptionRow(icon = R.drawable.ic_location, text = "Delivery Addresses", onClick = { mainNavController.navigate("add_address") })
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = MaterialTheme.colorScheme.surfaceVariant)
                        ProfileOptionRow(icon = R.drawable.ic_settings, text = "Settings", onClick = { mainNavController.navigate("settings") })
                    }
                }

                // Logout Button
                OutlinedButton(
                    onClick = { /* TODO: Handle Logout */ },
                    shape = RoundedCornerShape(12.dp),
                    // CHANGE: Use theme error color for border
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.error.copy(alpha = 0.5f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // CHANGE: Use theme error color
                    Icon(imageVector = Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Log Out", tint = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Log Out", color = MaterialTheme.colorScheme.error, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(user: DemoUser, showContent: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                // CHANGE: Use theme primary color
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
            )
            .padding(top = 40.dp, bottom = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AnimatedVisibility(
            visible = showContent,
            // CHANGE: Reduced animation duration
            enter = fadeIn(tween(500)) + scaleIn(tween(500)),
            exit = fadeOut()
        ) {
            Image(
                painter = painterResource(id = user.profileImageRes),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    // CHANGE: Use theme surface color for border
                    .border(2.dp, MaterialTheme.colorScheme.surface, CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        AnimatedVisibility(
            visible = showContent,
            // CHANGE: Reduced animation duration and delay
            enter = fadeIn(tween(500, 100))
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                // CHANGE: Use onPrimary color for text on primary background
                Text(text = user.name, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                Text(text = user.email, fontSize = 16.sp, color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f))
            }
        }
    }
}

@Composable
private fun ProfileInfoRow(icon: Int, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            // CHANGE: Use theme primary color for icon
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = label, fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(text = value, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun ProfileOptionRow(icon: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = text,
            // CHANGE: Use onSurfaceVariant for icon
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.weight(1f))
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = null,
            // CHANGE: Use onSurfaceVariant for icon
            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
        )
    }
}


// --- Previews ---

@Preview(showBackground = true, name = "Profile Screen - Light")
@Composable
fun ProfileScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        ProfileScreen(mainNavController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Profile Screen - Dark")
@Composable
fun ProfileScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        ProfileScreen(mainNavController = rememberNavController())
    }
}