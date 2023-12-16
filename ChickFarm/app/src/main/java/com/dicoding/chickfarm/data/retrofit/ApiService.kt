package com.dicoding.chickfarm.data.retrofit

import com.dicoding.chickfarm.data.response.AkunUserResponse
import com.dicoding.chickfarm.data.response.DataItem
import com.dicoding.chickfarm.data.response.ProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService{
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

}