package com.example.user.pretendingnews.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.user.pretendingnews.R
import com.example.user.pretendingnews.model.PretendingNewsDetailModel
import com.example.user.pretendingnews.model.PretendingNewsMainModel
import com.squareup.picasso.Picasso

/**
 * Created by dsa28s on 05/11/2017.
 */

class PretendingMainNewsGridAdapter(c: Activity, mList: MutableList<PretendingNewsMainModel>): RecyclerView.Adapter<PretendingMainNewsGridAdapter.PretendingNewsMainViewHolder>() {
    private val context = c
    private var mainModelList = mList
    private var clickListener: ItemClickListener? = null

    interface ItemClickListener {
        fun onItemClicked(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PretendingNewsMainViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_main_news, parent, false)
        return PretendingNewsMainViewHolder(view)
    }

    override fun onBindViewHolder(holder: PretendingNewsMainViewHolder?, position: Int) {
        try {
            with(holder!!) {
                Picasso.with(context).load(mainModelList[position + 1].imageLink).fit().centerCrop().into(image)

                if(clickListener != null) {
                    imageContainer.setOnClickListener {
                        clickListener!!.onItemClicked(position + 1)
                    }
                }
            }
        } catch(e: Exception) {

        }
    }

    override fun getItemCount(): Int {
        return mainModelList.size
    }

    fun setOnItemClickListener(listener: ItemClickListener) {
        clickListener = listener
    }

    fun changeApply(list: ArrayList<PretendingNewsMainModel>) {
        mainModelList = list
        notifyDataSetChanged()
    }

    inner class PretendingNewsMainViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageContainer = itemView.findViewById<RelativeLayout>(R.id.main_news_item_container)!!
        val image = itemView.findViewById<ImageView>(R.id.titleImage)!!
    }
}