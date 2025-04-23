package ca.qc.cgodin.ecommnerceapp.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.data.Product

@Composable
fun ProductDescriptionScreen(
    navController: NavHostController,
    product: Product
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Barre de navigation supérieure avec bouton retour
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Retour"
                )
            }

            Text(
                text = "Détails du produit",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        // Image du produit
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Image(
                painter = painterResource(id = product.imageRes),
                contentDescription = product.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Fit
            )
        }

        // Nom du produit
        Text(
            text = product.name,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp)
        )

        // Catégorie
        Text(
            text = product.category,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 16.dp, top = 2.dp)
        )

        // Section des couleurs
        Text(
            text = "Couleurs",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        // Options de couleur
        Row(
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF1E3A8A))
            )
        }

        // Description du produit
        Text(
            text = "Description du produit",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )

        Text(
            text = "Casque sans fil Bluetooth offrant un son de qualité, des basses profondes et un design léger. Autonomie longue, micro intégré, mode filaire disponible. Compatible smartphones, tablettes et PC.",
            fontSize = 14.sp,
            color = Color.DarkGray,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        // Section prix + bouton d'ajout
        Spacer(modifier = Modifier.height(135.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Total",
                    fontSize = 14.sp,
                    color = Color.Gray
                )

                Text(
                    text = "MAD ${product.price}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1E3A8A)
                )
            }

            Button(
                onClick = { /* Action pour ajouter au panier */ },
                modifier = Modifier
                    .height(48.dp)
                    .width(200.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1E3A8A)
                )
            ) {
                Text(
                    text = "Ajouter au panier",
                    color = Color.White
                )
            }
        }
    }
}