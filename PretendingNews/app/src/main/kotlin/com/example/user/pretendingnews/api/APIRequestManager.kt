package com.example.user.pretendingnews.api

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.util.Log
import okhttp3.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class APIRequestManager {
    enum class API {
        NEWS_DATA_TRUMP
    }

    interface APIRequestListener {
        fun onFinishRequest(result: APIRequest?)
    }

    private val BASE_URL = "http://13.124.104.217:5000/"

    private lateinit var apiRequestURL: API
    private var requestModels = ArrayList<Any>()
    private lateinit var requestListener: APIRequestListener

    private constructor(s: API) {
        apiRequestURL = s
    }

    companion object {
        fun api(api: API): APIRequestManager {
            return APIRequestManager(api)
        }
    }

    fun sendParametersOrJson(obj: Any): APIRequestManager {
        requestModels.add(obj)
        return this
    }

    fun addRequestListener(listener: APIRequestListener): APIRequestManager {
        requestListener = listener
        return this
    }

    fun request() {
        /*val mSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_1, TlsVersion.TLS_1_2, TlsVersion.TLS_1_3)
                .cipherSuites(
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA,
                        CipherSuite.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_ECDHE_RSA_WITH_RC4_128_SHA,
                        CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA).build()*/

        //val interceptor = HttpLoggingInterceptor()
        //interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
                //.connectionSpecs(Collections.singletonList(mSpec))
                /*.addInterceptor { chain ->
                    val original = chain.request()

                    val request = original.newBuilder()

                    for(x in 0 until headers.size) {
                        request.addHeader(headers[x].name, headers[x].value)
                    }

                    request.method(original.method(), original.body())

                    chain.proceed(request.build())
                }.addInterceptor(interceptor)
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)*/

        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

        when (apiRequestURL) {
            API.NEWS_DATA_TRUMP -> {
                val api = retrofit.create(APIs::class.java)
                RequestAPITask(requestListener).execute(api.getNews(requestModels[0] as String, requestModels[1] as String) as Call<Any>)
            }
        }
    }

    interface APIRequest {
        /* THIS ONLY API REQUEST MODEL */
    }

    @SuppressLint("StaticFieldLeak")
    internal class RequestAPITask(listener: APIRequestListener) : AsyncTask<retrofit2.Call<Any>, Any, Any>() {
        private var requestListener: APIRequestListener = listener

        override fun doInBackground(vararg p0: Call<Any>?): Any? {
            val call = p0[0]
            val response = call!!.execute()

            return response.body()
        }

        override fun onPostExecute(result: Any?) {
            requestListener.onFinishRequest(result as APIRequest)
        }
    }
}