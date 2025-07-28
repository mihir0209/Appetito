package ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kalyani.appetito.R

// --- Data Models ---
data class Address(
    val id: Int,
    val street: String,
    val cityState: String,
    val fullAddress: String // A simple string for display in the list
)

data class DemoUser(
    val name: String,
    val email: String,
    val phone: String,
    val balance: Float,
    val profileImageRes: Int
)

data class CartItem(
    val name: String,
    val description: String,
    val price: Float,
    var quantity: Int,
    val imageRes: Int
)

data class UpcomingOrder(
    val id: String,
    val restaurant: String,
    val items: Int,
    val status: String,
    val eta: Int,
    val imageRes: Int
)

data class HistoryOrder(
    val id: String,
    val restaurant: String,
    val items: Int,
    val date: String,
    val price: Float,
    val status: String,
    val imageRes: Int
)

// NEW: Data model for a featured restaurant
data class Restaurant(
    val id: String,
    val name: String,
    val description: String,
    val deliveryInfo: String,
    val rating: Float,
    val imageRes: Int
)

// NEW: Data model for a popular item
data class PopularItem(
    val id: String,
    val name: String,
    val restaurantName: String,
    val price: Float,
    val imageRes: Int
)

// --- Data Provider Object ---

object DemoDataProvider {

    val user = DemoUser(
        name = "Kalyani Patil",
        email = "patil.kalyanii0209 @gmail.com",
        phone = "+91 9876543210",
        balance = 902.29f,
        profileImageRes = R.drawable.image_13
    )

    val savedAddresses = mutableStateListOf(
        Address(id = 1, street = "DYP, Talsande", cityState = "Kolhapur, MH", fullAddress = "DYP, Talsande"),
        Address(id = 2, street = "Gangapur Road", cityState = "Nashik, MH", fullAddress = "Gangapur"),
        Address(id = 3, street = "Gargoti Main Rd", cityState = "Gargoti, MH", fullAddress = "Gargoti")
    )

    var cartItems by mutableStateOf(
        listOf(
            CartItem("Red n hot pizza", "Spicy chicken, beef", 15.30f, 2, R.drawable.chicken_hawaiian),
            CartItem("Greek salad", "with baked salmon", 29.00f, 2, R.drawable.greek_salad)
        )
    )
    var selectedAddress by mutableStateOf(savedAddresses.first())
    // NEW: List of featured restaurants
    val featuredRestaurants = listOf(
        Restaurant(
            id = "mcdonalds",
            name = "McDonald's",
            description = "Burger • Chicken • Fast Food",
            deliveryInfo = "Free delivery • 10-15 mins",
            rating = 4.9f,
            imageRes = R.drawable.mcdonalds_img
        ),
        Restaurant(
            id = "starbucks",
            name = "Starbucks",
            description = "Coffee • Bakery • Drinks",
            deliveryInfo = "Free delivery • 5-10 mins",
            rating = 4.7f,
            imageRes = R.drawable.starbucks_img
        )
    )

    // NEW: List of popular food items
    val popularItems = listOf(
        PopularItem(
            id = "cheese_burger",
            name = "Cheese Burger",
            restaurantName = "Burger King",
            price = 5.50f,
            imageRes = R.drawable.img_burger
        ),
        PopularItem(
            id = "toffee_cake",
            name = "Toffee's Cake",
            restaurantName = "Top Cake",
            price = 8.25f,
            imageRes = R.drawable.cake_img
        ),
        PopularItem(
            id = "dosa",
            name = "Dosa",
            restaurantName = "Madras Cafe",
            price = 6.75f,
            imageRes = R.drawable.dosa // You'll need to add this drawable
        )
    )

    val upcomingOrders = listOf(
        UpcomingOrder("264100", "Starbuck", 3, "Food on the way", 25, R.drawable.chicken_hawaiian)
    )

    val historyOrders = listOf(
        HistoryOrder("264099", "McDonald’s", 2, "2025-07-25", 22.50f, "Delivered", R.drawable.chicken_hawaiian),
        HistoryOrder("264098", "Pizza Hut", 1, "2025-07-20", 15.30f, "Delivered", R.drawable.chicken_hawaiian)
    )
}