package ca.qc.cgodin.ecommnerceapp.ui.theme

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import ca.qc.cgodin.ecommnerceapp.data.remote.ProductViewModel

@Composable
fun ProductDescriptionScreen(
    navController: NavHostController,
    productId: Int,
    viewModel: ProductViewModel = viewModel()
) {
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    var selectedColorIndex by remember { mutableStateOf(0) }

    LaunchedEffect(productId) {
        viewModel.selectProductById(productId)
    }

    selectedProduct?.let { product ->
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 100.dp) // Leave space for bottom bar
            ) {
                Spacer(modifier = Modifier.height(25.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Retour"
                        )
                    }

                    Spacer(modifier = Modifier.width(60.dp))

                    Text(
                        text = "Détails du produit",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )
                }

                Text(
                    text = product.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp)
                )

                Text(
                    text = product.category,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp, top = 2.dp)
                )

                Text(
                    text = "Couleurs",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                // Utilisation de la couleur de l'API et ajout de variantes
                ColorOptionsSection(
                    mainColor = product.color,
                    selectedIndex = selectedColorIndex,
                    onColorSelected = { selectedColorIndex = it }
                )

                Text(
                    text = "Description du produit",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                ScrollableDescriptionBox(product.description)

                Spacer(modifier = Modifier.height(12.dp))
            }

            // Bottom bar fixed
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Total", fontSize = 14.sp, color = Color.Gray)
                        Text(
                            text = "MAD ${product.price}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1E3A8A)
                        )
                    }

                    Button(
                        onClick = { /* TODO: Ajouter au panier */ },
                        modifier = Modifier
                            .height(48.dp)
                            .width(200.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E3A8A))
                    ) {
                        Text(text = "Ajouter au panier", color = Color.White)
                    }
                }
            }
        }
    } ?: Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ColorOptionsSection(
    mainColor: String,
    selectedIndex: Int,
    onColorSelected: (Int) -> Unit
) {
    // Convertir la couleur principale de l'API
    val mainColorObject = parseColor(mainColor)

    // Générer quelques couleurs supplémentaires basées sur la catégorie
    val colorOptions = listOf(
        mainColorObject,                 // Couleur principale de l'API
        Color(0xFF1E3A8A),               // Bleu foncé
        Color.Black,                     // Noir
        Color(0xFF6E6E6E)                // Gris
    )

    Row(modifier = Modifier.padding(start = 16.dp)) {
        colorOptions.forEachIndexed { index, color ->
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(color)
                    .then(
                        if (index == selectedIndex) {
                            Modifier
                                .border(
                                    width = 2.dp,
                                    color = Color(0xFF1E3A8A),
                                    shape = CircleShape
                                )
                                .shadow(4.dp, CircleShape)
                        } else {
                            Modifier.clickable { onColorSelected(index) }
                        }
                    )
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

// Fonction pour convertir un nom de couleur en objet Color
fun parseColor(colorName: String): Color {
    return when (colorName.lowercase()) {
        "black", "noir" -> Color.Black
        "white", "blanc" -> Color.White
        "red", "rouge" -> Color.Red
        "blue", "bleu" -> Color(0xFF1E3A8A)
        "green", "vert" -> Color.Green
        "yellow", "jaune" -> Color.Yellow
        "gray", "grey", "gris" -> Color.Gray
        "purple", "violet" -> Color(0xFF800080)
        "pink", "rose" -> Color(0xFFFF69B4)
        "orange" -> Color(0xFFFF8C00)
        "brown", "marron" -> Color(0xFF8B4513)
        "silver", "argent" -> Color(0xFFC0C0C0)
        "gold", "or" -> Color(0xFFFFD700)
        else -> Color(0xFF1E3A8A) // Couleur par défaut si la couleur n'est pas reconnue
    }
}

@Composable
fun ScrollableDescriptionBox(description: String) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .height(120.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(end = 12.dp)
        ) {
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(8.dp)
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(20.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color(0xFFF5F5F5))
                    )
                )
        )
    }
}