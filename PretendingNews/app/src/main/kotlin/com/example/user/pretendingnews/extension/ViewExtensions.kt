package com.example.user.pretendingnews.extension

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.Typeface
import android.os.Handler
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast


fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.dimen(dp: Int): Float {
    return resources.getDimension(dp)
}

fun Toolbar.applyBoldFont(context: Activity) {
    val toolbar = this
    for (i in 0 until toolbar.childCount) {
        val view = toolbar.getChildAt(i)
        if (view is TextView) {
            with(view) {
                if (text == toolbar.title) {
                    //tv.typeface = titleFont
                    textSize = convertIntegerToPx(context.resources, 17.5.toFloat())
                    setTypeface(typeface, Typeface.BOLD)
                }
            }
            if(view == toolbar.title) {
                break
            }
        }
    }
}

fun CollapsingToolbarLayout.applyBoldFont(context: Activity) {
    val toolbar = this
    for (i in 0 until toolbar.childCount) {
        val view = toolbar.getChildAt(i)
        if (view is TextView) {
            with(view) {
                if (text == toolbar.title) {
                    //tv.typeface = titleFont
                    textSize = convertIntegerToPx(context.resources, 17.5.toFloat())
                    setTypeface(typeface, Typeface.BOLD)
                }
            }
            if(view == toolbar.title) {
                break
            }
        }
    }
}

fun TextView.applyBoldFont(context: Activity) {
    val titleFont = Typeface.createFromAsset(context.assets, "fonts/NotoSansKR-Bold-Hestia.otf")
    setTypeface(titleFont, Typeface.BOLD)
}

private fun getDeepChildOffset(mainParent: ViewGroup, parent: ViewParent, child: View, accumulatedOffset: Point) {
    val parentGroup = parent as ViewGroup
    accumulatedOffset.x += child.left
    accumulatedOffset.y += child.top
    if (parentGroup == mainParent) {
        return
    }
    getDeepChildOffset(mainParent, parentGroup.parent, parentGroup, accumulatedOffset)
}

fun scrollToView(scrollViewParent: ScrollView, view: View) {
    val childOffset = Point()
    getDeepChildOffset(scrollViewParent, view.parent, view, childOffset)
    val y = childOffset.y
    Log.e("scrollToView", "$y")
    // Scroll to child.
    Handler().postDelayed({
        scrollViewParent.smoothScrollTo(0, y)
    }, 500)
}

fun hideSoftKeyboard(rootView: View) {
    val imm = rootView.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(rootView.windowToken, 0)
}

fun showSoftKeyboard(context: Context) {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    Handler().postDelayed({
        //imm.showSoftInput(rootView, InputMethodManager.SHOW_IMPLICIT)
        imm.toggleSoftInput(0, 0)
    }, 700)
}