package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VerificationCodeScreen(
    // The function signature is already correct.
    onVerify: () -> Unit,
    onResend: () -> Unit
) {
    val code1 = remember { mutableStateOf("") }
    val code2 = remember { mutableStateOf("") }
    val code3 = remember { mutableStateOf("") }
    val code4 = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Decorative circles remain in the background
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

        // A single column for a more responsive layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(140.dp))

            Text(
                "Verification Code",
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                "Please type the verification code sent to your email.",
                color = Color(0xFF9796A1),
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Code input fields
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                val codeFieldModifier = Modifier.width(60.dp)
                CodeTextField(value = code1.value, onValueChange = { code1.value = it }, modifier = codeFieldModifier)
                CodeTextField(value = code2.value, onValueChange = { code2.value = it }, modifier = codeFieldModifier)
                CodeTextField(value = code3.value, onValueChange = { code3.value = it }, modifier = codeFieldModifier)
                CodeTextField(value = code4.value, onValueChange = { code4.value = it }, modifier = codeFieldModifier)
            }

            // Spacer with weight pushes the content below it to the bottom.
            Spacer(modifier = Modifier.weight(1f))

            // Resend code
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("I don’t receive a code! ", color = Color(0xFF5B5B5E), fontSize = 16.sp)
                Text(
                    "Please resend",
                    color = Color(0xFFFE724C),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    // The onResend callback is now correctly wired here.
                    modifier = Modifier.clickable { onResend() }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Verify Button
            Button(
                onClick = onVerify,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                modifier = Modifier
                    .height(60.dp)
                    .width(248.dp)
            ) {
                Text("VERIFY", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

// A helper composable to reduce repetition for the text fields.
@Composable
private fun CodeTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { if (it.length <= 1) onValueChange(it) },
        modifier = modifier,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center, fontSize = 24.sp),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color(0xFFFE724C),
            unfocusedIndicatorColor = Color(0xFFEEEEEE),
            focusedTextColor = Color(0xFF111719),
            unfocusedTextColor = Color(0xFF111719),
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color(0xFFF9F9F9),
        )
    )
}

@Preview(showBackground = true)
@Composable
fun VerificationCodeScreenPreview() {
    // Calling the main composable with empty lambdas for the preview.
    VerificationCodeScreen(
        onVerify = {},
        onResend = {}
    )
}