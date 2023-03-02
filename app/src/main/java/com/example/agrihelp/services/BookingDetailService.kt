package com.example.agrihelp.services

import com.example.agrihelp.utils.ApiRequest
import com.example.agrihelp.utils.ApiResponse

class BookingDetailService{
    fun getBookingDetails(userid:Int):ApiResponse{
        return ApiRequest.get(ApiRequest.BOOKING_URL.plus("?user_id=$userid"))
    }

}