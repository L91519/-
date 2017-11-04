package com.example.user.pretendingnews.network;

import android.content.Context;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by user on 2017-11-05.
 */

public class Service extends APIAdapter{

    public static RestApi getRetrofit (Context context) {
        return (RestApi) retrofit(context, RestApi.class);
    }

    public interface RestApi {
        @GET("search/news?display=100")
        Call<JsonObject> getInfo();
    }
}
