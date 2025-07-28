package ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.kalyani.appetito.R

private data class FoodItemDetails(
    val id: String,
    val name: String,
    val description: String,
    val price: Float,
    val imageRes: Int,
    val rating: Float,
    val reviewCount: Int
)

private fun getFoodDetailsById(itemId: String?): FoodItemDetails {
    return when (itemId) {
        "cheese_burger" -> FoodItemDetails("cheese_burger", "Cheese Burger", "A classic cheese burger with a juicy beef patty, fresh lettuce, tomatoes, and our secret sauce.", 9.50f, R.drawable.img_burger, 4.8f, 102)
        "toffee_cake" -> FoodItemDetails("toffee_cake", "Toffee's Cake", "A rich and moist toffee cake, topped with a delicious caramel glaze and nuts.", 8.25f, R.drawable.cake_img, 4.9f, 78)
        "mcdonalds" -> FoodItemDetails("mcdonalds", "Big Mac Combo", "The world-famous Big Mac combo with a large fries and a drink of your choice.", 12.99f, R.drawable.mcdonalds_img, 4.5f, 500)
        "starbucks" -> FoodItemDetails("starbucks", "Caramel Macchiato", "Rich, full-bodied espresso with vanilla-flavored syrup, steamed milk, and a caramel drizzle.", 5.75f, R.drawable.starbucks_img, 4.7f, 830)
        else -> FoodItemDetails("default", "Ground Beef Tacos", "Brown the beef better. Lean ground beef – I like to use 85% lean angus. Garlic – use fresh chopped. Spices – chili powder, cumin, onion powder.", 9.50f, R.drawable.chicken_hawaiian, 4.5f, 35)
    }
}

data class AddOn(val name: String, val price: Float)

@Composable
fun FoodDetailsScreen(
    itemId: String?,
    navController: NavHostController
) {
    val foodItem = remember(itemId) { getFoodDetailsById(itemId) }
    var quantity by remember { mutableStateOf(1) }
    val addOns = listOf(
        AddOn("Pepper Julienned", 2.30f),
        AddOn("Baby Spinach", 4.70f),
        AddOn("Masroom", 2.50f)
    )

    Column(modifier = Modifier.fillMaxSize().background(Color.White)) {
        Box(modifier = Modifier.height(270.dp).fillMaxWidth()) {
            Image(painter = painterResource(id = foodItem.imageRes), contentDescription = foodItem.name, modifier = Modifier.fillMaxSize())
            IconButton(onClick = { navController.popBackStack() }, modifier = Modifier.size(48.dp).align(Alignment.TopStart).padding(8.dp).background(Color.White, shape = CircleShape)) {
                Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = "Back", tint = Color(0xFF111719))
            }
            IconButton(onClick = { /* TODO */ }, modifier = Modifier.size(48.dp).align(Alignment.TopEnd).padding(8.dp).background(Color(0xFFFE724C), shape = CircleShape)) {
                Icon(painter = painterResource(id = R.drawable.ic_favorite), contentDescription = "Favorite", tint = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = foodItem.name, fontSize = 28.sp, fontWeight = FontWeight.SemiBold, color = Color(0xFF323643), modifier = Modifier.padding(horizontal = 22.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 22.dp, vertical = 8.dp)) {
            Icon(painter = painterResource(id = R.drawable.ic_star), contentDescription = null, tint = Color(0xFFFFC529), modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text("${foodItem.rating}", fontWeight = FontWeight.SemiBold, color = Color(0xFF111719), fontSize = 14.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text("(${foodItem.reviewCount}+)", color = Color(0xFF9796A1), fontSize = 14.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "See Review", color = Color(0xFFFE724C), fontSize = 13.sp, modifier = Modifier.clickable { /* TODO */ }, textDecoration = TextDecoration.Underline)
        }
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(horizontal = 22.dp)) {
            Text(text = "$${String.format("%.2f", foodItem.price)}", color = Color(0xFFFE724C), fontSize = 31.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(1f))
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedButton(onClick = { if (quantity > 1) quantity-- }, shape = CircleShape, border = BorderStroke(1.dp, Color(0xFFFE724C)), contentPadding = PaddingValues(0.dp), modifier = Modifier.size(32.dp)) { Icon(painter = painterResource(id = R.drawable.ic_minus), contentDescription = "Decrease", tint = Color(0xFFFE724C)) }
                Text(text = quantity.toString().padStart(2, '0'), fontSize = 16.sp, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(horizontal = 8.dp))
                Button(onClick = { quantity++ }, shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)), contentPadding = PaddingValues(0.dp), modifier = Modifier.size(32.dp)) { Icon(painter = painterResource(id = R.drawable.ic_plus), contentDescription = "Increase", tint = Color.White) }
            }
        }
        Text(text = foodItem.description, color = Color(0xFF858992), fontSize = 15.sp, lineHeight = 22.sp, modifier = Modifier.padding(horizontal = 22.dp, vertical = 8.dp))
        Text(text = "Choice of Add On", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = Color(0xFF323643), modifier = Modifier.padding(start = 22.dp, top = 8.dp, bottom = 4.dp))
        Column(modifier = Modifier.padding(horizontal = 22.dp)) { addOns.forEach { addOn -> AddOnRow(addOn) } }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* TODO */ }, shape = RoundedCornerShape(26.5.dp), colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFE724C)), modifier = Modifier.align(Alignment.CenterHorizontally).width(167.dp).height(53.dp)) { Text("Add to cart", color = Color.White, fontSize = 15.sp) }
    }
}

@Composable
fun AddOnRow(addOn: AddOn) {
    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Image(painter = painterResource(id = R.drawable.profile_photo), contentDescription = null, modifier = Modifier.size(48.dp).clip(CircleShape).background(Color(0xFFEFEFEF)))
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = addOn.name, fontSize = 14.sp, color = Color.Black, modifier = Modifier.weight(1f))
        Text(text = "+$${String.format("%.2f", addOn.price)}", fontSize = 14.sp, color = Color.Black, modifier = Modifier.padding(end = 8.dp))
        OutlinedButton(onClick = { /* TODO */ }, shape = CircleShape, border = BorderStroke(1.dp, Color(0xFFFE724C)), contentPadding = PaddingValues(0.dp), modifier = Modifier.size(28.dp)) { Icon(painter = painterResource(id = R.drawable.ic_addon), contentDescription = "Select AddOn", tint = Color(0xFFFE724C)) }
    }
}

@Preview(showBackground = true)
@Composable
fun FoodDetailsScreenPreview() {
    FoodDetailsScreen(itemId = "cheese_burger", navController = rememberNavController())
}