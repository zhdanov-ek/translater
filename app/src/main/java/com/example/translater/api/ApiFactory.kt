package com.example.translater.api

import android.util.Log
import com.example.translater.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiFactory {

    private var builder: OkHttpClient.Builder? = null

    private fun getClient(): OkHttpClient {
        if (builder == null) {
            builder = OkHttpClient.Builder()
            builder?.connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
            builder?.writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            builder?.readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)

            builder?.addNetworkInterceptor(StethoInterceptor())

            builder?.addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    Log.d(TAG, "addInterceptor")

                    val original = chain.request()
                    val requestBuilder = original.newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("x-rapidapi-host", "google-translate1.p.rapidapi.com")
                        .addHeader("x-rapidapi-key", BuildConfig.X_RAPIDAPI_KEY)
                        .addHeader("accept-encoding", "application/gzip")
                        .header("platform", "android")
                        .method(original.method, original.body)

                    val request = requestBuilder
                        .cacheControl(CacheControl.Builder().noCache().build())
                        .build()

                    return chain.proceed(request)
                }
            })
        }

        return builder!!.build()
    }

    fun buildRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        var TAG: String = ApiFactory::class.java.simpleName

        private const val DEFAULT_TIMEOUT = 15
        const val CONNECT_TIMEOUT = DEFAULT_TIMEOUT
        const val WRITE_TIMEOUT = DEFAULT_TIMEOUT
        const val READ_TIMEOUT = DEFAULT_TIMEOUT
    }
}