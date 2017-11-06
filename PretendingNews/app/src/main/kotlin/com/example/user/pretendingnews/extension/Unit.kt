package com.example.user.pretendingnews.extension

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

fun convertIntegerToPx(res: Resources, int: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, int, res.displayMetrics)
}

fun convertIntegerToDp(res: Resources, int: Float): Float {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, int, res.displayMetrics)
}

fun getMaxWidthDP(resources: Resources): Float {
    val displayMetrics = resources.displayMetrics
    return displayMetrics.widthPixels / displayMetrics.density
}

fun getStatusBarHeight(context: Context): Int {
    var result = 0
    val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        result = context.resources.getDimensionPixelSize(resourceId)
    }
    return result
}