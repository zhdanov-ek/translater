package com.example.translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.translator.api.AppRest
import com.example.translator.model.TranslationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var inputEditText: EditText
    private lateinit var okButton: Button
    private lateinit var translateTextView: TextView
    private lateinit var progressBar: View
    private val viewModel = TranslationScreenViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        inputEditText = findViewById(R.id.inputEditText)
        okButton = findViewById(R.id.okButton)
        okButton.setOnClickListener(this)
        translateTextView = findViewById(R.id.translateTextView)
        progressBar = findViewById(R.id.progressBar)

        viewModel.getTranslation().observe(this,
            Observer {
                translateTextView.text = it
            })

        viewModel.isTranslating().observe(this,
            Observer { showProgress(it)})
    }

    override fun onClick(v: View?) {
        val enteredText = inputEditText.text.toString()
        if (enteredText.trim().length > 1) {
            viewModel.translate(enteredText)
        }
    }

    private fun showProgress(show: Boolean) {
        okButton.isEnabled = !show
        progressBar.visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
}