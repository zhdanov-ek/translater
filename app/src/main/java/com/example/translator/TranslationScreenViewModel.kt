package com.example.translator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class TranslationScreenViewModel: ViewModel() {

    private var isPerformTranslating: MutableLiveData<Boolean> = MutableLiveData()
    private var sourceText: MutableLiveData<String> = MutableLiveData()
    private val repository = TranslationScreenRepository()

    fun isTranslating(): LiveData<Boolean> {
        return isPerformTranslating
    }

    fun getTranslation(): LiveData<String?> {
        return Transformations.switchMap(sourceText, fun(it: String): MutableLiveData<String?> {
            return repository.translate(it)
        })
    }

    fun translate(text: String) {
        sourceText.value = text
    }

}