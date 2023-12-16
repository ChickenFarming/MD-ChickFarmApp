package com.dicoding.chickfarm.di

import com.dicoding.chickfarm.data.Repository
import com.dicoding.chickfarm.data.retrofit.ApiService

object Injection {
    fun provideRepository(apiService: ApiService): Repository {
        return Repository.getInstance(apiService)
    }
}