package com.example.agrihelp.services

import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse

class OperatorService {
    fun getOperatorById(talukaId: Int, machineId:Int): ApiResponse
    {
        return ApiRequest.get(ApiRequest.OPERATORS_URL.plus("?talukaId=$talukaId&machineId=$machineId"))
    }
}