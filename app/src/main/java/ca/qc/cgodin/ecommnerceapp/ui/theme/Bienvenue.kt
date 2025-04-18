package ca.qc.cgodin.ecommnerceapp.ui.theme

import android.print.PrintAttributes.Margins
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ca.qc.cgodin.ecommnerceapp.R

@Composable
fun WelcomeScreen(navController: NavHostController) {
    val blueColor = Color(0xFF1E5FAA) // Définition d'une couleur bleue personnalisée

    // Utilisation de Box pour occuper tout l'écran avec un fond noir
    Box(
        modifier = Modifier
            .fillMaxSize() // Remplit tout l'écran
            .background(Color.Black) // Définit un fond noir
    ) {
        // Colonne contenant le contenu principal
        Column(
            modifier = Modifier
                .fillMaxSize() // Remplit tout l'écran
                .background(Color.White), // Fond blanc pour la colonne
            horizontalAlignment = Alignment.CenterHorizontally, // Centrer horizontalement
            verticalArrangement = Arrangement.SpaceBetween // Espacement uniforme des éléments
        ) {
            // Espace vide pour créer un margin top (alternative : utiliser Modifier.padding(top = 60.dp))
            Spacer(modifier = Modifier.height(60.dp))

            // Affichage du logo de l'application
            Image(
                painter = painterResource(id = R.drawable.logo), // Charge une image depuis les ressources
                contentDescription = "TechnoShop Logo", // Texte pour l'accessibilité
                modifier = Modifier.size(325.dp) // Taille du logo
            )

            // Bloc contenant le texte de bienvenue et les boutons
            Box(
                modifier = Modifier
                    .width(420.dp) // Largeur du bloc
                    .height(330.dp) // Hauteur du bloc
                    .background(blueColor, shape = RoundedCornerShape(topStart = 45.dp, topEnd = 45.dp)) // Fond bleu avec coins arrondis
                    .padding(24.dp) // Marge intérieure
            ) {
                Column(horizontalAlignment = Alignment.Start) {
                    Spacer(modifier = Modifier.height(25.dp)) // Espacement avant
                    // Titre de bienvenue
                    Text(
                        text = "Bienvenue",
                        fontSize = 23.sp, // Taille du texte
                        fontWeight = FontWeight.Bold, // Texte en gras
                        color = Color.White // Couleur blanche
                    )

                    Spacer(modifier = Modifier.height(27.dp)) // Espacement entre les textes

                    // Description sous le titre
                    Text(
                        text = "Découvrez les meilleures offres en électronique. Achetez en toute simplicité et sécurité !",
                        fontSize = 14.sp,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(40.dp)) // Espacement avant les boutons

                    // Ligne contenant les boutons Connexion et Inscription
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly, // Répartir uniformément les boutons
                        modifier = Modifier.fillMaxWidth() // La ligne prend toute la largeur
                    ) {
                        // Bouton Connexion
                        Button(
                            onClick = {navController.navigate("login_screen")},
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black), // Fond noir
                            modifier = Modifier
                                .width(170.dp) // Chaque bouton prend une part égale
                                .height(51.dp)
                                //.padding(end = 13.dp) // Espacement entre les boutons
                                .width(1.dp) // Cette ligne est inutile, car `.weight(1f)` gère la largeur
                            //.contentAlignment = Alignement.
                        ) {
                            Text(text = "Connexion", color = Color.White) // Texte blanc
                        }

                        // Bouton Inscription
                        Button(
                            onClick = { navController.navigate("signup_screen") },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White), // Fond blanc
                            modifier = Modifier
                                .width(170.dp) // Chaque bouton prend une part égale
                                .height(51.dp)

                            //.padding(start = 13.dp) // Espacement entre les boutons
                        ) {
                            Text(text = "S’inscrire", color = Color.Black) // Texte noir
                        }
                    }
                }
            }
        }
    }
}
