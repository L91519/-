package com.example.user.pretendingnews

import android.app.Dialog
import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.user.pretendingnews.adapter.PretendingNewsDetailAdapter
import com.example.user.pretendingnews.model.PretendingNewsDetailModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import qiu.niorgai.StatusBarCompat

/**
 * Created by dsa28s on 05/11/2017.
 */

class PretendingNewsDetailActivity: PretendingNewsBaseActivity() {
    private val actionbarBackground by lazy { findViewById<ImageView>(R.id.detail_activity_background_view) }
    private val detailList by lazy { findViewById<RecyclerView>(R.id.detail_activity_listview) }

    private val detailListObj = ArrayList<PretendingNewsDetailModel.NewsDetailModel>()
    private val detailListAdapter by lazy { PretendingNewsDetailAdapter(this, detailListObj) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailList.layoutManager = LinearLayoutManager(this@PretendingNewsDetailActivity)
        detailList.adapter = detailListAdapter

        CheckExistMainImage().execute()
    }

    inner class CheckExistMainImage: AsyncTask<Void, Void, Void>() {
        var newsSite: Document? = null

        override fun onPreExecute() {
            loadingDialog(true)
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            newsSite = Jsoup.connect("http://news.naver.com/main/read.nhn?mode=LSD&mid=sec&sid1=104&oid=214&aid=0000788684").get()

            return null
        }

        override fun onPostExecute(result: Void?) {
            val elements = newsSite!!.select("span[class=end_photo_org]")

            /*if(elements.size == 0) {
                val thumbnailImage = newsSite!!.getElementsByClass("u_thumb_img")

                Picasso.with(this@PretendingNewsDetailActivity).load(thumbnailImage[0].getElementsByTag("img").attr("data-src")).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE).into(actionbarBackground)
            } else {
                for(x in 0 until elements.size) {
                    Log.e("TEST", elements[x].getElementsByTag("img").attr("src"))
                    Picasso.with(this@PretendingNewsDetailActivity).load(elements[x].getElementsByTag("img").attr("src")).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE).into(actionbarBackground)
                }
            }*/

            val meta = newsSite!!.select("meta")
            val imageURL = (0 until meta.size)
                    .firstOrNull { meta[it].attr("property") == "og:image" }
                    ?.let { meta[it].attr("content") }
                    ?: ""

            Picasso.with(this@PretendingNewsDetailActivity).load(imageURL).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE).into(actionbarBackground)

            detailListObj.add(PretendingNewsDetailModel.TitleModel(newsSite!!.getElementById("articleTitle").text()))
            Log.e("TEST", newsSite!!.getElementById("articleBodyContents").text())
            detailListObj.add(PretendingNewsDetailModel.SummaryModel(newsSite!!.getElementById("articleBodyContents").text().trim()))
            detailListObj.add(PretendingNewsDetailModel.GonggamModel(0, 0, 0))

            detailListAdapter.changeApply(detailListObj)

            loadingDialog(false)
            StatusBarCompat.translucentStatusBar(this@PretendingNewsDetailActivity, false)
        }
    }
}