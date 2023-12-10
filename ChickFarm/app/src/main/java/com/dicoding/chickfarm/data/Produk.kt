package com.dicoding.chickfarm.data

data class Produk(
    val id:Long,
    val namaToko: String,
    val namaProduk: String,
    val harga: String,
    val image: Int,
    val lokasi: String,
    val desc : String,
)