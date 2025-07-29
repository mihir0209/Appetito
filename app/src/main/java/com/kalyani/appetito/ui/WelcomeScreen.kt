package ui

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
        Image(
            painter = painterResource(R.drawable.welcome_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // The dark gradient overlay is a design choice that works for both themes.
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black.copy(alpha = 0.3f),
                            Color.Black.copy(alpha = 0.6f)
                        ),
                        startY = 0f,
                        endY = Float.POSITIVE_INFINITY
                    )
                )
        )

        // The Skip button should be theme-aware.
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
                .align(Alignment.TopEnd)
                .padding(top = 50.dp, end = 24.dp)
        ) {
            Text("Skip", fontSize = 14.sp, fontWeight = FontWeight.Medium)
        }

        // This content sits on a light part of the background image.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 25.dp, vertical = 300.dp), // Adjusted padding
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            // This text should contrast with the light background image part
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold,
                            fontSize = 46.sp
                        )
                    ) {
                        append("Welcome to\n")
                    }
                    withStyle(
                        SpanStyle(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                        )
                    ) {
                        append("Appetito")
                    }
                },
                lineHeight = 42.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Your favourite foods delivered\nfast at your door.",
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.9f),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )
        }

        // This content sits on the dark gradient overlay.
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(horizontal = 32.dp, vertical = 30.dp),
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
                // Social login buttons are theme-aware.
                Button(
                    onClick = onFacebook,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    modifier = Modifier.weight(1f).height(56.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.facebook_icon), contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("FACEBOOK", color = Color(0xFF1877F3), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
                Button(
                    onClick = onGoogle,
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = ButtonDefaults.buttonElevation(4.dp),
                    modifier = Modifier.weight(1f).height(56.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.google_icon), contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("GOOGLE", color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
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

@Preview(showBackground = true, name = "Welcome Screen")
@Composable
fun WelcomeScreenPreview() {
    AppetitoTheme {
        WelcomeScreen({}, {}, {}, {}, {})
    }
}