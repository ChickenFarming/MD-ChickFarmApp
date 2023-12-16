package com.dicoding.chickfarm.data.response

import com.dicoding.chickfarm.data.Produk

data class ProductResponse(
	val data: List<DataProduct?>? = null,
	val message: String? = null
)

data class DataProduct(
	val idProduk: Int? = null,
	val stokProduk: Int? = null,
	val gambarProduk: String? = null,
	val namaProduk: String? = null,
	val hargaProduk: Int? = null,
	val deskripsiProduk: String? = null


){
	fun toProduk(): Produk {
		return Produk(
			idProduk = idProduk ?: 0,
			stokProduk = stokProduk ?: 0,
			gambarProduk = gambarProduk ?: "",
			namaProduk = namaProduk ?: "",
			hargaProduk = hargaProduk ?: 0,
			deskripsiProduk = deskripsiProduk ?: ""
		)
	}
}

