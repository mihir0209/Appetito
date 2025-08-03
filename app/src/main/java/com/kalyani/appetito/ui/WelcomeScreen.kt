package ui

import android.graphics.Color.rgb
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kalyani.appetito.R

@Composable
fun WelcomeScreen(
    onSkip: () -> Unit,
    onSignIn: () -> Unit,
    onGoogle: () -> Unit,
    onFacebook: () -> Unit,
    onEmailOrPhone: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Background Image
        Image(
            painter = painterResource(R.drawable.welcome_bg), // Ensure you have this drawable
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Gradient Overlay (from end to start)
        // This gradient is transparent at the top 40% and fades to a dark color at the bottom.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(rgb(25, 27, 47)).copy(alpha = 1.3f)),
                        startY = 20f // Starts the fade effect further down the screen
                    )
                )
        )

        // Main content column for robust layout
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, bottom = 30.dp)
        ) {
            // 3. Skip Button
            Button(
                onClick = onSkip,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                elevation = ButtonDefaults.buttonElevation(4.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(end = 24.dp)
            ) {
                Text("Skip", fontSize = 14.sp, fontWeight = FontWeight.Medium)
            }

            Spacer(modifier = Modifier.height(80.dp))

            // 4. Welcome Text Block
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 25.dp),
                horizontalAlignment = Alignment.Start // Align text to the left/start
            ) {
                Text(
                    buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = Color(0xFF333333), // A dark gray for good contrast on the image
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 52.sp
                            )
                        ) {
                            append("Welcome to\n")
                        }
                        withStyle(
                            SpanStyle(
                                color = MaterialTheme.colorScheme.primary, // Use theme's primary color
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 48.sp,
                            )
                        ) {
                            append("Appetito")
                        }
                    },
                    lineHeight = 50.sp,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Your favourite foods delivered\nfast at your door.",
                    color = Color(0xFF555555), // A softer gray for the tagline
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }

            // This spacer pushes the login controls to the bottom
            Spacer(modifier = Modifier.weight(1f))

            // 5. Login Controls Block (always on the dark gradient)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(Color.White.copy(alpha = 0.5f)))
                    Text("  sign in with  ", color = Color.White, fontSize = 14.sp)
                    Box(modifier = Modifier.weight(1f).height(1.dp).background(Color.White.copy(alpha = 0.5f)))
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Facebook & Google buttons remain theme-aware
                    SocialButton(
                        text = "FACEBOOK",
                        iconRes = R.drawable.facebook_icon,
                        textColor = Color(0xFF1877F3),
                        onClick = onFacebook,
                        modifier = Modifier.weight(1f)
                    )
                    SocialButton(
                        text = "GOOGLE",
                        iconRes = R.drawable.google_icon,
                        textColor = MaterialTheme.colorScheme.onSurface,
                        onClick = onGoogle,
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedButton(
                    onClick = onEmailOrPhone,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(2.dp, Color.White),
                    modifier = Modifier.fillMaxWidth().height(56.dp)
                ) {
                    Text("Start with email or phone", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Already have an account? ", color = Color.White.copy(alpha = 0.8f), fontSize = 14.sp)
                    Text("Sign In", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.clickable(onClick = onSignIn))
                }
            }
        }
    }
}

// Helper composable for social buttons to reduce repetition
@Composable
private fun SocialButton(
    text: String,
    iconRes: Int,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = ButtonDefaults.buttonElevation(4.dp),
        modifier = modifier.height(56.dp)
    ) {
        Image(painter = painterResource(id = iconRes), contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, color = textColor, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}


@Preview(showBackground = true, name = "Welcome Screen")
@Composable
fun WelcomeScreenPreview() {
    AppetitoTheme {
        WelcomeScreen({}, {}, {}, {}, {})
    }
}