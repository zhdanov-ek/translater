package com.example.translator

interface ApiResultHandler {
    fun onApiRequestError(errorMessage: String?)
    fun onApiRequestSuccess(response: Any?)
}