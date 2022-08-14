package com.polotika.androidunittesting.utils

import android.content.Context

class StringComparator {

    fun isSameString(context: Context,strId:Int, string: String):Boolean{
        return context.getString(strId) == string
    }
}
