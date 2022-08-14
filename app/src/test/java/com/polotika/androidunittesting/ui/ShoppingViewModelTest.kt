package com.polotika.androidunittesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.polotika.androidunittesting.MainCoroutineRule
import com.polotika.androidunittesting.getOrAwaitValueTest
import com.polotika.androidunittesting.repositories.FakeDataRepository
import com.polotika.androidunittesting.utils.Constants
import com.polotika.androidunittesting.utils.Constants.MAX_AMOUNT_LENGTH
import com.polotika.androidunittesting.utils.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class ShoppingViewModelTest {
    private lateinit var viewModel: ShoppingViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainRule = MainCoroutineRule()

    @Before
    fun setup() {
        viewModel = ShoppingViewModel(FakeDataRepository())
    }

    @Test
    fun `insert shopping item with empty field return error`() {
        viewModel.insertShoppingItem("name", "", "10")
        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too long name return error`() {
        val name = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1) {
                append("a")
            }
        }
        viewModel.insertShoppingItem(name, "1.5", "10")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too price name return error`() {
        val price = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1) {
                append("a")
            }
        }
        viewModel.insertShoppingItem("name", "1.5", price)

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with invalid amount return error`() {
        val amount = buildString {
            for (i in 1..MAX_AMOUNT_LENGTH +1){
                append(1)
            }
        }
        viewModel.insertShoppingItem(name ="name", amountString = amount, priceString = "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with valid input return success`() {
        viewModel.insertShoppingItem("name", "3", "3.0")

        val result = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()
        assertThat(result.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }
}