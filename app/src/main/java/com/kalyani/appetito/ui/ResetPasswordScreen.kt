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
fun ResetPasswordScreen(
    onSendNewPassword: () -> Unit
) {
    val email = remember { mutableStateOf("") }

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(140.dp))

            // Title
            Text(
                "Reset Password",
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                // CHANGE: Use onBackground for title
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                "Please enter your email address to request a password reset",
                // CHANGE: Use onSurfaceVariant for description
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Email field
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("E-mail", fontSize = 16.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                // CHANGE: Use theme-aware colors for text field
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                    focusedLabelColor = MaterialTheme.colorScheme.primary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    cursorColor = MaterialTheme.colorScheme.primary,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onSendNewPassword,
                shape = RoundedCornerShape(28.dp),
                // CHANGE: Use theme colors for button
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier
                    .height(60.dp)
                    .width(248.dp)
            ) {
                Text("SEND", fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// --- Previews ---

@Preview(showBackground = true, name = "Reset Password - Light")
@Composable
fun ResetPasswordScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        ResetPasswordScreen(onSendNewPassword = {})
    }
}

@Preview(showBackground = true, name = "Reset Password - Dark")
@Composable
fun ResetPasswordScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        ResetPasswordScreen(onSendNewPassword = {})
    }
}