package ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R

@Composable
fun SignUpScreen(
    // The function signature is updated to match MainActivity's calls.
    onSignUp: () -> Unit,
    onNavigateToLogin: () -> Unit,
    onFacebookSignUp: () -> Unit,
    onGoogleSignUp: () -> Unit
) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

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

        // Main content column
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 28.dp)
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Text("Sign Up", fontSize = 36.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.height(28.dp))

            OutlinedTextField(
                value = name.value,
                onValueChange = { name.value = it },
                label = { Text("Full name", color = Color(0xFF9796A1), fontSize = 16.sp) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFE724C),
                    unfocusedIndicatorColor = Color(0xFFEEEEEE),
                    focusedLabelColor = Color(0xFFFE724C),
                    unfocusedLabelColor = Color(0xFF9796A1),
                    focusedTextColor = Color(0xFF111719),
                    unfocusedTextColor = Color(0xFF111719),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text("E-mail", color = Color(0xFF9796A1), fontSize = 16.sp) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFE724C),
                    unfocusedIndicatorColor = Color(0xFFEEEEEE),
                    focusedLabelColor = Color(0xFFFE724C),
                    unfocusedLabelColor = Color(0xFF9796A1),
                    focusedTextColor = Color(0xFF111719),
                    unfocusedTextColor = Color(0xFF111719),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(18.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password", color = Color(0xFF9796A1), fontSize = 16.sp) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color(0xFFFE724C),
                    unfocusedIndicatorColor = Color(0xFFEEEEEE),
                    focusedLabelColor = Color(0xFFFE724C),
                    unfocusedLabelColor = Color(0xFF9796A1),
                    focusedTextColor = Color(0xFF111719),
                    unfocusedTextColor = Color(0xFF111719),
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                // Wired to the correct parameter.
                onClick = onSignUp,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("SIGN UP", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(18.dp))

            // --- "Already have an account? Login" ABOVE social options ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 22.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Already have an account? ", color = Color(0xFF5B5B5E), fontSize = 14.sp)
                Text(
                    "Login",
                    color = Color(0xFFFE724C),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 2.dp)
                        // Wired to the correct parameter.
                        .clickable(onClick = onNavigateToLogin)
                )
            }

            Spacer(modifier = Modifier.height(100.dp))
            // --- Centered divider with 'Sign up with' text ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f).height(1.dp).background(Color(0xFFEEEEEE)))
                Text(
                    "  Sign up with  ",
                    color = Color(0xFF5B5B5E),
                    fontSize = 14.sp
                )
                Box(modifier = Modifier.weight(1f).height(1.dp).background(Color(0xFFEEEEEE)))
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- Social signup buttons ---
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Facebook
                Button(
                    // Wired to the correct parameter.
                    onClick = onFacebookSignUp,
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_icon),
                        contentDescription = "Facebook Sign Up",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "FACEBOOK",
                        color = Color(0xFF1877F3),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
                // Google
                Button(
                    // Wired to the correct parameter.
                    onClick = onGoogleSignUp,
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = "Google Sign Up",
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        "GOOGLE",
                        color = Color(0xFF5B5B5E),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 13.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp)) // Space from bottom, as needed
        }
    }
}

// The Preview is updated to call the function with the new, correct signature.
@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(
        onSignUp = {},
        onNavigateToLogin = {},
        onFacebookSignUp = {},
        onGoogleSignUp = {}
    )
}