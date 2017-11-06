package com.example.user.pretendingnews

import android.app.Dialog
import android.graphics.Typeface
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.user.pretendingnews.adapter.PretendingNewsDetailAdapter
import com.example.user.pretendingnews.extension.applyBoldFont
import com.example.user.pretendingnews.extension.getStatusBarHeight
import com.example.user.pretendingnews.helper.CollapsingToolbarEventHelperListener
import com.example.user.pretendingnews.model.PretendingNewsDetailModel
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import com.thefinestartist.finestwebview.FinestWebView
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import qiu.niorgai.StatusBarCompat

/**
 * Created by dsa28s on 05/11/2017.
 */

class PretendingNewsDetailActivity: PretendingNewsBaseActivity() {
    private val appbar by lazy { findViewById<AppBarLayout>(R.id.detail_activity_appbar) }
    private val toolbar by lazy { findViewById<Toolbar>(R.id.detail_activity_toolbar) }
    private val collapseToolbar by lazy { findViewById<CollapsingToolbarLayout>(R.id.detail_activity_collapsing_toolbar) }
    private val actionbarBackground by lazy { findViewById<ImageView>(R.id.detail_activity_background_view) }
    private val detailList by lazy { findViewById<RecyclerView>(R.id.detail_activity_listview) }
    private val urlFab by lazy { findViewById<FloatingActionButton>(R.id.detail_activity_web_fab) }

    private val detailListObj = ArrayList<PretendingNewsDetailModel.NewsDetailModel>()
    private val detailListAdapter by lazy { PretendingNewsDetailAdapter(this, detailListObj) }

    private var link = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        detailList.layoutManager = LinearLayoutManager(this@PretendingNewsDetailActivity)
        detailList.adapter = detailListAdapter
        collapseToolbar.title = ""
        title = ""

        toolbar.applyBoldFont(this)
        collapseToolbar.applyBoldFont(this)
        collapseToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar)
        collapseToolbar.setCollapsedTitleTypeface(Typeface.createFromAsset(assets, "fonts/NotoSansKR-Bold-Hestia.otf"))

        link = intent.getStringExtra("link")

        urlFab.setOnClickListener {
            FinestWebView.Builder(this@PretendingNewsDetailActivity).showSwipeRefreshLayout(false).show(link)
        }

        appbar.addOnOffsetChangedListener(object: CollapsingToolbarEventHelperListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                if(state == State.EXPANDED) {
                    StatusBarCompat.translucentStatusBar(this@PretendingNewsDetailActivity, false)
                    collapseToolbar.title = ""
                    //setTitlebarBackButtonCustom(R.drawable.ic_arrow_white)
                } else if(state == State.COLLAPSED) {
                    StatusBarCompat.setStatusBarColor(this@PretendingNewsDetailActivity, ContextCompat.getColor(this@PretendingNewsDetailActivity, R.color.colorAccent))
                    collapseToolbar.title = intent.getStringExtra("title")
                    //setTitlebarBackButtonCustom(R.drawable.ic_arrow_primary)
                } else {
                    collapseToolbar.title = ""
                }
            }
        })

        val param = toolbar.layoutParams as ViewGroup.MarginLayoutParams
        param.topMargin = getStatusBarHeight(this)
        toolbar.requestLayout()

        CheckExistMainImage().execute()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item!!.itemId) {
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    inner class CheckExistMainImage: AsyncTask<Void, Void, Void>() {
        var newsSite: Document? = null

        override fun onPreExecute() {
            loadingDialog(true)
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            newsSite = Jsoup.connect(link).get()

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