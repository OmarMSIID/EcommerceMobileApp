package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ca.qc.cgodin.ecommnerceapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BrandRow() {
    val brands = listOf(
        R.drawable.ic_apple,
        R.drawable.ic_xiaomi,
        R.drawable.ic_huawei,
        R.drawable.ic_logitech,
        R.drawable.ic_hp
    )

    val repeatedBrands = remember { List(100) { brands[it % brands.size] } }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Défilement automatique fluide
    LaunchedEffect(Unit) {
        while (true) {
            delay(2000) // Délai de 2 secondes entre chaque défilement
            val nextIndex = (listState.firstVisibleItemIndex + 1) % repeatedBrands.size
            coroutineScope.launch {
                // Défilement fluide sans utiliser d'animationSpec
                listState.animateScrollToItem(nextIndex)
            }
        }
    }

    LazyRow(
        state = listState,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .padding(start = 8.dp, end = 16.dp) // Réduire le padding à gauche
            .height(80.dp)
    ) {
        itemsIndexed(repeatedBrands) { _, icon ->
            Card(
                shape = CircleShape,
                modifier = Modifier.size(60.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(30.dp)
                    )
                }
            }
        }
    }
}
