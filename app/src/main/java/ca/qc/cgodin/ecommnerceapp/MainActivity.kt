package ca.qc.cgodin.ecommnerceapp

import android.os.Bundle
import android.util.Log
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ca.qc.cgodin.ecommnerceapp.AppUtil.AppUtil
import ca.qc.cgodin.ecommnerceapp.data.ProductRepository
import ca.qc.cgodin.ecommnerceapp.data.remote.ProductViewModel
import ca.qc.cgodin.ecommnerceapp.navigation.Screen
import ca.qc.cgodin.ecommnerceapp.ui.theme.*
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TechnoShopTheme {
                AppNavigator()
            }
        }
    }
}

@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val isLogged = Firebase.auth.currentUser != null
    val firstPage = if (isLogged) Screen.Home.route else "splash_screen"

    NavHost(
        navController = navController,
        startDestination = firstPage
    ) {
        composable("splash_screen") {
            //SplashScreen(navController)
            //ElectroTechStoreApp(onCartClick = {}, onAddProductClick = {})
            ProductTitleScreen()
        }
        composable("welcome_screen") {
            WelcomeScreen(navController)
        }
        composable("login_screen") {
            LoginScreen(navController)
        }
        composable("signup_screen") {
            SignupScreen(navController)
        }
        composable(Screen.Home.route) {
            //HomeScreen(navController)
            ProductTitleScreen()
            //ElectroTechStoreApp(onAddProductClick = {}, onCartClick = {})

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

@Composable
fun SplashScreen(navController: NavHostController) {
    val blueColor = Color(0xFF1E5FAA)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Cercle en haut à droite
        Box(
            modifier = Modifier
                .size(200.dp)
                .offset(x = 100.dp, y = (-100).dp)
                .clip(CircleShape)
                .background(blueColor)
                .align(Alignment.TopEnd)
        )

        // Cercle en bas à gauche
        Box(
            modifier = Modifier
                .size(300.dp)
                .offset(x = (-150).dp, y = 150.dp)
                .clip(CircleShape)
                .background(blueColor)
                .align(Alignment.BottomStart)
        )

        // Logo au centre
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "TechnoShop Logo",
            modifier = Modifier
                .size(375.dp)
                .align(Alignment.Center)
        )
    }

    LaunchedEffect(Unit) {
        delay(1000)
        navController.navigate("welcome_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    TechnoShopTheme {
        SplashScreen(navController = rememberNavController())
    }
}

@Composable
fun ProductTitleScreen(productViewModel: ProductViewModel=viewModel()){
    var listofProducts = productViewModel.getFilteredProducts()
    Spacer(modifier = Modifier.size(15.dp))
    Text("list of product")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(top = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

    ) {
        items(listofProducts) { product ->
            Text(
                text = product.title,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

        }
    }

}

