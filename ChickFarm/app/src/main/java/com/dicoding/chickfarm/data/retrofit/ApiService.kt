package com.dicoding.chickfarm.data.retrofit

import com.dicoding.chickfarm.data.response.AkunUserResponse
import com.dicoding.chickfarm.data.response.DataItem
import com.dicoding.chickfarm.data.response.DataPesanan
import com.dicoding.chickfarm.data.response.PesananResponse
import com.dicoding.chickfarm.data.response.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("akunUser")
    suspend fun getAllUsers(
    ): AkunUserResponse

    @POST("akunUser")
    suspend fun insertUser(
        @Body dataItem: DataItem
    ): AkunUserResponse

    @GET("product")
    suspend fun getAllProduct(
    ): ProductResponse

    @POST("pesanan")
    suspend fun insertPesanan(
        @Body dataPesanan: DataPesanan
    ): PesananResponse

    @GET("pesanan")
    suspend fun getAllOrders(
    ): PesananResponse
    @DELETE("pesanan/{idPesanan}")
    suspend fun hapusPesanan(@Path("idPesanan") idPesanan: Int): Response<Unit>
}

