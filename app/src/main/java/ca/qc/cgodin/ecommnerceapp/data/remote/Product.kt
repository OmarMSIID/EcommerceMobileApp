package ca.qc.cgodin.ecommnerceapp.data.remote

data class Product(
    val brand: String,
    val category: String,
    val color: String,
    val description: String,
    val discount: Int,
    val id: Int,
    val image: String,
    val model: String,
    val price: Double,
    val title: String
)