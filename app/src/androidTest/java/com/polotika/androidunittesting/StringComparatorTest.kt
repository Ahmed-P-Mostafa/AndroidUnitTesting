package com.polotika.androidunittesting

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat

import org.junit.After
import org.junit.Before
import org.junit.Test

class StringComparatorTest {

    private lateinit var comparator : StringComparator
    private lateinit var context: Context

    @Before
    fun setUp() {
        comparator = StringComparator()
        //context = ApplicationProvider.getApplicationContext()
    }

    @After
    fun tearDown() {

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