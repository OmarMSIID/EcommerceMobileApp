package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.qc.cgodin.ecommnerceapp.R
import ca.qc.cgodin.ecommnerceapp.data.Product

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGrid() {
    val products = listOf(
        Product("1", "Casque stereo P47", "Audio", 140.0, R.drawable.headphones),
        Product("2", "Galaxy watch 7", "Montres", 450.0, R.drawable.watch),
        Product("3", "Samsung Galaxy S21", "Téléphones", 2549.0, R.drawable.s21),
        Product("4", "Clavier sans fil", "Accessoires", 350.0, R.drawable.keyboard)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        items(products) { product ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth(1f) // Augmenter la largeur un peu
                        .height(150.dp)
                        .clip(RoundedCornerShape(15.dp)), // Coins arrondis
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = product.name,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "MAD ${product.price}",
                    fontSize = 13.sp,
                    color = Color.Gray,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
