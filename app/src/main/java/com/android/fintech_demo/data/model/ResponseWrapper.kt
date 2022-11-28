package com.android.fintech_demo.data.model

import com.android.fintech_demo.domain.model.CardModel
import retrofit2.Response

data class ResponseWrapper(val users: List<CardDto>)

class NetworkException(val code: Int, message: String?) : Exception(message)

fun Response<ResponseWrapper>.unwrap(): List<CardModel> {
    if (isSuccessful && body() != null) {
        val responseWrapper = body() as ResponseWrapper
        return responseWrapper.users.map(CardDto::toModel)
    } else {
        throw NetworkException(this.code(), this.errorBody().toString())
    }
}