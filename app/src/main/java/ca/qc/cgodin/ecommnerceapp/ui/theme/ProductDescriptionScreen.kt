package ca.qc.cgodin.ecommnerceapp.ui.theme

import android.widget.Toast
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
import androidx.compose.ui.text.style.TextAlign
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
    viewModel: ProductViewModel = viewModel(),
    // Add CartViewModel as a parameter
    cartViewModel: CartViewModel = viewModel()
) {
    val selectedProduct by viewModel.selectedProduct.collectAsState()
    var selectedColorIndex by remember { mutableStateOf(0) }
    // Get context outside of the onClick lambda
    val context = LocalContext.current

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
                        onClick = {
                            // Utilisez directement le cartViewModel passé en paramètre
                            product.let {
                                cartViewModel.addToCart(it)

                                // Utilisez le contexte obtenu plus tôt
                                Toast.makeText(
                                    context,
                                    "${it.title} ajouté au panier",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                            // Optionally navigate to cart screen
                            // navController.navigate(Screen.Cart.route)
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .width(200.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E5FAA))
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

data class ColorOption(
    val color: Color,
    val name: String,
    val initial: String
)

@Composable
fun ColorOptionsSection(
    mainColor: String,
    selectedIndex: Int,
    onColorSelected: (Int) -> Unit
) {
    // Créer des options de couleur avec la couleur principale de l'API et d'autres variantes
    val colorOptions = createColorOptions(mainColor)

    Row(modifier = Modifier.padding(start = 16.dp)) {
        colorOptions.forEachIndexed { index, colorOption ->
            // Box externe pour gérer la bordure de sélection
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .padding(2.dp)
                    .then(
                        if (index == selectedIndex) {
                            Modifier.border(
                                width = 2.dp,
                                color = Color(0xFF1E3A8A),
                                shape = CircleShape
                            )
                        } else {
                            Modifier
                        }
                    )
            ) {
                // Box interne pour la couleur (toujours présente)
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(colorOption.color)
                        .border(
                            width = 1.dp,
                            color = Color.LightGray,
                            shape = CircleShape
                        )
                        .clickable { onColorSelected(index) }
                ) {
                    // Afficher l'initiale de la couleur pour une meilleure accessibilité
                    // Utiliser une couleur de texte contrastante
                    val textColor = if (isColorDark(colorOption.color)) Color.White else Color.Black
                    Text(
                        text = colorOption.initial,
                        color = textColor,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

// Fonction pour créer les options de couleur à partir de la couleur principale
fun createColorOptions(mainColorName: String): List<ColorOption> {
    val mainColorInfo = getColorInfo(mainColorName)
    val additionalColors = listOf(
        ColorOption(Color(0xFF1E3A8A), "Bleu", "B"),
        ColorOption(Color.Black, "Noir", "N"),
        ColorOption(Color(0xFF6E6E6E), "Gris", "G")
    )

    // Rendre unique en évitant la duplication de la couleur principale
    val uniqueColors = mutableListOf(mainColorInfo)
    additionalColors.forEach { colorOption ->
        if (colorOption.color != mainColorInfo.color) {
            uniqueColors.add(colorOption)
        }
    }

    return uniqueColors.take(4) // Limiter à 4 options maximum
}

// Fonction pour déterminer si une couleur est sombre
fun isColorDark(color: Color): Boolean {
    val luminance = (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue)
    return luminance < 0.5
}

// Fonction pour obtenir l'info d'une couleur à partir de son nom
fun getColorInfo(colorName: String): ColorOption {
    return when (colorName.lowercase()) {
        "black", "noir" -> ColorOption(Color.Black, "Noir", "N")
        "white", "blanc" -> ColorOption(Color.White, "Blanc", "W")
        "red", "rouge" -> ColorOption(Color.Red, "Rouge", "R")
        "blue", "bleu" -> ColorOption(Color(0xFF1E3A8A), "Bleu", "B")
        "green", "vert" -> ColorOption(Color.Green, "Vert", "V")
        "yellow", "jaune" -> ColorOption(Color.Yellow, "Jaune", "J")
        "gray", "grey", "gris" -> ColorOption(Color.Gray, "Gris", "G")
        "purple", "violet" -> ColorOption(Color(0xFF800080), "Violet", "V")
        "pink", "rose" -> ColorOption(Color(0xFFFF69B4), "Rose", "R")
        "orange" -> ColorOption(Color(0xFFFF8C00), "Orange", "O")
        "brown", "marron" -> ColorOption(Color(0xFF8B4513), "Marron", "M")
        "silver", "argent" -> ColorOption(Color(0xFFC0C0C0), "Argent", "A")
        "gold", "or" -> ColorOption(Color(0xFFFFD700), "Or", "O")
        else -> ColorOption(Color(0xFF1E3A8A), "Bleu", "B") // Couleur par défaut
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