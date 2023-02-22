package com.example.agrihelp.services

import com.example.agrihelp.models.ContactUs
import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse
import com.google.gson.Gson

class ContactUsService {
    fun contactUs(contactus: ContactUs): ApiResponse
    {
        return ApiRequest.post(ApiRequest.CONTACTUS_URL, Gson().toJson(contactus))
    }
}