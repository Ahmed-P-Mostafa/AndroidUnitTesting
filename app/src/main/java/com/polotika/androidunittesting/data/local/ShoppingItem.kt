package com.polotika.androidunittesting.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var name: String,
    var imageUrl: String,
    var price: Float,
    var amount: Int,
)
