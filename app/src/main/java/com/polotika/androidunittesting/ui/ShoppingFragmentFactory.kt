package com.polotika.androidunittesting.ui
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.polotika.androidunittesting.adapters.ImagesListAdapter
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImagesListAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}