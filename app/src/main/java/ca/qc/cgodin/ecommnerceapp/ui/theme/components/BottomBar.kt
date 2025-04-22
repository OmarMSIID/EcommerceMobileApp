package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ca.qc.cgodin.ecommnerceapp.R
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun BottomBar() {
    NavigationBar(
        containerColor = Color(0xB0D9D9D9),
        tonalElevation = 4.dp,
        modifier = Modifier.height(80.dp), // Réduire la hauteur de la barre
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { /* panier */ },
            icon = { Icon(painterResource(R.drawable.ic_cart), contentDescription = "Panier") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { /* accueil */ },
            icon = { Icon(painterResource(R.drawable.ic_home), contentDescription = "Accueil") }
        )
        NavigationBarItem(
            selected = false,
            onClick = {
                Firebase.auth.signOut()
            },
            icon = { Icon(painterResource(R.drawable.ic_profile), contentDescription = "Profil") }
        )
    }
}
