package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
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
    // The function signature is already correct.
    onSendNewPassword: () -> Unit
) {
    val email = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Decorative circles (placeholders)
        Box(
            modifier = Modifier
                .size(96.dp)
                .offset(x = (-46).dp, y = (-21).dp)
                .border(36.dp, Color(0xFFFE724C), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(165.dp)
                .offset(x = (-5).dp, y = (-99).dp)
                .background(Color(0xFFFFECE7), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(181.dp)
                .offset(x = 298.dp, y = (-109).dp)
                .background(Color(0xFFFE724C), CircleShape)
        )

        // Main content column for a responsive layout
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
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Description
            Text(
                "Please enter your email address to request a password reset",
                color = Color(0xFF9796A1),
                fontSize = 14.sp,
                lineHeight = 20.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Email field
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("E-mail", color = Color(0xFF9796A1), fontSize = 16.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFE724C),
                    unfocusedIndicatorColor = Color(0xFFEEEEEE),
                    focusedTextColor = Color(0xFF111719),
                    unfocusedTextColor = Color(0xFF111719),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                )
            )

            // This spacer pushes the button towards the bottom.
            Spacer(modifier = Modifier.weight(1f))

            // Send new password Button
            Button(
                onClick = onSendNewPassword,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                modifier = Modifier
                    .height(60.dp)
                    .width(248.dp)
            ) {
                Text("SEND", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// Updated preview to match the new function signature.
@Preview(showBackground = true)
@Composable
fun ResetPasswordScreenPreview() {
    ResetPasswordScreen(onSendNewPassword = {})
}