package com.example.user.pretendingnews

import android.app.Application
import uk.co.chrisjenx.calligraphy.CalligraphyConfig



/**
 * Created by dsa28s on 04/11/2017.
 */

class PretendingNewsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NotoSansKR-Regular-Hestia.otf")
                .build()
        )
    }
}