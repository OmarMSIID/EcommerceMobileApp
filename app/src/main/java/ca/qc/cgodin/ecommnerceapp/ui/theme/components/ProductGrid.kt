package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.data.remote.ProductUiState
import ca.qc.cgodin.ecommnerceapp.data.remote.ProductViewModel
import ca.qc.cgodin.ecommnerceapp.navigation.Screen
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGrid(
    navController: NavHostController,
    viewModel: ProductViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()

    // Log the current state for debugging
    LaunchedEffect(searchQuery, selectedCategory) {
        Log.d("ProductGrid", "Search query: $searchQuery, Category: $selectedCategory")
    }

    when (uiState) {
        is ProductUiState.Loading -> {
            Log.d("ProductGrid", "UI State: Loading")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is ProductUiState.Error -> {
            val message = (uiState as ProductUiState.Error).message
            Log.d("ProductGrid", "UI State: Error - $message")
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Erreur: $message", color = Color.Red)
                    Button(onClick = { viewModel.loadProducts() }) {
                        Text("Réessayer")
                    }
                }
            }
        }

        is ProductUiState.Success -> {
            Log.d("ProductGrid", "UI State: Success")
            // Get all products for reference
            val allProducts = (uiState as ProductUiState.Success).products
            Log.d("ProductGrid", "Total products: ${allProducts.size}")

            // Get filtered products
            val filteredProducts = viewModel.getFilteredProducts()
            Log.d("ProductGrid", "Filtered products: ${filteredProducts.size}")

            if (filteredProducts.isEmpty() && (searchQuery.isNotEmpty() || selectedCategory != null)) {
                Log.d("ProductGrid", "No products found with current filters")
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "Aucun produit trouvé",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )

                        if (searchQuery.isNotEmpty()) {
                            Text(
                                text = "Essayez d'autres termes de recherche: '$searchQuery'",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = Color.Gray
                                ),
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }

                        Button(
                            onClick = {
                                Log.d("ProductGrid", "Clearing filters")
                                viewModel.setSearchQuery("")
                                if (selectedCategory != null) {
                                    viewModel.selectCategory(selectedCategory!!)
                                }
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Effacer les filtres")
                        }
                    }
                }
            } else {
                // Display the filtered products
                Log.d("ProductGrid", "Displaying ${filteredProducts.size} products")
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(8.dp)
                ) {
                    items(filteredProducts) { product ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(4.dp)
                                .clickable {
                                    navController.navigate(Screen.ProductDescription.createRoute(product.id.toString()))
                                }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(product.image)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = product.title,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(12.dp))
                            )

                            Column(
                                modifier = Modifier.padding(top = 4.dp),
                                verticalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = product.title,
                                    style = TextStyle(
                                        fontWeight = FontWeight.SemiBold,
                                        fontSize = 14.sp,
                                        color = Color.Black
                                    ),
                                    maxLines = 1,
                                    overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                                )
                                Text(
                                    text = product.category,
                                    style = TextStyle(
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 12.sp,
                                        color = Color.Gray
                                    )
                                )
                                Text(
                                    text = "MAD ${product.price}",
                                    style = TextStyle(
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color.DarkGray
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}