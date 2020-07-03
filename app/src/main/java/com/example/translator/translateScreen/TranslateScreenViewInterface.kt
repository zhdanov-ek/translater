package com.example.translator.translateScreen

interface TranslateScreenViewInterface {
    fun showTranslation(translation: String?)
    fun clearEnteredText()
    fun setProgressVisibility(visible: Boolean);
}