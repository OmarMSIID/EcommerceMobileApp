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
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import ca.qc.cgodin.ecommnerceapp.AppUtil.AppUtil
import ca.qc.cgodin.ecommnerceapp.auth.AuthModel


@Composable
fun SignupScreen(navController: NavController, authViewModel: AuthModel = viewModel()) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    val context= LocalContext.current

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
                    tint = Color.White
                )
                Text(
                    text = "Connexion",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(21.dp))

            // Titre et sous-titre
            Text(
                text = "Créer un compte",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Rejoignez-nous et commencez votre shopping dès maintenant !",
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
                Spacer(modifier = Modifier.height(20.dp))
                var emailError by remember { mutableStateOf(false) }

                // Champ email
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(it).matches()
                    },
                    isError = emailError,
                    label = {
                        Text(
                            text = "E-mail",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
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
                    ),
                    singleLine = true
                )
                // Affichage d’un message d’erreur
                if (emailError) {
                    Text(
                        text = "Adresse e-mail invalide",
                        color = Color.Red,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                    )
                }


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

                OutlinedTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = { Text(text = "Confirmer le mot de passe",
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

                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(text = "Adresse",
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

                Spacer(modifier = Modifier.height(70.dp))

                // Bouton Connexion
                Button(
                    onClick = {
                        authViewModel.signUp(email,password,address){isSuccess,errorMsg->
                            if(isSuccess){
                                navController.navigate("login_screen"){
                                    popUpTo(0){
                                        inclusive=true
                                    }
                                }
                            }
                            else{
                                AppUtil.showToaster(context,errorMsg?:"something goes wrong")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(53.dp),
                    shape = RoundedCornerShape(50),
                    colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                        containerColor = blueColor
                    )
                ) {
                    Text("S’inscrire", color = Color.White)
                }
            }
        }
    }
}