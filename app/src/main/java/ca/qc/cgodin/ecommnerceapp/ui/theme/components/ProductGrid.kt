package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.data.ProductRepository
import ca.qc.cgodin.ecommnerceapp.navigation.Screen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductGrid(navController: NavHostController) {
    val products = ProductRepository.products

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxHeight()
            .padding(8.dp)
    ) {
        items(products) { product ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable {
                        ProductRepository.selectProduct(product.id)
                        navController.navigate(Screen.ProductDescription.createRoute(product.id))
                    }
            ) {
                Image(
                    painter = painterResource(id = product.imageRes),
                    contentDescription = product.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier.padding(top = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.2.dp)
                ) {
                    Text(
                        text = product.name,
                        style = TextStyle(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp,
                            color = Color.Black,
                            lineHeight = 16.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = product.category,
                        style = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            color = Color.Gray,
                            lineHeight = 14.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "MAD ${product.price}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray,
                            lineHeight = 14.sp
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                }
            }
        }
    }
}