package com.example.user.pretendingnews

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import qiu.niorgai.StatusBarCompat

/**
 * Created by user on 2017-11-04.
 */

class SplashActivity : PretendingNewsBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        StatusBarCompat.translucentStatusBar(this@SplashActivity, false)

        Handler().postDelayed({
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 7500)
    }
}
