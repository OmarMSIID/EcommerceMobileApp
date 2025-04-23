package ca.qc.cgodin.ecommnerceapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.qc.cgodin.ecommnerceapp.ui.theme.components.BrandRow
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.ui.theme.components.*

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar() }
    ) { paddingValues ->
        // Utilisation d'un seul padding global pour tout l'écran
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize() // Remplir toute la taille de l'écran
                .background(Color.White) // Fond blanc
                .padding(horizontal = 16.dp) // Padding horizontal pour tout le contenu
        ) {
            // Banner
            Banner()

            // Espacement entre la bannière et les marques
            Spacer(modifier = Modifier.height(16.dp))

            // Titre "Marques"
            Text(
                text = "Marques",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp) // Espacement sous le titre
            )

            // Affichage des marques
            BrandRow()

            // Espacement entre la section des marques et les produits
            Spacer(modifier = Modifier.height(16.dp))

            // Titre "Produits"
            Text(
                text = "Produits",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp) // Espacement sous le titre
            )

            // Affichage des produits - Passage du NavController
            ProductGrid(navController)
        }
    }
}