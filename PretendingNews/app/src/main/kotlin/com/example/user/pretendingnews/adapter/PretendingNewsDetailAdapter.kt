package com.example.user.pretendingnews.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.example.user.pretendingnews.R
import com.example.user.pretendingnews.model.PretendingNewsDetailModel

/**
 * Created by dsa28s on 05/11/2017.
 */

class PretendingNewsDetailAdapter(c: Activity, nList: MutableList<PretendingNewsDetailModel.NewsDetailModel>): RecyclerView.Adapter<PretendingNewsDetailAdapter.PretendingNewsDetailViewHolder>() {
    private val context = c
    private var detailModelList = nList

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): PretendingNewsDetailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_detail_list, parent, false)
        return PretendingNewsDetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: PretendingNewsDetailViewHolder?, position: Int) {
        with(holder!!) {
            when {
                detailModelList[position] is PretendingNewsDetailModel.TitleModel -> {
                    titleContainer.visibility = View.VISIBLE
                    summaryContainer.visibility = View.GONE
                    gongamContainer.visibility = View.GONE

                    titleText.text = (detailModelList[position] as PretendingNewsDetailModel.TitleModel).title
                }
                detailModelList[position] is PretendingNewsDetailModel.SummaryModel -> {
                    titleContainer.visibility = View.GONE
                    summaryContainer.visibility = View.VISIBLE
                    gongamContainer.visibility = View.GONE

                    summaryText.text = (detailModelList[position] as PretendingNewsDetailModel.SummaryModel).summary
                }
                detailModelList[position] is PretendingNewsDetailModel.GonggamModel -> {
                    titleContainer.visibility = View.GONE
                    summaryContainer.visibility = View.GONE
                    gongamContainer.visibility = View.VISIBLE


                }
            }
        }
    }

    override fun getItemCount(): Int {
        return detailModelList.size
    }

    fun changeApply(list: ArrayList<PretendingNewsDetailModel.NewsDetailModel>) {
        detailModelList = list
        notifyDataSetChanged()
    }

    inner class PretendingNewsDetailViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val titleContainer = itemView.findViewById<RelativeLayout>(R.id.detail_list_item_title_container)!!
        val titleText = itemView.findViewById<TextView>(R.id.detail_list_item_title)!!

        val summaryContainer = itemView.findViewById<RelativeLayout>(R.id.detail_list_item_summary_container)!!
        val summaryText = itemView.findViewById<TextView>(R.id.detail_list_item_summary_text)!!

        val gongamContainer = itemView.findViewById<LinearLayout>(R.id.detail_list_item_gonggam_container)!!
        val gongamLikeButtonContainer = itemView.findViewById<RelativeLayout>(R.id.detail_list_like_button_container)!!
        val gongamHateButtonContainer = itemView.findViewById<RelativeLayout>(R.id.detail_list_sad_button_container)!!
        val gongamAngryButtonContainer = itemView.findViewById<RelativeLayout>(R.id.detail_list_angry_button_container)!!
    }
}