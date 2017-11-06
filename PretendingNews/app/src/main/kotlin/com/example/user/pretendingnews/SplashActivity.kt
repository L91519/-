package com.example.user.pretendingnews

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.example.user.pretendingnews.activities.Main2Activity
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
            intent.putExtra("query", "트럼프")
            startActivity(intent)
            finish()
        }, 2500)
    }
}
