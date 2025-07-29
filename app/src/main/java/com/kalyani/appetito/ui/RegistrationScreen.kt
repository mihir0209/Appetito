package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RegistrationScreen(
    onSend: () -> Unit
) {
    val phone = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            // CHANGE: Use theme background color
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- Decorative Circles (Theme-Aware) ---
        Box(
            modifier = Modifier
                .size(96.dp)
                .offset(x = (-46).dp, y = (-21).dp)
                // CHANGE: Use theme primary color
                .border(36.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Box(
            modifier = Modifier
                .size(165.dp)
                .offset(x = (-5).dp, y = (-99).dp)
                // CHANGE: Use a subtle, theme-aware version of the primary color
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(181.dp)
                .offset(x = 298.dp, y = (-109).dp)
                // CHANGE: Use theme primary color
                .background(MaterialTheme.colorScheme.primary, CircleShape)
        )

        // --- Content Column (More Robust Layout) ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp)
        ) {
            Spacer(modifier = Modifier.height(180.dp))

            Text(
                "Registration",
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                // CHANGE: Use onBackground for main titles
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                "Enter your phone number to verify your account",
                // CHANGE: Use onSurfaceVariant for descriptive text
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = phone.value,
                onValueChange = { phone.value = it },
                label = { Text("Phone number", fontSize = 16.sp) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                singleLine = true,
                // CHANGE: Use theme-aware colors for text field
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary
                )
            )

            // This spacer pushes the button to the bottom
            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onSend,
                shape = RoundedCornerShape(28.dp),
                // CHANGE: Use theme colors for the main button
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .height(60.dp)
                    .width(248.dp)
            ) {
                Text("SEND", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(120.dp))
        }
    }
}


// --- Previews ---

@Preview(showBackground = true, name = "Registration Screen - Light")
@Composable
fun RegistrationScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        RegistrationScreen(onSend = {})
    }
}

@Preview(showBackground = true, name = "Registration Screen - Dark")
@Composable
fun RegistrationScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        RegistrationScreen(onSend = {})
    }
}