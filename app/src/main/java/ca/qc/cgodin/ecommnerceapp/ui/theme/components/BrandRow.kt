package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ca.qc.cgodin.ecommnerceapp.R

@Composable
fun BrandRow() {
    val brands = listOf(
        R.drawable.ic_apple,
        R.drawable.ic_xiaomi,
        R.drawable.ic_huawei,
        R.drawable.ic_logitech,
        R.drawable.ic_hp
    )

    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(brands) { icon ->
            Card(
                shape = CircleShape,
                modifier = Modifier.size(60.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F0F0))
            ) {
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.padding(12.dp)
                )
            }
        }
    }
}