package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
        modifier = Modifier.fillMaxHeight()
    ) {
        items(products) { product ->
            Card(
                modifier = Modifier.height(200.dp),
                shape = RoundedCornerShape(15.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = product.imageRes),
                        contentDescription = product.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = product.name, fontWeight = FontWeight.SemiBold, fontSize = 14.sp)
                    Text(text = "MAD ${product.price}", color = Color.Gray, fontSize = 13.sp)
                }
            }
        }
    }
}