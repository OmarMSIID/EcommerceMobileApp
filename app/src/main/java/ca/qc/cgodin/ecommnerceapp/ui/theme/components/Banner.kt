package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.qc.cgodin.ecommnerceapp.R
import kotlinx.coroutines.delay

@Composable
fun Banner() {
    val banners = listOf(
        BannerData(
            R.drawable.banner_image,
            "Nouvelle collection",
            "50% de réduction\nsur votre 1ère commande"
        ),
        BannerData(
            R.drawable.web_banner_design,
            "Offre d'été",
            "Jusqu'à 70% sur les accessoires"
        ),
        BannerData(
            R.drawable.headphones,
            "Promo spéciale",
            "Livraison gratuite dès 300DH d'achat"
        )
    )

    var currentIndex by remember { mutableStateOf(0) }

    // Carrousel automatique
    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentIndex = (currentIndex + 1) % banners.size
        }
    }

    val banner = banners[currentIndex]

    // Icons Search + Notifications
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButtonBox(icon = Icons.Filled.Search, description = "Icône de recherche")
        Spacer(modifier = Modifier.width(7.dp))
        IconButtonBox(icon = Icons.Filled.Notifications, description = "Icône de notifications")
    }

    // Carte de la bannière
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Image de fond
            Image(
                painter = painterResource(id = banner.imageRes),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Contenu textuel à gauche
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        banner.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    )
                    Text(
                        banner.description,
                        fontSize = 12.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { },
                        shape = RoundedCornerShape(20.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            "Commandez !",
                            color = Color.Black,
                            fontSize = 12.sp
                        )
                    }
                }

                // ❌ Plus de miniature ici
            }
        }
    }
}

// Petit composant pour les boutons icônes (Search + Notifications)
@Composable
fun IconButtonBox(icon: androidx.compose.ui.graphics.vector.ImageVector, description: String) {
    Box(
        modifier = Modifier
            .size(49.dp)
            .background(Color(0xFFD9D9D9), shape = CircleShape)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = description,
            modifier = Modifier.size(24.dp),
            tint = Color.Black
        )
    }
}

// Données de chaque slide de la bannière
data class BannerData(
    val imageRes: Int,
    val title: String,
    val description: String
)
