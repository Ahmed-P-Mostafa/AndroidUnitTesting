package com.polotika.androidunittesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class StringComparatorTest {

    private lateinit var comparator : StringComparator

    @Before
    fun setUp() {
        comparator = StringComparator()
    }


    @Test
    fun stringResourceWithSameString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = comparator.isSameString(context,R.string.app_name,"AndroidUnitTesting")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceWithDifferentString_returnsFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = comparator.isSameString(context,R.string.app_name,"string ")
        assertThat(result).isFalse()
    }
}