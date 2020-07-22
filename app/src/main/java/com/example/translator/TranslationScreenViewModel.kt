package com.example.translator

import androidx.lifecycle.*

class TranslationScreenViewModel: ViewModel() {

    private val repository = TranslationScreenRepository()

    fun getTranslation(): LiveData<String?> {
        return repository.translation
    }

    fun translate(text: String) {
        repository.translate(text)
    }

    fun isDoingApiRequest(): LiveData<Boolean> {
        return repository.isDoingApiRequest()
    }

}