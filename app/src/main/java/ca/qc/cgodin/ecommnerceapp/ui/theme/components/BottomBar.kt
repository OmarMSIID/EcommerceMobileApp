package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ca.qc.cgodin.ecommnerceapp.R

@Composable
fun BottomBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 4.dp
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
            onClick = { /* profil */ },
            icon = { Icon(painterResource(R.drawable.ic_profile), contentDescription = "Profil") }
        )
    }
}