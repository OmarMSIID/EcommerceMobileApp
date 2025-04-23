package ca.qc.cgodin.ecommnerceapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("products")
    suspend fun getProducts(): ApiResponse

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): Product
}