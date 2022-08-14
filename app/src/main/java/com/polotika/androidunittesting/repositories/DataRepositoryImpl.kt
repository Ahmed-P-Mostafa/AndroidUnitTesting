package com.polotika.androidunittesting.repositories

import androidx.lifecycle.LiveData
import com.polotika.androidunittesting.data.local.ShoppingDao
import com.polotika.androidunittesting.data.local.ShoppingItem
import com.polotika.androidunittesting.data.remote.PixabayAPI
import com.polotika.androidunittesting.data.remote.responses.ImageResponse
import com.polotika.androidunittesting.utils.Resource
import java.lang.Exception
import javax.inject.Inject

class DataRepositoryImpl @Inject constructor(private val dao: ShoppingDao, private val api: PixabayAPI) :DataRepository  {
    override fun insertShoppingItem(item: ShoppingItem) {
        dao.insertShoppingItem(item)
    }

    override fun deleteShoppingItem(item: ShoppingItem) {
        dao.deleteShoppingItem(item)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return dao.observeTotalPrice()
    }

    override suspend fun searchImages(query: String): Resource<ImageResponse> {
        return try {
            val response = api.searchForImage(query)
            if (response.isSuccessful){
                response.body()?.let {
                    Resource.success(it)
                }?: Resource.error("unknown error occured",null)
            }else{
                Resource.error("unknown error occured",null)
            }
        }catch (e:Exception){
            Resource.error("Couldn't reach the server,please check your internet",null)
        }
    }
}