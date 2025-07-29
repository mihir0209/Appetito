package ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var notificationState by remember { mutableStateOf(true) }

    // -- THEME LOGIC --
    val context = LocalContext.current
    val themeViewModel: ThemeViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
            // Unchecked cast is safe here as we know we are creating a ThemeViewModel
            @Suppress("UNCHECKED_CAST")
            return ThemeViewModel(context) as T
        }
    })
    val currentThemeSetting by themeViewModel.theme

    // Determine if the app is currently using a dark theme, considering the "System" option.
    val useDarkTheme = when (currentThemeSetting) {
        Theme.Light -> false
        Theme.Dark -> true
        Theme.System -> isSystemInDarkTheme()
    }

    // -- UI --
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Settings", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                // CHANGE: Use theme colors for the TopAppBar for theme awareness.
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
                .padding(16.dp)
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                // CHANGE: Use the theme's surface color for the card.
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column {
                    SettingsToggleRow(
                        iconRes = R.drawable.ic_notification,
                        title = "Push Notifications",
                        subtitle = "Receive updates on your orders",
                        checked = notificationState,
                        onCheckedChange = { notificationState = it }
                    )
                    // CHANGE: Use theme color for the divider.
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant
                    )

                    // The Dark Mode toggle now reflects the actual display theme.
                    SettingsToggleRow(
                        iconRes = R.drawable.ic_dark_mode,
                        title = "Dark Mode",
                        subtitle = if (useDarkTheme) "Enabled" else "Disabled",
                        checked = useDarkTheme,
                        onCheckedChange = { isChecked ->
                            // When toggled, explicitly set the theme to Light or Dark.
                            val newTheme = if (isChecked) Theme.Dark else Theme.Light
                            themeViewModel.setTheme(newTheme)
                        }
                    )
                }
            }
        }
    }
}


@Composable
private fun SettingsToggleRow(
    iconRes: Int,
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            // CHANGE: Use a theme color for the icon tint.
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            // Text color will be inherited from the parent's content color (onSurface).
            Text(title, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            // CHANGE: Use a theme color for the subtitle.
            Text(subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 14.sp)
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                // CHANGE: Use theme colors for the switch.
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary,
                uncheckedThumbColor = MaterialTheme.colorScheme.outline,
                uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        )
    }
}

@Preview(showBackground = true, name = "Settings Screen Light")
@Composable
fun SettingsScreenPreview() {
    AppetitoTheme(useDarkTheme = false) {
        SettingsScreen(navController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Settings Screen Dark")
@Composable
fun SettingsScreenDarkPreview() {
    AppetitoTheme(useDarkTheme = true) {
        SettingsScreen(navController = rememberNavController())
    }
}