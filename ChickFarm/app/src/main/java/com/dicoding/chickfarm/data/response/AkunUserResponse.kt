package com.dicoding.chickfarm.data.response

data class AkunUserResponse(
    val data: List<DataItem?>? = null,
    var message: String? = null
)

data class DataItem(
	val idUser: Int? = null,
	val password: String? = null,
	val email: String? = null,
	val username: String? = null
)

