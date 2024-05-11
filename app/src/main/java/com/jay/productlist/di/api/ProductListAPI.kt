package com.jay.productlist.di.api

import com.jay.productlist.datamodels.ProductListResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductListAPI {

    @POST("/api/ProductList")
    suspend fun getProductList(
        @Query("RegisterId") registerId: String,
        @Query("Iswishlist") isWishList: String,
        @Query("Pagination") pagination: String,
        @Query("CategoryId") categoryId: String,
        @Query("SubCategoryId") subCategoryId: String,
        @Query("BrandId") brandId: String,
        @Query("PriceFilter") priceFilter: String,
        @Query("SearchProductName") searchProductName: String,
        @Query("LanguageId") languageId: String
    ): Response<ProductListResponse>
}