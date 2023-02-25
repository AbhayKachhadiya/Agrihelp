package com.example.agrihelp.models

data class ServiceBooking(
    val id: Int = 0,
    val user_id: Int = 0,
    val service_id: Int = 0,
    val name: String = "",
    val village_id: Int = 0,
    val pincode: Int = 0,
    val mobile_no: Int = 0,
    val hours: Int = 0,
    val crop_name: String = ""
)
