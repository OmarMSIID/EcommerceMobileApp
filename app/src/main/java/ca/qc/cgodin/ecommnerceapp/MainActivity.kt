package ca.qc.cgodin.ecommnerceapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ca.qc.cgodin.ecommnerceapp.ui.theme.LoginScreen
import ca.qc.cgodin.ecommnerceapp.ui.theme.SignupScreen
import kotlinx.coroutines.delay
import ca.qc.cgodin.ecommnerceapp.ui.theme.TechnoShopTheme
import ca.qc.cgodin.ecommnerceapp.ui.theme.WelcomeScreen

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

    NavHost(navController = navController, startDestination = "splash_screen") {
        composable("splash_screen") {
            SplashScreen(navController)
        }
        composable("welcome_screen") {
            WelcomeScreen(navController)
        }
        composable("login_screen"){
            LoginScreen(navController)
        }
        composable("signup_screen") {
            SignupScreen(navController)
        }
    }
}



@Composable
fun SplashScreen(navController: NavHostController) {
    val blueColor = Color(0xFF1E5FAA)

    // Splash screen content
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
        TechnoShopLogo(modifier = Modifier.align(Alignment.Center))
    }

    // Delay for 1 second, then navigate to the Welcome Screen
    LaunchedEffect(Unit) {
        delay(1000) // 1 second delay
        navController.navigate("welcome_screen") {
            popUpTo("splash_screen") { inclusive = true }
        }
    }
}

@Composable
fun TechnoShopLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.logo), // Assurez-vous d'ajouter "logo.png" dans res/drawable
        contentDescription = "TechnoShop Logo",
        modifier = modifier.size(375.dp) // Ajustez la taille de l'image si nécessaire
    )
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    TechnoShopTheme {
        SplashScreen(navController = rememberNavController())
    }
}