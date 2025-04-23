package ca.qc.cgodin.ecommnerceapp.data

import ca.qc.cgodin.ecommnerceapp.R

object ProductRepository {
    val products = listOf(
        Product("1", "Casque stereo P47", "Casques", 140.0, R.drawable.headphones),
        Product("2", "Galaxy watch 7", "Accessoires", 450.0, R.drawable.watch),
        Product("3", "Samsung galaxy s21", "Smartphones", 2549.0, R.drawable.s21),
        Product("4", "Wireless keyboard", "Accessoires", 350.0, R.drawable.keyboard)
    )

    var selectedProduct: Product? = null

    fun getProductById(id: String?): Product? {
        return products.find { it.id == id }
    }

    fun selectProduct(id: String) {
        selectedProduct = getProductById(id)
    }
}