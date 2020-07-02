package com.example.translater

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.translater.api.AppRest
import com.example.translater.model.TranslationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val SOURCE_LANG = "en"
    private val TARGET_LANG = "ru"

    private lateinit var inputEditText: EditText
    private lateinit var okButton: Button
    private lateinit var translateTextView: TextView
    private lateinit var progressBar: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.inputEditText)
        okButton = findViewById(R.id.okButton)
        okButton.setOnClickListener(this)
        translateTextView = findViewById(R.id.translateTextView)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun onClick(v: View?) {
        val enteredText = inputEditText.text.toString()
        if (enteredText.trim().length > 1) {
            showProgress(true)
            performTranslate(enteredText)
        }
    }

    private fun performTranslate(text: String) {
        AppRest.api.translate(SOURCE_LANG, text, TARGET_LANG)
            .enqueue(object : Callback<TranslationResponse> {

                override fun onFailure(call: Call<TranslationResponse>, t: Throwable) {
                    showProgress(false)
                }

                override fun onResponse(call: Call<TranslationResponse>, response: Response<TranslationResponse>) {
                    if (response.isSuccessful && response.body() != null) {
                        showTranslation(response.body()!!)
                    }
                    showProgress(false)
                }
            })
    }

    private fun showTranslation(translationResponse: TranslationResponse) {
        translationResponse.data.let {
            if (it!!.translations.isNotEmpty()) {
                val translation = it.translations[0].translatedText
                translateTextView.text = translation
            }
        }

        inputEditText.text = null
    }

    private fun showProgress(show: Boolean) {
        okButton.isEnabled = !show
        progressBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }

}