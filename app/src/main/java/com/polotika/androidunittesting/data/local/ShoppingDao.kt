package com.polotika.androidunittesting.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShoppingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertShoppingItem(item: ShoppingItem)

    @Delete
    fun deleteShoppingItem(item: ShoppingItem)

    @Query("select * from shoppingitem")
    fun observeAllShoppingItems():LiveData<List<ShoppingItem>>

    @Query("select sum(price * amount) from shoppingitem")
    fun observeTotalPrice():LiveData<Float>
}