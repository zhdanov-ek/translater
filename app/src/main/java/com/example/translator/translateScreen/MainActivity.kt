package com.example.translator.translateScreen

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.translator.R

class MainActivity : AppCompatActivity(), View.OnClickListener, TranslateScreenViewInterface {

    private lateinit var inputEditText: EditText
    private lateinit var okButton: Button
    private lateinit var translateTextView: TextView
    private lateinit var progressBar: View
    private val presenter: TranslateScreenPresenter = TranslateScreenPresenter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.inputEditText)
        okButton = findViewById(R.id.okButton)
        okButton.setOnClickListener(this)
        translateTextView = findViewById(R.id.translateTextView)
        progressBar = findViewById(R.id.progressBar)
    }

    override fun showTranslation(translation: String?) {
        translateTextView.text = translation
    }

    override fun clearEnteredText() {
        inputEditText.text = null
    }

    override fun setProgressVisibility(visible: Boolean) {
        okButton.isEnabled = !visible
        progressBar.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    override fun onClick(v: View?) {
        val enteredText = inputEditText.text.toString()
        presenter.onTranslateButtonClick(enteredText)
    }

}