package com.jay.productlist.di.repository

import com.jay.productlist.di.api.ProductListInstance
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@Singleton
class ProductListRepository {

    suspend fun getProductList(
        registerId: String,
        isWishList: String,
        pagination: String,
        categoryId: String,
        subCategoryId: String,
        brandId: String,
        priceFilter: String,
        searchProductName: String,
        languageId: String
    ): Any? {
        val response = try {
            ProductListInstance.api.getProductList(
                registerId,
                isWishList,
                pagination,
                categoryId,
                subCategoryId,
                brandId,
                priceFilter,
                searchProductName,
                languageId
            )
        } catch (e: IOException) {
            return e.message
        } catch (e: HttpException) {
            return e.message()
        } catch (e: Exception) {
            return e.message
        }
        return response.body() ?: ""
    }
}