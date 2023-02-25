package com.example.agrihelp.services

import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse
import com.google.gson.Gson

class ServiceBookingService {

    fun ServiceBooking(serviceBooking : ServiceBookingService): ApiResponse{
        return ApiRequest.post(ApiRequest.BOOKING_URL,Gson().toJson(serviceBooking))
    }

}