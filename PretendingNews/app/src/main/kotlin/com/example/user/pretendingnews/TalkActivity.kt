package com.example.user.pretendingnews

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import qiu.niorgai.StatusBarCompat
import android.widget.RelativeLayout
import android.util.DisplayMetrics
import com.example.user.pretendingnews.service.BackgroundSpeechRecognitionService
import com.igalata.bubblepicker.BubblePickerListener
import com.igalata.bubblepicker.adapter.BubblePickerAdapter
import com.igalata.bubblepicker.model.BubbleGradient
import com.igalata.bubblepicker.model.PickerItem
import com.igalata.bubblepicker.rendering.BubblePicker


/**
 * Created by dsa28s on 05/11/2017.
 */

class TalkActivity: PretendingNewsBaseActivity() {
    private val rootView by lazy { findViewById<RelativeLayout>(R.id.talk_activity_root_view) }
    private val picker by lazy { findViewById<BubblePicker>(R.id.picker) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_talk)

        StatusBarCompat.translucentStatusBar(this@TalkActivity, false)

        val titles = resources.getStringArray(R.array.data)
        val colors = resources.obtainTypedArray(R.array.colors)

        picker.adapter = object : BubblePickerAdapter {

            override val totalCount = titles.size

            override fun getItem(position: Int): PickerItem {
                return PickerItem().apply {
                    title = titles[position]
                    gradient = BubbleGradient(colors.getColor((position * 2) % 8, 0),
                            colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL)
                    textColor = ContextCompat.getColor(this@TalkActivity, android.R.color.white)
                    //backgroundImage = ContextCompat.getDrawable(this@TalkActivity, images.getResourceId(position, 0))
                }
            }
        }

        picker.listener = object : BubblePickerListener {
            override fun onBubbleSelected(item: PickerItem) {
                val mainIntent = Intent(this@TalkActivity, MainActivity::class.java)
                mainIntent.putExtra("query", item.title)
                startActivity(mainIntent)
                finish()

                stopService(Intent(this@TalkActivity, BackgroundSpeechRecognitionService::class.java))
            }

            override fun onBubbleDeselected(item: PickerItem) {

            }
        }
    }

    override fun onBackPressed() {
        //stopService(Intent(this@TalkActivity, BackgroundSpeechRecognitionService::class.java))
        //startService(Intent(this@TalkActivity, BackgroundSpeechRecognitionService::class.java))
        finish()
    }

    override fun onResume() {
        super.onResume()
        picker.onResume()
    }

    override fun onPause() {
        super.onPause()
        picker.onPause()
    }
}