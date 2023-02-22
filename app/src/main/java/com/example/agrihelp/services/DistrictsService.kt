package com.example.agrihelp.services

import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse

class DistrictsService {
    fun getDistricts(): ApiResponse
    {
        return ApiRequest.get(ApiRequest.DISTRICTS_URL)
    }
}