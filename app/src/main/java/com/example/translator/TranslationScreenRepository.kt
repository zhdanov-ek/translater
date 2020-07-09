package com.example.translator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.translator.api.AppRest
import com.example.translator.model.TranslationResponse
import retrofit2.Response

class TranslationScreenRepository: ApiResultHandler {

    private val SOURCE_LANG = "en"
    private val TARGET_LANG = "ru"

    var translation: MutableLiveData<String?> = MutableLiveData()
    var doingApiRequest: MutableLiveData<Boolean> = MutableLiveData()

    fun translate(text: String?): MutableLiveData<String?> {
        text?.let { performTranslate(text) }
        return translation
    }

    private fun performTranslate(text: String) {
        doingApiRequest.value = true
        AppRest.api.translate(SOURCE_LANG, text, TARGET_LANG)
            .enqueue(object : BaseApiCallback<TranslationResponse>(this) { })
    }

    override fun onApiRequestError(errorMessage: String?) {
        doingApiRequest.value = false
    }

    fun isDoingApiRequest(): LiveData<Boolean> {
        return doingApiRequest
    }

    override fun onApiRequestSuccess(response: Any?) {
        doingApiRequest.value = false
        if ((response as Response<*>).body() is TranslationResponse) {
            translation.postValue(extractTranslation(response.body() as TranslationResponse))
        }
    }

    private fun extractTranslation(translationResponse: TranslationResponse): String? {
        translationResponse.data.let {
            if (it!!.translations.isNotEmpty()) {
                return it.translations[0].translatedText
            }
        }
        return null
    }
}