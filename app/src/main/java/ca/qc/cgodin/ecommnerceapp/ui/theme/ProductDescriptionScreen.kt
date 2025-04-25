package ca.qc.cgodin.ecommnerceapp.ui.theme

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.data.remote.ProductViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun ProductDescriptionScreen(
    navController: NavHostController,
    productId: Int,
    viewModel: ProductViewModel = viewModel(),
    cartViewModel: CartViewModel = viewModel()
) {
    val selectedProduct by viewModel.selectedProduct.collectAsState()
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
                    .padding(bottom = 100.dp)
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
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }

                    Spacer(modifier = Modifier.width(60.dp))

                    Text(
                        text = "Product Details",
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
                        model = product.image,
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

                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp)
                ) {
                    Text(
                        text = "Colors",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    ColorOptionsSection(
                        mainColor = product.color
                    )
                }

                Text(
                    text = "Product Description",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )

                ScrollableDescriptionBox(product.description)
            }

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
                            cartViewModel.addToCart(product)
                            Toast.makeText(
                                context,
                                "${product.title} added to cart",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .height(48.dp)
                            .width(200.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E5FAA))
                    ) {
                        Text(text = "Add to Cart", color = Color.White)
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
fun ColorOptionsSection(mainColor: String) {
    // Create color options with the main color from the API and some variants
    val colorOptions = createColorOptions(mainColor)

    Row(modifier = Modifier.padding(start = 16.dp)) {
        colorOptions.forEach { colorOption ->
            ColorOptionItem(color = colorOption.color, name = colorOption.name)
            Spacer(modifier = Modifier.width(12.dp))
        }
    }
}

@Composable
fun ColorOptionItem(color: Color, name: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(40.dp)
            .border(1.dp, Color.LightGray, CircleShape)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(color)
        ) {
            Text(
                text = name.take(1),
                color = if (isColorDark(color)) Color.White else Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

data class ColorOption(
    val color: Color,
    val name: String
)

fun createColorOptions(mainColorName: String): List<ColorOption> {
    val mainColor = when (mainColorName.lowercase()) {
        "black", "noir" -> Color.Black
        "white", "blanc" -> Color.White
        "red", "rouge" -> Color.Red
        "blue", "bleu" -> Color(0xFF1E3A8A)
        "green", "vert" -> Color.Green
        else -> Color(0xFF1E3A8A)
    }

    val additionalColors = listOf(
        ColorOption(Color(0xFF1E3A8A), "Blue"),
        ColorOption(Color.Black, "Black"),
        ColorOption(Color(0xFF6E6E6E), "Gray")
    )

    val uniqueColors = mutableListOf(ColorOption(mainColor, "Main"))
    additionalColors.forEach { colorOption ->
        if (colorOption.color != mainColor) {
            uniqueColors.add(colorOption)
        }
    }

    return uniqueColors.take(4)
}

fun isColorDark(color: Color): Boolean {
    val luminance = (0.299 * color.red + 0.587 * color.green + 0.114 * color.blue)
    return luminance < 0.5
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