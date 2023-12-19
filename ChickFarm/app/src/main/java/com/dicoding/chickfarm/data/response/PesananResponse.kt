package com.dicoding.chickfarm.data.response

import com.dicoding.chickfarm.data.Pesanan

data class PesananResponse(
	val data: List<DataPesanan?>? = null,
	val message: String? = null
)

data class DataPesanan(
	val idUser: Int? = null,
	val idProduk: Int? = null,
	val namaPenerima: String? = null,
	val namaProduk: String? = null,
	val alamatPengiriman: String? = null,
	val idPesanan: Int? = null,
	val metodePembayaran: String? = null


){

	fun toPesanan(): Pesanan {

		return Pesanan(
			idUser = idUser ?: 0,
			idProduk = idProduk ?: 0,
			namaPenerima = namaPenerima ?: "",
			namaProduk = namaProduk ?: "",
			alamatPengiriman = alamatPengiriman ?: "",
			idPesanan = idPesanan ?: 0,
			metodePembayaran = metodePembayaran ?: ""
		)
	}
}

