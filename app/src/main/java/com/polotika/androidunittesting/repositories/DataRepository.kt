package com.polotika.androidunittesting.repositories

import androidx.room.Dao
import androidx.room.Query
import com.polotika.androidunittesting.data.local.ShoppingDao
import com.polotika.androidunittesting.data.remote.responses.ImageResponse
import com.polotika.androidunittesting.data.remote.responses.ImageResult
import com.polotika.androidunittesting.utils.Resource

interface DataRepository :ShoppingDao {
    suspend fun searchImages(query: String):Resource<ImageResponse>
}