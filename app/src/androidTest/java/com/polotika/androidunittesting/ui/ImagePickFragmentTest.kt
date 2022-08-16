package com.polotika.androidunittesting.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import com.polotika.androidunittesting.R
import com.polotika.androidunittesting.adapters.ImagesListAdapter
import com.polotika.androidunittesting.data.FakeDataRepository
import com.polotika.androidunittesting.getOrAwaitValue
import com.polotika.androidunittesting.launchFragmentInHiltContainer
import com.polotika.androidunittesting.repositories.FakeDataRepositoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class ImagePickFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    lateinit var viewModel: ShoppingViewModel

    @Before
    fun before() {
        hiltRule.inject()
        viewModel = ShoppingViewModel(FakeDataRepository())
    }

    @Test
    fun pressBack_clearCurrentImageUrl() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ImagePickFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        Espresso.pressBack()
        val value = viewModel.curImageUrl.getOrAwaitValue()
        assertThat(value).isEmpty()
    }

    @Test
    fun clickImage_popBackStack() {
        val testViewModel = ShoppingViewModel(FakeDataRepositoryAndroidTest())
        val navController = mock(NavController::class.java)
        val imageUrl = "test"
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
            imageAdapter.images = listOf(imageUrl)
        }

        onView(withId(R.id.rvImages)).perform(RecyclerViewActions
            .actionOnItemAtPosition<ImagesListAdapter.ViewHolder>(0,click()))
        verify(navController).popBackStack()
    }

    @Test
    fun clickImage_setImageUrl() {
        val testViewModel = ShoppingViewModel(FakeDataRepositoryAndroidTest())
        val navController = mock(NavController::class.java)
        val imageUrl = "test"
        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            viewModel = testViewModel
            imageAdapter.images = listOf(imageUrl)
        }

        onView(withId(R.id.rvImages)).perform(RecyclerViewActions
            .actionOnItemAtPosition<ImagesListAdapter.ViewHolder>(0,click()))
        assertThat(viewModel.curImageUrl.getOrAwaitValue()).isEqualTo(imageUrl)
    }
}