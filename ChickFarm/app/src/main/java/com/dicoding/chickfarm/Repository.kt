package com.dicoding.chickfarm

import com.dicoding.chickfarm.data.ListProduk
import com.dicoding.chickfarm.data.ProductData
import com.dicoding.chickfarm.data.Produk

class Repository {

    private val productList = mutableListOf<ListProduk>()

    init{
        if(productList.isEmpty()){
            ProductData.product.forEach{
                productList.add(ListProduk(it,))
            }
        }
    }

fun getAllProduct(): List<Produk> {
    return ProductData.product
}


    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(): Repository =
            instance ?: synchronized(this) {
                Repository().apply {
                    instance = this
                }
            }
    }
}