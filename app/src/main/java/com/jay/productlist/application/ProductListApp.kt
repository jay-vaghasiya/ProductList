package com.jay.productlist.application

import android.app.Application
import com.jay.productlist.di.repository.ProductListRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ProductListApp : Application() {
    companion object {
        lateinit var productListRepository: ProductListRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        productListRepository = ProductListRepository()
    }

}