package com.example.agrihelp.services

import com.example.agrihelp.models.User
import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse
import com.google.gson.Gson

class AuthService {
    fun login(user: User): ApiResponse
    {
        return ApiRequest.post(ApiRequest.LOGIN_URL, Gson().toJson(user))
    }

    fun user(user: User): ApiResponse
    {
        return ApiRequest.post(ApiRequest.USER_URL, Gson().toJson(user))
    }
}