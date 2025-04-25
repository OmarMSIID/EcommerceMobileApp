package ca.qc.cgodin.ecommnerceapp.data.remote

import kotlinx.coroutines.withContext
import ca.qc.cgodin.ecommnerceapp.database.ProduitPanier
import ca.qc.cgodin.ecommnerceapp.database.ProduitPanierDao
import kotlinx.coroutines.Dispatchers

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
            try {
                val produitPanier = ProduitPanier(
                    nom = product.title,
                    description = product.description,
                    prix = product.price.toFloat(),
                    quantity = quantity,
                    image = product.image
                )
                panierDao.addProduit(produitPanier)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    suspend fun removeProductFromCart(panierDao: ProduitPanierDao, productId: Int) {
        withContext(Dispatchers.IO) {
            try {
                panierDao.deleteProduit(productId)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    suspend fun updateProductQuantity(panierDao: ProduitPanierDao, productId: Int, quantity: Int) {
        withContext(Dispatchers.IO) {
            try {
                panierDao.updateProduitQuantity(productId, quantity)
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
    }

    suspend fun getAllCartProducts(panierDao: ProduitPanierDao): List<ProduitPanier> {
        return withContext(Dispatchers.IO) {
            try {
                panierDao.getAllProduit()
            } catch (e: Exception) {
                e.printStackTrace()
                emptyList()
            }
        }
    }
}