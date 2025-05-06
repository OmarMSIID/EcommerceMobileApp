package ca.qc.cgodin.ecommnerceapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.qc.cgodin.ecommnerceapp.ui.theme.components.BrandRow
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.ui.theme.components.*
import androidx.lifecycle.viewmodel.compose.viewModel
import ca.qc.cgodin.ecommnerceapp.data.remote.ProductViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: ProductViewModel = viewModel()
) {
    // Get the search query from view model
    val searchQuery by viewModel.searchQuery.collectAsState()

    Scaffold(
        bottomBar = { BottomBar(navController) }
    ) { paddingValues ->
        // Utilisation d'un seul padding global pour tout l'écran
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize() // Remplir toute la taille de l'écran
                .background(Color.White) // Fond blanc
                .padding(horizontal = 16.dp) // Padding horizontal pour tout le contenu
        ) {
            // Banner with search function that updates the view model
            Banner(
                onSearchQueryChanged = { query ->
                    viewModel.setSearchQuery(query)
                }
            )

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

            // Pass the same viewModel to ProductGrid to ensure consistent state
            ProductGrid(navController, viewModel)
        }
    }
}