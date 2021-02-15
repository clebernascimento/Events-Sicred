package com.institutotransire.events.util

import android.content.res.Resources

object ScreenWidth {
    val screenWidth: Int
        get() = Resources.getSystem().displayMetrics.widthPixels
}