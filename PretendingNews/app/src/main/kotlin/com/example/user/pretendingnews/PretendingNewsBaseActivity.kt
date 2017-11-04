package com.example.user.pretendingnews

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper

/**
 * Created by dsa28s on 04/11/2017.
 */

open class PretendingNewsBaseActivity: AppCompatActivity() {
    private val dialogView by lazy { LayoutInflater.from(this).inflate(R.layout.dialog_loading, null) }
    private lateinit var loadingDialog: Dialog
    private lateinit var loadingText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDialog()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase))
    }

    private fun initDialog() {
        loadingDialog = Dialog(this, R.style.SangsCustomizableDialog)

        loadingDialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        loadingDialog.setContentView(dialogView)
        loadingDialog.setCancelable(false)

        loadingText = dialogView.findViewById(R.id.loading_dialog_text)
        loadingText.visibility = View.GONE

        loadingDialog.window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        loadingDialog.window.setGravity(Gravity.CENTER)
        loadingDialog.window.setDimAmount(0.5.toFloat())
        loadingDialog.setCanceledOnTouchOutside(false)
    }

    @SuppressLint("InflateParams")
    fun loadingDialog(isShow: Boolean) {
        if (isShow) {
            loadingDialog.show()
        } else {
            loadingDialog.dismiss()
        }
    }

    fun loadingText(text: String) {
        if(text == "") {
            loadingText.visibility = View.GONE
        } else {
            loadingText.text = text
            loadingText.visibility = View.VISIBLE
        }
    }
}