package ca.qc.cgodin.ecommnerceapp.data.remote

import retrofit2.http.GET

interface ApiInterface {
    @GET("products")
    suspend fun getProducts(): ApiResponse
}