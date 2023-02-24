package com.example.agrihelp.services

import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse

class VillagesService {
        fun getVillages(): ApiResponse
        {
            return ApiRequest.get(ApiRequest.VILLAGES_URL)
        }

    fun getVillagesById(id: Int): ApiResponse
    {
        return ApiRequest.get(ApiRequest.VILLAGES_URL.plus("?talukaId=$id"))
    }
}