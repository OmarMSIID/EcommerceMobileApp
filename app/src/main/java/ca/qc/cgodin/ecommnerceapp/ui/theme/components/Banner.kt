package ca.qc.cgodin.ecommnerceapp.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ca.qc.cgodin.ecommnerceapp.R


@Composable
fun Banner() {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFDDEEFF))
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f)) {
                Text("Nouvelle collection", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text("50% de réduction\nsur votre 1ère commande", fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { }) {
                    Text("Commandez !")
                }
            }
            Image(
                painter = painterResource(id = R.drawable.banner_image), // à remplacer
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}