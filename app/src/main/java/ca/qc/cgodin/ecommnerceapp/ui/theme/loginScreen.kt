package ca.qc.cgodin.ecommnerceapp.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material3.Icon
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.compose.foundation.clickable
import android.R.attr.onClick
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.AlertDialogDefaults.containerColor


@Composable
fun LoginScreen(navController: NavHostController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val blueColor = Color(0xFF1E5FAA) // Définition d'une couleur bleue personnalisée
    val textFieldGray = Color(0xFF7F7F7F)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(blueColor)
        ) {
            // Barre du haut
            Spacer(modifier = Modifier.height(40.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { navController.navigate("welcome_screen") },
                    tint = Color.White,

                )
                Text(
                    text = "S’inscrire",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable{
                        navController.navigate("signup_screen")
                    }
                )
            }

            Spacer(modifier = Modifier.height(21.dp))

            // Titre et sous-titre
            Text(
                text = "Connexion",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Accédez à votre compte et commencez votre\nexpérience d'achat dès maintenant.",
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.height(55.dp))

            // Partie blanche en bas
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White, shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(80.dp))
                // Champ nom d'utilisateur
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = "Nom d'utilisateur",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    ) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = textFieldGray,
                        unfocusedLabelColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = textFieldGray,
                        focusedTextColor = Color.Black,
                    )
                )


                // Champ mot de passe
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Mot de passe",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    shape = RoundedCornerShape(50),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = textFieldGray,
                        unfocusedLabelColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedContainerColor = textFieldGray,
                        focusedTextColor = Color.Black,
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                )

                // Lien mot de passe oublié
                TextButton(
                    onClick = { navController.navigate("forget_password_screen") },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text("Mot de passe oublié ?", color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(70.dp))

                // Bouton Connexion
                Button(
                    onClick = { /* Handle login */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    shape = RoundedCornerShape(50),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = blueColor
                    )
                ) {
                    Text("Connexion", color = Color.White)
                }
            }
        }
    }
}