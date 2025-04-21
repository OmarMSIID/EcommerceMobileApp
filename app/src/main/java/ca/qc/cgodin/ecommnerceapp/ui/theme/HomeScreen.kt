package ca.qc.cgodin.ecommnerceapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.ui.theme.components.*


@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        bottomBar = { BottomBar() }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
                .padding(16.dp)
        ) {
            Banner()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Marques", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            BrandRow()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Produits", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(8.dp))
            ProductGrid()
        }
    }
}