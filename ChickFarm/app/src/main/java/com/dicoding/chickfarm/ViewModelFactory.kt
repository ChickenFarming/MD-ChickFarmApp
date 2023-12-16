package com.dicoding.chickfarm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.retrofit.ApiService
import com.dicoding.chickfarm.di.Injection
import com.dicoding.chickfarm.ui.screen.auth.AuthViewModel
import com.dicoding.chickfarm.ui.screen.market.MarketViewModel
import com.dicoding.chickfarm.ui.screen.market.detail.DetailProductViewModel

class ViewModelFactory(private val repository: Repository,private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(MarketViewModel::class.java)) {
            return MarketViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(DetailProductViewModel::class.java)) {
            return DetailProductViewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(repository, context) as T
        }


        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        @JvmStatic
        fun getInstance(apiService: ApiService, context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(ViewModelFactory::class.java) {
                INSTANCE ?: ViewModelFactory(Injection.provideRepository(apiService), context)
                    .also { INSTANCE = it }
            }
        }
//        @JvmStatic
//        fun getInstance(apiService: ApiService): ViewModelFactory {
//            if (INSTANCE == null) {
//                synchronized(ViewModelFactory::class.java) {
//                    INSTANCE = ViewModelFactory(Injection.provideRepository( apiService),)
//                }
//            }
//            return INSTANCE as ViewModelFactory
//        }
    }

}