package com.jay.productlist.di.api

import com.jay.productlist.utils.NetworkModule
import okhttp3.OkHttpClient

object ProductListInstance {
    val api: ProductListAPI by lazy {
        NetworkModule.provideRetrofit(OkHttpClient())
            .create(ProductListAPI::class.java)
    }
}