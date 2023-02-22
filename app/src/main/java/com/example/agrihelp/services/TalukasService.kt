package com.example.agrihelp.services

import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse

class TalukasService {
    fun getTalukas(): ApiResponse
    {
        return ApiRequest.get(ApiRequest.TALUKAS_URL)
    }
}