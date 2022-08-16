package com.polotika.androidunittesting.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.polotika.androidunittesting.data.local.ShoppingItem
import com.polotika.androidunittesting.data.remote.responses.ImageResponse
import com.polotika.androidunittesting.repositories.DataRepository
import com.polotika.androidunittesting.utils.Resource

class FakeDataRepository : DataRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()
    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>(0f)
    private var shouldReturnNetworkError : Boolean = false

    private fun refreshLiveData() {
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(
            shoppingItems.sumOf { it.price.toDouble() * it.amount.toDouble() }.toFloat())
    }



    fun setNetworkError(value:Boolean){
        shouldReturnNetworkError = value
    }
    override fun insertShoppingItem(item: ShoppingItem) {
        shoppingItems.add(item)
        refreshLiveData()
    }

    override fun deleteShoppingItem(item: ShoppingItem) {
        shoppingItems.remove(item)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchImages(query: String): Resource<ImageResponse> {
        return if (shouldReturnNetworkError){
            return Resource.error("error",null)
        }else{
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}