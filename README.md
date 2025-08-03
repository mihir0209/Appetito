# Appetito - Food Delivery App UI (Jetpack Compose)

Welcome to Appetito, a modern food delivery application UI built entirely with Jetpack Compose and Kotlin. This project showcases a complete user flow, from onboarding and authentication to browsing food, placing an order, and managing user profiles. A key feature of this app is its dynamic theming system, allowing users to switch between a clean light mode and a sleek dark mode, with their preference saved across app sessions.

## Screenshots & Demo

*(You should replace these placeholder links with actual screenshots and a screen recording of your app)*

|                            Light Mode                            |                           Dark Mode                            |
|:----------------------------------------------------------------:|:--------------------------------------------------------------:|
| ![Home_Screen_Light_theme](Project_Demo/Home_Screen_Light_theme) | ![Home_Screen_Dark_theme](Project_Demo/Home_Screen_Dark_theme) |

**Complete video demo of the app's frontend:**

[Click here to watch the demo](https://drive.google.com/file/d/1Piz-pfCeEZfgs6J4CYrJdIjC0ALnMhb_/view?usp=sharing)

---

## Key Features

This project is a comprehensive demonstration of building a feature-rich UI in Jetpack Compose.

-   **Dynamic Theming:**
    -   Seamless switching between **Light and Dark themes**.
    -   Theme preference is **persisted** using Jetpack DataStore.
    -   A dedicated toggle in the Settings screen allows users to manually select Light/Dark mode or follow the system setting.

-   **User Authentication Flow:**
    -   **Welcome Screen:** Beautiful onboarding screen with social login options.
    -   **Login & Sign Up:** Clean, user-friendly forms for authentication.
    -   **Verification & Password Reset:** Complete UI for user account management.

-   **Core App Experience:**
    -   **Animated Side Menu:** A custom, animated side navigation drawer with smooth open/close transitions.
    -   **Intuitive Gestures:** Swipe from the screen edge to open the menu, and swipe on the menu or main content to close it.
    -   **Nested Navigation:** A robust navigation graph using two `NavControllers` to manage main app flow and the bottom navigation bar state independently.
    -   **Home Screen:** Features featured restaurants, popular items, and category chips.
    -   **Food Details:** Detailed view of food items with quantity controls and add-on selection.
    -   **Comprehensive Cart Management:** Add, increase, decrease, and remove items from the cart. Includes a clear empty-cart state.

-   **Order & Profile Management:**
    -   **Checkout Process:** A multi-step checkout screen summarizing the order and payment details.
    -   **Order Success Screen:** A confirmation screen with animations after placing an order.
    -   **My Orders:** A tabbed layout (`HorizontalPager`) to view "Upcoming" and "History" orders.
    -   **User Profile:** A detailed screen displaying user information and providing navigation to other sections.
    -   **Restaurant & User Reviews:** Screens for viewing and submitting ratings and reviews.
    -   **Full Suite of Settings:** Screens for managing addresses, payment methods, settings (theme toggle), and help/FAQs.

---

## 🛠Technical Implementation

-   **UI:** Built 100% in **Kotlin** with **Jetpack Compose**, using a single-activity architecture.
-   **Theming:** Implemented a custom `AppetitoTheme` using `MaterialTheme` and `Material 3` components. Colors are defined in `ui/Theme.kt` and dynamically applied.
-   **State Management:** Utilizes `ViewModel` (`ThemeViewModel`) and `mutableStateOf` to manage UI state effectively. The user's theme choice is persisted using **Jetpack DataStore**.
-   **Navigation:** Employs **Jetpack Navigation for Compose** with a nested graph structure for a clean and scalable navigation flow.
-   **Animations & Gestures:** Leverages Compose's animation APIs (`animateFloatAsState`, `AnimatedVisibility`) and the `pointerInput` modifier for custom gesture detection (side menu swipe).
-   **Architecture:** Follows modern Android app architecture principles, with a clear separation between UI (`@Composable` functions) and data (`DemoDataProvider`).

---

## How to Run

To get this project running on your local machine, follow these simple steps:

1.  **Clone the repository:**
    ```bash
    git clone https://github.com/mihir0209/appetito.git
    ```
2.  **Open in Android Studio:**
    -   Open Android Studio (latest stable version recommended).
    -   Click on `File` > `Open` and navigate to the cloned repository folder.
3.  **Build & Run:**
    -   Let Android Studio sync the Gradle files.
    -   Select an emulator or a physical device.
    -   Click the `Run` button.

---

## Project Structure

The codebase is organized logically within the `ui` package, making it easy to navigate and understand.

```
.
└── ui/
    ├── AddNewAddressScreen.kt      # Manage user addresses
    ├── AppetitoBottomNavBar.kt     # Themed bottom navigation bar
    ├── CartScreen.kt               # Shopping cart
    ├── CategoryScreen.kt           # Food category/menu view
    ├── CheckoutScreen.kt           # Order checkout process
    ├── ContactUsScreen.kt          # Contact information
    ├── DemoDataProvider.kt         # Central source for all sample data, can be used for integrating database
    ├── FavoritesFoodItemsScreen.kt # User's favorite items
    ├── FoodDetailsScreen.kt        # Detailed view of a food item
    ├── HelpsFaqsScreen.kt          # Help and FAQ section
    ├── HomeScreen.kt               # Main screen with animated side menu
    ├── LoginScreen.kt              # User login
    ├── MainActivity.kt             # Main entry point, sets up the theme
    ├── MainWithBottomNav.kt        # Hosts the bottom nav and its screens
    ├── MyOrdersUpcomingScreen.kt   # Upcoming and past orders
    ├── OrderSuccessScreen.kt       # Confirmation after placing an order
    ├── PaymentMethodsScreen.kt     # Manage payment cards
    ├── ProfileScreen.kt            # User profile view
    ├── RatingScreen.kt             # Rate an order/restaurant
    ├── RegistrationScreen.kt       # New user registration
    ├── ResetPasswordScreen.kt      # Password reset flow
    ├── ReviewRestaurantScreen.kt   # Write a review
    ├── ReviewsScreen.kt            # View all reviews
    ├── SettingsScreen.kt           # App settings, including theme toggle
    ├── SignUpScreen.kt             # New user sign-up
    ├── SplashScreen.kt             # Initial splash screen
    ├── Theme.kt                    # Defines light/dark color schemes
    ├── ThemeViewModel.kt           # ViewModel to manage and persist theme state
    ├── VerificationCodeScreen.kt   # OTP verification
    └── WelcomeScreen.kt            # Onboarding screen
```

