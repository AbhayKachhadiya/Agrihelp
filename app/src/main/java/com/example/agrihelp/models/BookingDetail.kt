package com.example.agrihelp.models

data class BookingDetail(
    val id:Int = 0,
    val service_name:String = "",
    val actualHours:Int = 0,
    val bookingDate:String = "",
    val cropName:String = "",
    val status:Int = 0
)