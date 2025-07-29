package ui

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.kalyani.appetito.R

// --- DEFINITIVE DATA MODELS ---

data class Address(val id: Int, val fullName: String, val mobileNumber: String, val street: String, val cityState: String, val fullAddress: String)
data class DemoUser(val name: String, val email: String, val phone: String, val balance: Float, val profileImageRes: Int)
data class CartItem(val id: String, val name: String, val description: String, val price: Float, var quantity: Int, val imageRes: Int)
data class Restaurant(val id: String, val name: String, val description: String, val deliveryInfo: String, val rating: Float, val imageRes: Int)
data class PopularItem(val id: String, val name: String, val restaurantName: String, val price: Float, val imageRes: Int)
data class CategoryItemData(val id: String, val name: String, val description: String, val price: Float, val rating: Float, val reviews: Int, val imageRes: Int)

// THE FIX: The 'items' property is now correctly a List<CartItem>, and imageRes is included.
data class UpcomingOrder(val id: String, val restaurant: String, val items: List<CartItem>, val status: String, val eta: Int, val imageRes: Int)

data class HistoryOrder(val id: String, val restaurant: String, val items: Int, val date: String, val price: Float, val status: String, val imageRes: Int)
data class FoodItemDetails(val id: String, val name: String, val description: String, val price: Float, val imageRes: Int, val rating: Float, val reviewCount: Int)


// --- SINGLE DATA PROVIDER OBJECT ---
object DemoDataProvider {

    val user = DemoUser("Kalyani Patil", "patil.kalyanii0209@gmail.com", "+91 9876543210", 902.29f, R.drawable.image_13)

    val savedAddresses = mutableStateListOf(
        Address(1, "Kalyani Patil", "+91 98765 43210", "DYP College Campus", "Talsande, Kolhapur", "DYP, Talsande"),
        Address(2, "John Doe", "+1 555-123-4567", "123 Main Street", "Los Angeles, CA", "Main Street"),
        Address(3, "Jane Smith", "+44 20 7946 0958", "800 Elm Avenue", "London, UK", "Elm Avenue")
    )
    var selectedAddress = mutableStateOf(savedAddresses.first())

    var cartItems = mutableStateListOf<CartItem>()

    // THE FIX: This is now a mutable list to hold the dynamic "Upcoming" orders.
    var placedUpcomingOrders = mutableStateListOf<UpcomingOrder>()

    val featuredRestaurants = listOf(
        Restaurant("mcdonalds", "McDonald's", "Burger • Chicken • Fast Food", "Free delivery • 10-15 mins", 4.9f, R.drawable.mcdonalds_img),
        Restaurant("starbucks", "Starbucks", "Coffee • Bakery • Drinks", "Free delivery • 5-10 mins", 4.7f, R.drawable.starbucks_img)
    )

    val popularItems = listOf(
        PopularItem("cheese_burger", "Cheese Burger", "Burger King", 5.50f, R.drawable.img_burger),
        PopularItem("toffee_cake", "Toffee's Cake", "Top Cake", 8.25f, R.drawable.cake_img),
        PopularItem("dosa", "Dosa", "Madras Cafe", 6.75f, R.drawable.dosa)
    )

    val categoryItems = listOf(
        CategoryItemData("chicken_hawaiian", "Chicken Hawaiian", "Chicken, Cheese and pineapple", 10.35f, 4.5f, 25, R.drawable.chicken_hawaiian),
        CategoryItemData("margherita_pizza", "Margherita Pizza", "Fresh tomatoes, mozzarella, basil", 8.99f, 4.7f, 42, R.drawable.pizza_2),
        CategoryItemData("pepperoni_special", "Pepperoni Special", "Pepperoni, cheese, Italian herbs", 12.50f, 4.6f, 38, R.drawable.pizza_3),
        CategoryItemData("veggie_supreme", "Veggie Supreme", "Bell peppers, olives, mushrooms", 11.25f, 4.4f, 29, R.drawable.pizza_4)
    )

    // THE FIX: This is the single, correct definition for historyOrders.
    val historyOrders = listOf(
        HistoryOrder("264099", "McDonald’s", 2, "2025-07-25", 22.50f, "Delivered", R.drawable.chicken_hawaiian),
        HistoryOrder("264098", "Pizza Hut", 1, "2025-07-20", 15.30f, "Delivered", R.drawable.chicken_hawaiian)
    )


    // --- UTILITY FUNCTIONS ---

    fun placeOrder(): UpcomingOrder? {
        if (cartItems.isEmpty()) return null

        val restaurantName = "Your Favorite Restaurant" // Placeholder
        // THE FIX: Correctly calculates the new ID.
        val newOrderId = (placedUpcomingOrders.size + historyOrders.size + 1000).toString()

        val newOrder = UpcomingOrder(
            id = newOrderId,
            restaurant = restaurantName,
            items = cartItems.toList(),
            status = "Food on the way",
            eta = 25,
            imageRes = cartItems.firstOrNull()?.imageRes ?: R.drawable.image_13 // Use first item's image
        )

        placedUpcomingOrders.add(newOrder)
        cartItems.clear()

        return newOrder
    }

    fun getItemById(id: String?): FoodItemDetails? {
        val allItems = popularItems.map { FoodItemDetails(it.id, it.name, "From ${it.restaurantName}", it.price, it.imageRes, 4.5f, 50) } +
                categoryItems.map { FoodItemDetails(it.id, it.name, it.description, it.price, it.imageRes, it.rating, it.reviews) } +
                featuredRestaurants.map { FoodItemDetails(it.id, it.name, it.description, 15.00f, it.imageRes, it.rating, 100) }

        return allItems.find { item -> item.id == id }
    }

    fun increaseCartItemQuantity(item: CartItem) {
        val index = cartItems.indexOf(item)
        if (index != -1) {
            cartItems[index] = item.copy(quantity = item.quantity + 1)
        }
    }

    fun decreaseCartItemQuantity(item: CartItem) {
        val index = cartItems.indexOf(item)
        if (index != -1) {
            if (item.quantity > 1) {
                cartItems[index] = item.copy(quantity = item.quantity - 1)
            } else {
                cartItems.removeAt(index)
            }
        }
    }
    // In ui/DemoDataProvider.kt, inside object DemoDataProvider

    // --- ADD THIS FUNCTION ---
    fun addToCart(item: FoodItemDetails, quantity: Int, selectedAddOns: Set<AddOn>) {
        val existingItem = cartItems.find { it.id == item.id }

        if (existingItem != null) {
            val index = cartItems.indexOf(existingItem)
            cartItems[index] = existingItem.copy(quantity = existingItem.quantity + quantity)
        } else {
            val description = selectedAddOns.joinToString(", ") { it.name }
            cartItems.add(
                CartItem(
                    id = item.id,
                    name = item.name,
                    description = if (description.isBlank()) "Standard" else description,
                    price = item.price,
                    quantity = quantity,
                    imageRes = item.imageRes
                )
            )
        }
    }
    fun removeCartItem(item: CartItem) {
        cartItems.remove(item)
    }
}