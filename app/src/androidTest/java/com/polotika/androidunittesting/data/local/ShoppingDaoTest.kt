package com.polotika.androidunittesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.polotika.androidunittesting.getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest :TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.dao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNewItemSavedInDatabaseReturnsTrue() = runBlockingTest {
        val item = ShoppingItem(id = 1, name = "name", imageUrl = "url", price = 1f, amount = 3)
        dao.insertShoppingItem(item)

        val result = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(result.contains(item)).isTrue()
    }

    @Test
    fun deleteItemTestReturnTrue() = runBlockingTest {
        val item = ShoppingItem(id = 1, name = "name", imageUrl = "url", price = 1f, amount = 3)
        dao.insertShoppingItem(item)
        dao.deleteShoppingItem(item)
        val result = dao.observeAllShoppingItems().getOrAwaitValue()
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun observeTotalSum() = runBlockingTest {
        val item1 = ShoppingItem(id = 1, name = "name", imageUrl = "url", price = 1f, amount = 3)
        val item2 = ShoppingItem(id = 2, name = "name", imageUrl = "url", price = 2f, amount = 3)
        val item3 = ShoppingItem(id = 3, name = "name", imageUrl = "url", price = 0f, amount = 3)

        dao.insertShoppingItem(item1)
        dao.insertShoppingItem(item2)
        dao.insertShoppingItem(item3)

        val result = dao.observeTotalPrice().getOrAwaitValue()
        assertThat(result).isEqualTo(1*3f + 2*3f)

    }



}