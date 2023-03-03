package com.example.agrihelp.services

import com.example.agrihelp.models.ServiceBooking
import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse
import com.google.gson.Gson

class ServiceBookingService {

    fun serviceBooking(serviceBooking: ServiceBooking): ApiResponse{
        return ApiRequest.post(ApiRequest.BOOKING_URL,Gson().toJson(serviceBooking))
    }

}