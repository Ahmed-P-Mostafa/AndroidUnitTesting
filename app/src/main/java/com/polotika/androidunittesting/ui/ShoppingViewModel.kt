package com.polotika.androidunittesting.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.polotika.androidunittesting.data.local.ShoppingItem
import com.polotika.androidunittesting.data.remote.responses.ImageResponse
import com.polotika.androidunittesting.repositories.DataRepository
import com.polotika.androidunittesting.utils.Constants
import com.polotika.androidunittesting.utils.Event
import com.polotika.androidunittesting.utils.Resource
import kotlinx.coroutines.launch

class ShoppingViewModel @ViewModelInject constructor(
    private val repository: DataRepository
) : ViewModel() {

    val shoppingItems = repository.observeAllShoppingItems()

    val totalPrice = repository.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url: String) {
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.deleteShoppingItem(shoppingItem)
    }

    private fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repository.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {
        if(name.isEmpty() || amountString.isEmpty() || priceString.isEmpty()){
            _insertShoppingItemStatus.postValue(Event(Resource.error("All fields most not be empty")))
            return
        }
        if (name.length>Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Name must not exceeds ${Constants.MAX_NAME_LENGTH} characters")))
            return
        }

        if (priceString.length>Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Price must not exceeds ${Constants.MAX_PRICE_LENGTH} characters")))
            return
        }

        try {
            amountString.toInt()
        }catch (e:Exception){
            _insertShoppingItemStatus.postValue(Event(Resource.error("Please enter valid amount")))
            return
        }

        if (amountString.length > Constants.MAX_AMOUNT_LENGTH){
            _insertShoppingItemStatus.postValue(Event(Resource.
            error("Amount must not exceeds ${Constants.MAX_AMOUNT_LENGTH} characters")))
            return
        }

        val shoppingItem = ShoppingItem(
            name = name,
            price = priceString.toFloat(),
            amount = amountString.toInt(),
            imageUrl = _curImageUrl.value?:"")
        insertShoppingItemIntoDb(shoppingItem)
        _curImageUrl.value = ""
        _insertShoppingItemStatus.postValue(Event(Resource.success(shoppingItem)))
    }

    fun searchForImage(imageQuery: String) {
        if (imageQuery.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading())
        viewModelScope.launch {
            val response = repository.searchImages(imageQuery)
            _images.postValue(Event(response))
        }
    }
}
