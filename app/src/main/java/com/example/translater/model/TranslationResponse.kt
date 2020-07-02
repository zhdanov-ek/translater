package com.example.translater.model

import com.google.gson.annotations.SerializedName

class TranslationResponse {
    @SerializedName("data")
    var data: Data? = null

    data class Data(
        @SerializedName("translations")
        val translations: List<Translation>
    )

    data class Translation(
        @SerializedName("translatedText")
        val translatedText: String
    )
}