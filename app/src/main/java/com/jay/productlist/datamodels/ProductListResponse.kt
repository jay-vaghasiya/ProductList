package com.jay.productlist.datamodels

data class ProductListResponse(
    val Result: List<Result>,
    val Status: Boolean,
    val StatusMessage: String
)