package com.example.translator

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

abstract class BaseApiCallback<T>(private val apiResultHandler: ApiResultHandler): Callback<T> {

    override fun onFailure(call: Call<T>, t: Throwable) {
        apiResultHandler.onApiRequestError(t.message)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        apiResultHandler.onApiRequestSuccess(response)
    }
}