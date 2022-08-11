package com.polotika.androidunittesting

import android.content.Context

class StringComparator {

    fun isSameString(context: Context,strId:Int, string: String):Boolean{
        return context.getString(strId) == string
    }
}