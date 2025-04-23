package ca.qc.cgodin.ecommnerceapp.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object ProductDescription : Screen("product_description_screen/{productId}") {
        fun createRoute(productId: String) = "product_description_screen/$productId"
    }
}