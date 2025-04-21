package ca.qc.cgodin.ecommnerceapp.data


data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val imageRes: Int // pour l’instant des images locales (R.drawable)
)