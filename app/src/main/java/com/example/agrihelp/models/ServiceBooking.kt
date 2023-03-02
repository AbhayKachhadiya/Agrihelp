package com.example.agrihelp.models

data class ServiceBooking(
    val id:Int = 0,
    val user_id: Int = 0,
    val service_id: Int = 0,
    val operator_id:Int = 0,
    val name: String = "",
    val village_id: Int = 0,
    val pincode: Int = 0,
    val mobile_no: String = "",
    val hours: Int = 0,
    val date: String = "",
    val crop_name: String = "",
    val status:Int = 0
)
