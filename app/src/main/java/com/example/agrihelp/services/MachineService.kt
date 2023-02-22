package com.example.agrihelp.services

import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse

class MachineService {
    fun getMachine(): ApiResponse
    {
        return ApiRequest.get(ApiRequest.MACHINES_URL)
    }
}