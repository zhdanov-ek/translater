package com.example.translater.api

import com.example.translater.model.TranslationResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("/language/translate/v2")
    fun translate(
        @Field("source") sourceLang: String,
        @Field("q") query: String,
        @Field("target") targetLang: String
    ): Call<TranslationResponse>

}