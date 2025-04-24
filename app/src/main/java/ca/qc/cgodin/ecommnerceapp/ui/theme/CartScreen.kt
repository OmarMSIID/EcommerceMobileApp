package ca.qc.cgodin.ecommnerceapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.R
import ca.qc.cgodin.ecommnerceapp.database.ProduitPanier
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch

@Composable
fun CartScreen(
    navController: NavHostController,
    viewModel: CartViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF1E5FAA))
                .padding(vertical = 16.dp)
        ) {
            Text(
                text = "panier d'achat",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Select All Option
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = viewModel.areAllItemsSelected.value,
                onCheckedChange = { isChecked ->
                    viewModel.selectAllItems(isChecked)
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFF1E5FAA),
                    uncheckedColor = Color.Gray
                ),
                modifier = Modifier.size(24.dp)
            )

            Text(
                text = "Sélectionner tout",
                fontSize = 16.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Cart Items List
        if (cartItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Votre panier est vide",
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(cartItems) { item ->
                    CartItemRow(
                        item = item,
                        isSelected = viewModel.isItemSelected(item.id),
                        onSelectionChanged = { isSelected ->
                            viewModel.selectItem(item.id, isSelected)
                        },
                        onQuantityChanged = { newQuantity ->
                            if (newQuantity > 0) {
                                coroutineScope.launch {
                                    viewModel.updateItemQuantity(item.id, newQuantity)
                                }
                            }
                        },
                        onDeleteClicked = {
                            coroutineScope.launch {
                                viewModel.removeFromCart(item.id)
                            }
                        }
                    )
                }
            }
        }

        // Total and Checkout Button
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Total",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )

                Text(
                    text = "MAD $totalPrice",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E5FAA)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: Implement checkout process */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1E5FAA)),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "Acheter maintenant",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: ProduitPanier,
    isSelected: Boolean,
    onSelectionChanged: (Boolean) -> Unit,
    onQuantityChanged: (Int) -> Unit,
    onDeleteClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Selection Checkbox
        Checkbox(
            checked = isSelected,
            onCheckedChange = onSelectionChanged,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF1E5FAA),
                uncheckedColor = Color.Gray
            ),
            modifier = Modifier.size(24.dp)
        )

        // Product Image (Utiliser l'image du produit depuis l'URL)
        Box(
            modifier = Modifier
                .size(80.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)
        ) {
            // Utiliser AsyncImage pour charger l'image du produit
            if (item.image.isNotEmpty()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(item.image)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Product Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            } else {
                // Fallback au logo si l'image n'est pas disponible
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Product Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        // Product Info
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = item.nom,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.description.take(30) + if (item.description.length > 30) "..." else "",
                fontSize = 14.sp,
                color = Color.Gray,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            // Color Indicator - using a placeholder since we don't have color in ProduitPanier
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                val colorDefault = "Noir" // Default color
                Text(
                    text = colorDefault,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Price
            Text(
                text = "MAD ${item.prix}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1E5FAA),
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        // Quantity Controls
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Delete button
            IconButton(
                onClick = onDeleteClicked,
                modifier = Modifier.size(30.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }

            // Quantity controls
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                // Decrease button
                IconButton(
                    onClick = { onQuantityChanged(item.quantity - 1) },
                    modifier = Modifier
                        .size(28.dp)
                        .border(1.dp, Color.LightGray, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Remove,
                        contentDescription = "Decrease Quantity",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Quantity
                Text(
                    text = item.quantity.toString(),
                    modifier = Modifier
                        .width(30.dp)
                        .padding(horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )

                // Increase button
                IconButton(
                    onClick = { onQuantityChanged(item.quantity + 1) },
                    modifier = Modifier
                        .size(28.dp)
                        .border(1.dp, Color.LightGray, CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Increase Quantity",
                        tint = Color.Black,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }

    Divider(
        color = Color.LightGray,
        thickness = 1.dp,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp)
    )
}