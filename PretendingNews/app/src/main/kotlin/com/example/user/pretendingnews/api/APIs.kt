package com.example.user.pretendingnews.api

import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.http.*

interface APIs {
    @GET("search/news/")
    fun getNews(@Query("display") search: String, @Query("query") query: String): Call<NewsData>

    class NewsData: APIRequestManager.APIRequest {
        @SerializedName("items") val items: List<Item>? = null

        class Item: APIRequestManager.APIRequest {
            @SerializedName("title") val title = ""
            @SerializedName("originallink") val originalLink = ""
            @SerializedName("link") val link = ""
            @SerializedName("description") val description = ""
            @SerializedName("pubDate") val pubDate = ""
        }
    }
}