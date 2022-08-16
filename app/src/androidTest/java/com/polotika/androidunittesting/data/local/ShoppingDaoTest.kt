package com.polotika.androidunittesting.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.polotika.androidunittesting.getOrAwaitValue
import com.polotika.androidunittesting.launchFragmentInHiltContainer
import com.polotika.androidunittesting.ui.ShoppingFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_database")
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject()
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
        assertThat(result).isEqualTo(1 * 3f + 2 * 3f)
    }
}