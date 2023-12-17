package com.dicoding.chickfarm.data

data class Produk(
    val idProduk: Int,
    val stokProduk: Int,
    val gambarProduk: String,
    val namaProduk: String,
    val hargaProduk: Int,
    val deskripsiProduk: String
)

data class Pesanan(
    val idUser: Int,
    val idProduk: Int,
    val namaPenerima: String,
    val namaProduk: String,
    val alamatPengiriman: String,
    val idPesanan: Int,
    val metodePembayaran: String
)