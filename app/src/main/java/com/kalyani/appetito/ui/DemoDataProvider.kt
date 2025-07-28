package ui
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.kalyani.appetito.R

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
    val quantity: Int,
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

object DemoDataProvider {
    val user = DemoUser(
        name = "Eljad Eendaz",
        email = "prelookstudio@gmail.com",
        phone = "+1 (783) 0986 8786",
        balance = 1679.30f,
        profileImageRes = R.drawable.ic_profile
    )

    var cartItems by mutableStateOf(
        listOf(
            CartItem(
                name = "Red n hot pizza",
                description = "Spicy chicken, beef",
                price = 15.30f,
                quantity = 2,
                imageRes = R.drawable.chicken_hawaiian
            ),
            CartItem(
                name = "Greek salad",
                description = "with baked salmon",
                price = 12.00f,
                quantity = 2,
                imageRes = R.drawable.greek_salad
            )
        )
    )

    val upcomingOrders = listOf(
        UpcomingOrder(
            id = "264100",
            restaurant = "Starbuck",
            items = 3,
            status = "Food on the way",
            eta = 25,
            imageRes = R.drawable.chicken_hawaiian
        )
    )

    val historyOrders = listOf(
        HistoryOrder(
            id = "264099",
            restaurant = "McDonald’s",
            items = 2,
            date = "2025-07-25",
            price = 22.50f,
            status = "Delivered",
            imageRes = R.drawable.chicken_hawaiian
        ),
        HistoryOrder(
            id = "264098",
            restaurant = "Pizza Hut",
            items = 1,
            date = "2025-07-20",
            price = 15.30f,
            status = "Delivered",
            imageRes = R.drawable.chicken_hawaiian
        )
    )
}
