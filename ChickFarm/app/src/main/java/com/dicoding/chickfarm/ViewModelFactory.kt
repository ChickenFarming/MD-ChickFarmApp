package com.dicoding.chickfarm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.chickfarm.ui.screen.map.MapViewModel

//class ViewModelFactory(val context: Context) :
//    ViewModelProvider.NewInstanceFactory() {
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//
//        if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
//            return MapViewModel(context = context) as T
//        }
//
//        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//    }
//}