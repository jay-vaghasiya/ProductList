package com.jay.productlist.datamodels

data class Result(
    val BrandId: String,
    val BrandName: String,
    val CGST: String,
    val CategoryId: String,
    val CategoryName: String,
    val GST: String,
    val IGST: String,
    val Image: List<Image>,
    val IsOrganic: String,
    val IsWishlisted: String,
    val ProductCoverImage: String,
    val ProductId: String,
    val ProductName: String,
    val SGST: String,
    val SubCategoryId: String,
    val SubCategoryName: String,
    val Varient: List<Varient>
)