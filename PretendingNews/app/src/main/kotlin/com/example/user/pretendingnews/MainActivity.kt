package com.example.user.pretendingnews

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.widget.ImageView
import android.widget.RelativeLayout
import com.example.user.pretendingnews.adapter.PretendingMainNewsGridAdapter
import com.example.user.pretendingnews.adapters.NewsAdapter
import com.example.user.pretendingnews.api.APIRequestManager
import com.example.user.pretendingnews.api.APIs
import com.example.user.pretendingnews.extension.applyBoldFont
import com.example.user.pretendingnews.model.PretendingNewsMainModel
import com.example.user.pretendingnews.models.NewsInfoItem
import com.example.user.pretendingnews.service.BackgroundSpeechRecognitionService
import com.google.gson.Gson
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import java.util.ArrayList
import kotlin.concurrent.thread

class MainActivity : PretendingNewsBaseActivity() {
    private val toolbar by lazy { findViewById<Toolbar>(R.id.main_activity_toolbar) }
    private val newsRecyclerView by lazy { findViewById<RecyclerView>(R.id.mainRecyclerView) }
    private val newsHeadlineImageView by lazy { findViewById<ImageView>(R.id.main_activity_main_image) }

    private val items: ArrayList<PretendingNewsMainModel> = ArrayList()
    private val newsListAdapter by lazy { PretendingMainNewsGridAdapter(this, items); }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.applyBoldFont(this)

        title = "뉴스인척 (카테고리 : " + intent.getStringExtra("query") + ")"

        newsRecyclerView.layoutManager = GridLayoutManager(this@MainActivity, 2)
        newsRecyclerView.adapter = newsListAdapter

        newsListAdapter.setOnItemClickListener(object: PretendingMainNewsGridAdapter.ItemClickListener {
            override fun onItemClicked(position: Int) {
                val detailActivity = Intent(this@MainActivity, PretendingNewsDetailActivity::class.java)
                detailActivity.putExtra("link", items[position].link)
                detailActivity.putExtra("originalLink", items[position].originalLink)
                detailActivity.putExtra("imageLink", items[position].imageLink)
                detailActivity.putExtra("title", items[position].title)
                startActivity(detailActivity)
            }
        })

        loadingDialog(true)

        newsHeadlineImageView.setOnClickListener {
            val detailActivity = Intent(this@MainActivity, PretendingNewsDetailActivity::class.java)
            detailActivity.putExtra("link", items[0].link)
            detailActivity.putExtra("originalLink", items[0].originalLink)
            detailActivity.putExtra("imageLink", items[0].imageLink)
            detailActivity.putExtra("title", items[0].title)
            startActivity(detailActivity)
        }

        TedPermission.with(this)
                .setPermissionListener(object: PermissionListener {
                    override fun onPermissionGranted() {
                        startService(Intent(this@MainActivity, BackgroundSpeechRecognitionService::class.java))
                        APIRequestManager.api(APIRequestManager.API.NEWS_DATA_TRUMP)
                                .sendParametersOrJson("100")
                                .sendParametersOrJson(intent.getStringExtra("query"))
                                .addRequestListener(object: APIRequestManager.APIRequestListener {
                                    override fun onFinishRequest(result: APIRequestManager.APIRequest?) {
                                        if(result != null) {
                                            val resultObj = result as APIs.NewsData
                                            var isOK = false

                                            for(x in 0 until resultObj.items!!.size) {
                                                if(!resultObj.items[x].link.startsWith("http://news.naver.com")) {
                                                    continue
                                                } else {
                                                    thread {
                                                        try {
                                                            val newsSite = Jsoup.connect(resultObj.items[x].link).get()
                                                            val meta = newsSite!!.select("meta")
                                                            val imageURL = (0 until meta.size)
                                                                    .firstOrNull { meta[it].attr("property") == "og:image" }
                                                                    ?.let { meta[it].attr("content") }
                                                                    ?: ""

                                                            if(imageURL == "http://static.news.naver.net/image/news/ogtag/navernews_200x200_20160804.png") {
                                                            } else {
                                                                items.add(PretendingNewsMainModel(resultObj.items[x].title, resultObj.items[x].originalLink, resultObj.items[x].link, imageURL))
                                                                runOnUiThread {
                                                                    newsListAdapter.changeApply(items)

                                                                    if(!isOK) {
                                                                        Picasso.with(this@MainActivity).load(items[0].imageLink).fit().centerCrop().memoryPolicy(MemoryPolicy.NO_CACHE).into(newsHeadlineImageView)
                                                                        isOK = true
                                                                    }
                                                                }
                                                            }
                                                        } catch(e: Exception) {

                                                        }
                                                    }

                                                    if(x > 40) {
                                                        loadingDialog(false)
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }).request()
                    }

                    override fun onPermissionDenied(deniedPermissions: ArrayList<String>?) {

                    }
                })
                .setDeniedMessage("권한이 없어요ㅠㅠ")
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .check()
    }
}
