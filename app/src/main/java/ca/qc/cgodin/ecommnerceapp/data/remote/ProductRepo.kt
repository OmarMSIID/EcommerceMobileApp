package ca.qc.cgodin.ecommnerceapp.data.remote

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
}