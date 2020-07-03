package com.example.translator.translateScreen

import com.example.translator.api.AppRest
import com.example.translator.model.TranslationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TranslateScreenPresenter(private val view: TranslateScreenViewInterface)
    : TranslateScreenPresenterInterface {

    private val SOURCE_LANG = "en"
    private val TARGET_LANG = "ru"


    override fun onTranslateButtonClick(sourceText: String) {
        if (sourceText.trim().length > 1) {
            view.setProgressVisibility(true)
            performTranslate(sourceText)
        }
    }

    private fun performTranslate(text: String) {
        AppRest.api.translate(SOURCE_LANG, text, TARGET_LANG)
            .enqueue(object : Callback<TranslationResponse> {

                override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                    view.setProgressVisibility(false)
                }

                override fun onResponse(call: Call<TranslationResponse>, response: Response<TranslationResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        showTranslation(response.body()!!)
                    }
                    view.setProgressVisibility(false)
                }
            })
    }

    private fun showTranslation(translationResponse: TranslationResponse) {
        var result: String? = null
        translationResponse.data.let {
            if (it!!.translations.isNotEmpty()) {
                val translation = it.translations[0].translatedText
                result = translation
            }
        }

        view.showTranslation(result)
    }
}