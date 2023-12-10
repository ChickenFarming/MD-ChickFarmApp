package com.dicoding.chickfarm.di

import com.dicoding.chickfarm.Repository

object Injection {
    fun provideRepository(): Repository {
        return Repository.getInstance()
    }
}