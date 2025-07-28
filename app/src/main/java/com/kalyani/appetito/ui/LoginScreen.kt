package ui

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.clickable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
    onFacebookLogin: () -> Unit,
    onGoogleLogin: () -> Unit
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .padding(horizontal = 28.dp)
        ) {
            Spacer(modifier = Modifier.height(110.dp))
            Text("Login", fontSize = 36.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
            Spacer(modifier = Modifier.height(28.dp))
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
                    unfocusedContainerColor = Color.Transparent,
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
                    unfocusedContainerColor = Color.Transparent,
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    "Forgot password?",
                    color = Color(0xFFFE724C),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier
                        .padding(top = 4.dp, end = 4.dp)
                        .clickable(onClick = onForgotPassword)
                )
            }
            Spacer(modifier = Modifier.height(22.dp))
            Button(
                onClick = onLoginSuccess,
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("LOGIN", color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text("Don’t have an account? ", color = Color(0xFF5B5B5E), fontSize = 14.sp)
                Text(
                    "Sign Up",
                    color = Color(0xFFFE724C),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable { onNavigateToSignUp() }
                )
            }


            Spacer(modifier = Modifier.height(100.dp))
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

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
                modifier = Modifier.fillMaxWidth()
            ) {
                // Facebook
                Button(
                    onClick = onFacebookLogin,
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook_icon),
                        contentDescription = null,
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
                    onClick = onGoogleLogin,
                    shape = RoundedCornerShape(22.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    elevation = ButtonDefaults.buttonElevation(2.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(54.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google_icon),
                        contentDescription = null,
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
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        onLoginSuccess = {},
        onNavigateToSignUp = {},
        onForgotPassword = {},
        onFacebookLogin = {},
        onGoogleLogin = {}
    )
}