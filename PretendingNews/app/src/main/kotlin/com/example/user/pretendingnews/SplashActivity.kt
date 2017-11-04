package com.example.user.pretendingnews

import android.content.Intent
import android.os.Bundle
import android.os.Handler
<<<<<<< HEAD
=======
import android.support.v7.app.AppCompatActivity
import com.example.user.pretendingnews.activities.Main2Activity
>>>>>>> refs/remotes/origin/master
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
<<<<<<< HEAD
            val intent = Intent(this@SplashActivity, PretendingNewsDetailActivity::class.java)
=======
            val intent = Intent(this@SplashActivity, Main2Activity::class.java)
>>>>>>> refs/remotes/origin/master
            startActivity(intent)
            finish()
        }, 2500)
    }
}
