package com.jay.productlist.di.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jay.productlist.application.ProductListApp
import com.jay.productlist.datamodels.ProductListResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val productListLiveData = MutableLiveData<ProductListResponse?>()
    val errorMessage = MutableLiveData<String?>()

    fun productList(
        registerId: String,
        isWishList: String,
        pagination: String,
        categoryId: String,
        subCategoryId: String,
        brandId: String,
        priceFilter: String,
        searchProductName: String,
        languageId: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            val productListResponse = ProductListApp.productListRepository.getProductList(
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

            if(productListResponse is ProductListResponse){
                productListLiveData.postValue(productListResponse)
            }else if (productListResponse is String){
                errorMessage.postValue(productListResponse)
            }else{
                //Do Nothing
            }
        }

    }
}