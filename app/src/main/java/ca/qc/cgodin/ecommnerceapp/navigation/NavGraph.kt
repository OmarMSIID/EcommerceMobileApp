package ca.qc.cgodin.ecommnerceapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ca.qc.cgodin.ecommnerceapp.data.ProductRepository
import ca.qc.cgodin.ecommnerceapp.ui.theme.HomeScreen
import ca.qc.cgodin.ecommnerceapp.ui.theme.ProductDescriptionScreen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }

        composable(
            route = Screen.ProductDescription.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            val product = ProductRepository.getProductById(productId)
            if (product != null) {
                ProductDescriptionScreen(navController, product)
            }
        }
    }
}