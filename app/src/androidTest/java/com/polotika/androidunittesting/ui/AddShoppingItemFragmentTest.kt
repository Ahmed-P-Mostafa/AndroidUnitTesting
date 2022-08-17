package com.polotika.androidunittesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.polotika.androidunittesting.R
import com.polotika.androidunittesting.data.local.ShoppingItem
import com.polotika.androidunittesting.getOrAwaitValue
import com.polotika.androidunittesting.launchFragmentInHiltContainer
import com.polotika.androidunittesting.repositories.FakeDataRepositoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun pressImageView_navigateToPickImageFragment() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        onView(withId(R.id.ivShoppingImage)).perform(click())
        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )

    }

    @Test
    fun pressInsertButtonWithValidInput_insertNewItemToDb(){
        val testViewModel = ShoppingViewModel(FakeDataRepositoryAndroidTest())
        launchFragmentInHiltContainer<AddShoppingItemFragment> { viewModel = testViewModel }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("Shopping Item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.shoppingItems.getOrAwaitValue())
            .contains(ShoppingItem(name = "Shopping Item", price = 5f, amount = 5, imageUrl = ""))

    }

}