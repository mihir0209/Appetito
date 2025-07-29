package ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(nestedNavController: NavHostController, mainNavController: NavHostController) {

    val categories = DemoDataProvider.categoryItems
    var isContentVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isContentVisible = true }

    // CHANGE: Use theme's background color.
    Box(modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Image(
            painter = painterResource(id = R.drawable.pizza),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .size(500.dp)
                .offset(x = 100.dp, y = (-150).dp)
        )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {},
                    navigationIcon = {
                        IconButton(onClick = {
                            nestedNavController.navigate(BottomNavTab.Home.route) {
                                popUpTo(nestedNavController.graph.startDestinationId) { inclusive = true }
                                launchSingleTop = true
                            }
                        }) {
                            // CHANGE: Use theme's onSurface color for icon tint.
                            Icon(
                                painter = painterResource(id = R.drawable.ic_back),
                                contentDescription = "Back",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    },
                    // Transparent background is intentional for this design.
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                )
            },
            // Transparent background is intentional for this design.
            containerColor = Color.Transparent
        ) { innerPadding ->
            LazyColumn(modifier = Modifier.padding(innerPadding)) {
                item {
                    Column(modifier = Modifier.padding(start = 26.dp, end = 26.dp, top = 20.dp)) {
                        // CHANGE: Use theme colors for text.
                        Text("Fast", fontSize = 45.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onBackground)
                        Text("Food", fontSize = 56.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("80 types of pizza", fontSize = 19.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text("Sort by:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onBackground)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Popular", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Medium)
                                Icon(painter = painterResource(id = R.drawable.ic_dropdown), contentDescription = null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(16.dp).padding(start = 4.dp))
                            }
                            // CHANGE: Use theme colors for page indicators.
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Box(modifier = Modifier.size(8.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
                                Box(modifier = Modifier.size(8.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape))
                                Box(modifier = Modifier.size(8.dp).background(MaterialTheme.colorScheme.surfaceVariant, CircleShape))
                            }
                        }
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                itemsIndexed(categories, key = { _, item -> item.id }) { index, item ->
                    AnimatedVisibility(
                        visible = isContentVisible,
                        enter = fadeIn(animationSpec = tween(500, delayMillis = index * 100)) +
                                slideInVertically(initialOffsetY = { 60 }, animationSpec = tween(500, delayMillis = index * 100))
                    ) {
                        CategoryCard(
                            item = item,
                            onClick = {
                                mainNavController.navigate("food_details/${item.id}")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}
@Composable
fun CategoryCard(item: CategoryItemData, onClick: () -> Unit) {
    var isFavorite by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .padding(horizontal = 26.dp)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        // CHANGE: Use theme surface color.
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column {
            Box {
                Image(
                    painter = painterResource(id = item.imageRes),
                    contentDescription = item.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                        .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                )
                Box(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        // CHANGE: Use theme surface color.
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(20.dp))
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("$${String.format("%.2f", item.price)}", fontSize = 16.sp, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                }
                IconButton(
                    onClick = { isFavorite = !isFavorite },
                    // CHANGE: Use theme colors for the favorite button.
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .size(40.dp),
                    colors = IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Icon(painter = painterResource(id = if (isFavorite) R.drawable.ic_heart_filled else R.drawable.ic_favorite), contentDescription = "Favorite", modifier = Modifier.size(20.dp))
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        // CHANGE: Use theme surface color.
                        .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    // CHANGE: Use theme colors for rating text.
                    Text(item.rating.toString(), fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurface, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = "Rating", tint = Color(0xFFFFC529), modifier = Modifier.size(16.dp)) // Star color is often brand-specific, keeping it yellow.
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("(${item.reviews}+)", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
            Column(modifier = Modifier.padding(16.dp)) {
                // CHANGE: Use theme colors for title and description.
                Text(text = item.name, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = item.description, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant, lineHeight = 20.sp)
            }
        }
    }
}


@Preview(showBackground = true, name = "Category Screen - Light")
@Composable
fun CategoryScreenPreviewLight() {
    AppetitoTheme(useDarkTheme = false) {
        CategoryScreen(nestedNavController = rememberNavController(), mainNavController = rememberNavController())
    }
}

@Preview(showBackground = true, name = "Category Screen - Dark")
@Composable
fun CategoryScreenPreviewDark() {
    AppetitoTheme(useDarkTheme = true) {
        CategoryScreen(nestedNavController = rememberNavController(), mainNavController = rememberNavController())
    }
}