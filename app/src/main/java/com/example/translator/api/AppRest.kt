package com.example.translator.api

object AppRest {
    private val BASE_URL = "https://google-translate1.p.rapidapi.com"

    private var apiFactory: ApiFactory = ApiFactory()

    val api: ApiService by lazy {
        return@lazy apiFactory
            .buildRetrofit(BASE_URL)
            .create(ApiService::class.java)
    }
}