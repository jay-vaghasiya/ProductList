package com.jay.productlist

import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import com.jay.productlist.datamodels.model.ProductModel
import com.jay.productlist.di.viewmodel.ProductListViewModel
import com.jay.productlist.ui.theme.LightGreen
import com.jay.productlist.ui.theme.LightRed
import com.jay.productlist.ui.theme.ProductListTheme

class MainActivity : ComponentActivity() {

    private lateinit var productListViewModel: ProductListViewModel
    private val productList = mutableListOf<ProductModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductListTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var dataObserved by remember {
                        mutableStateOf(false)
                    }

                    productListViewModel = ViewModelProvider(this)[ProductListViewModel::class.java]

                    productListViewModel.productListLiveData.observe(this) { productResponse ->
                        Log.d("productListAll", productResponse.toString())

                        if (productResponse != null) {
                            productList.clear()
                            productResponse.Result.forEach {
                                val productId = it.ProductId
                                val productName = it.ProductName
                                var productImageUri = ""
                                val productImageUriList = it.Varient
                                productImageUriList.forEach { image ->
                                    if (image.ProductImage.isNotEmpty()) {
                                        productImageUri = image.ProductImage
                                    }
                                }
                                val productCategory = it.BrandName
                                productList.add(
                                    ProductModel(
                                        productId = productId,
                                        productName = productName,
                                        productImageUri = productImageUri,
                                        productCategory = productCategory
                                    )
                                )
                            }
                            dataObserved = true
                        }

                    }

                    productListViewModel.errorMessage.observe(this) { errorMessage ->
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                        Log.d("errorProduct", errorMessage.toString())
                    }

                    productListViewModel.productList(
                        registerId = "1",
                        isWishList = "",
                        pagination = "1",
                        searchProductName = "",
                        brandId = "",
                        subCategoryId = "",
                        categoryId = "",
                        languageId = "",
                        priceFilter = ""
                    )

                    if (dataObserved && productList.isNotEmpty()) {
                        DisplayList(productList)
                    }


                }

            }
        }
    }

    @Composable
    private fun DisplayList(productList: MutableList<ProductModel>) {
        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            Log.d("LLLLLLIST", productList.toString())
            itemsIndexed(items = productList) { index, item ->
                GridItem(item, index)
            }
        }
    }

    fun getZigZagColor(position: Int): Color {
        return if (position % 4 == 0 || position % 4 == 3) LightRed else LightGreen
    }


    @Composable
    private fun GridItem(productModel: ProductModel, position: Int) {
        Log.d("LLLLLLIST", productModel.toString())

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = getZigZagColor(position))
                    .padding(16.dp)
            ) {
                IconButton(modifier = Modifier
                    .align(alignment = Alignment.End)
                    .padding(8.dp), onClick = {}) {
                    Icon(Icons.Outlined.Star, contentDescription = "Favorite")
                }
                Image(
                    imageVector = Icons.Rounded.AccountBox,
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp),
                    contentScale = ContentScale.Crop,
                    colorFilter = ColorFilter.tint(Color.LightGray)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Transparent)
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                ) {
                    Text(
                        text = productModel.productName,
                        style = MaterialTheme.typography.titleMedium,
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = productModel.productCategory,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.DarkGray
                    )
                }

            }

        }
    }
}
