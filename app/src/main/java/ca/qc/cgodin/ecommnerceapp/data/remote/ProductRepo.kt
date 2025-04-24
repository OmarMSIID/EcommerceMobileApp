package ca.qc.cgodin.ecommnerceapp.data.remote

import kotlinx.coroutines.withContext
import ca.qc.cgodin.ecommnerceapp.database.ProduitPanier
import ca.qc.cgodin.ecommnerceapp.database.ProduitPanierDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepo {
    private val apiService = RetrofitClient.apiService

    suspend fun getProducts(): Result<List<Product>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getProducts()
                Result.success(response.products)
            } catch (e: Exception) {
                Result.failure(Exception("Error fetching products: ${e.message}"))
            }
        }
    }

    suspend fun addProductToCart(panierDao: ProduitPanierDao, product: Product, quantity: Int = 1) {
        withContext(Dispatchers.IO) {
            val produitPanier = ProduitPanier(
                nom = product.title,
                description = product.description,
                prix = product.price.toFloat(),
                quantity = quantity,
                image = product.image // Ajout de l'URL de l'image
            )
            panierDao.addProduit(produitPanier)
        }
    }

    suspend fun removeProductFromCart(panierDao: ProduitPanierDao, productId: Int) {
        withContext(Dispatchers.IO) {
            panierDao.deleteProduit(productId)
        }
    }

    suspend fun updateProductQuantity(panierDao: ProduitPanierDao, productId: Int, quantity: Int) {
        withContext(Dispatchers.IO) {
            panierDao.updateProduitQuantity(productId, quantity)
        }
    }

    suspend fun getAllCartProducts(panierDao: ProduitPanierDao): List<ProduitPanier> {
        return withContext(Dispatchers.IO) {
            panierDao.getAllProduit()
        }
    }
}