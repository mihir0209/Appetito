package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
    var showName by remember { mutableStateOf(false) }
    var showDetails by remember { mutableStateOf(false) }
    var showBalance by remember { mutableStateOf(false) }
    var showEmail by remember { mutableStateOf(false) }
    var showPhone by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(200); showName = true
        delay(120); showDetails = true
        delay(120); showBalance = true
        delay(120); showEmail = true
        delay(120); showPhone = true
    }
    val user = DemoDataProvider.user

    Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
        val elevation by animateDpAsState(targetValue = 16.dp, animationSpec = tween(600))
        val scale by animateFloatAsState(targetValue = 1.0f, animationSpec = tween(600))
        AnimatedVisibility(visible = true, enter = fadeIn(animationSpec = tween(600)), exit = fadeOut(animationSpec = tween(200)), modifier = Modifier.align(Alignment.TopCenter).padding(top = 120.dp)) {
            Box(modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale }.shadow(elevation, CircleShape)) {
                Box(modifier = Modifier.size(108.dp).clip(CircleShape).background(Color.White).border(BorderStroke(2.dp, Color(0xFFFE724C)), CircleShape))
                Image(painter = painterResource(id = R.drawable.image_13), contentDescription = "Profile Picture", modifier = Modifier.size(90.dp).align(Alignment.Center).clip(CircleShape), contentScale = ContentScale.Crop)
                Box(modifier = Modifier.size(17.dp).align(Alignment.TopEnd).offset(x = 10.dp, y = 8.dp).background(Color.White, CircleShape)) {
                    Box(modifier = Modifier.size(11.dp).align(Alignment.Center).background(Color(0xFFFE724C), CircleShape))
                }
            }
        }
        AnimatedVisibility(visible = showName, enter = fadeIn(animationSpec = tween(400)), exit = fadeOut(animationSpec = tween(200)), modifier = Modifier.align(Alignment.TopCenter).padding(top = 235.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = user.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color.Black)
                Text(text = "Edit Profile", fontSize = 14.sp, color = Color(0xFF9796A1), modifier = Modifier.clickable { /* mainNavController.navigate("edit_profile") */ })
            }
        }
        Column(modifier = Modifier.fillMaxWidth().align(Alignment.TopCenter).padding(top = 320.dp, start = 24.dp, end = 24.dp), horizontalAlignment = Alignment.Start) {
            AnimatedVisibility(visible = showDetails, enter = fadeIn(animationSpec = tween(400)), exit = fadeOut(animationSpec = tween(200))) {
                Column {
                    Text(text = "Full name", fontSize = 16.sp, color = Color(0xFF9796A1))
                    Box(modifier = Modifier.fillMaxWidth().height(48.dp).background(Color.White, RoundedCornerShape(10.dp)).border(1.dp, Color(0xFFFE724C), RoundedCornerShape(10.dp))) {
                        Text(text = user.name, fontSize = 20.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF111719), modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            AnimatedVisibility(visible = showBalance, enter = fadeIn(animationSpec = tween(400)), exit = fadeOut(animationSpec = tween(200))) { Text(text = "$ ${"%.2f".format(user.balance)}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black) }
            Spacer(modifier = Modifier.height(28.dp))
            AnimatedVisibility(visible = showEmail, enter = fadeIn(animationSpec = tween(400)), exit = fadeOut(animationSpec = tween(200))) {
                Column {
                    Text(text = "E-mail", fontSize = 16.sp, color = Color(0xFF9796A1))
                    Box(modifier = Modifier.fillMaxWidth().height(48.dp).background(Color.White, RoundedCornerShape(10.dp)).border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))) {
                        Text(text = user.email, fontSize = 17.sp, fontWeight = FontWeight.Medium, color = Color(0xFF111719), modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(visible = showPhone, enter = fadeIn(animationSpec = tween(400)), exit = fadeOut(animationSpec = tween(200))) {
                Column {
                    Text(text = "Phone Number", fontSize = 16.sp, color = Color(0xFF9796A1))
                    Box(modifier = Modifier.fillMaxWidth().height(48.dp).background(Color.White, RoundedCornerShape(10.dp)).border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(10.dp))) {
                        Text(text = user.phone, fontSize = 17.sp, fontWeight = FontWeight.Medium, color = Color(0xFF111719), modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(mainNavController = rememberNavController())
}