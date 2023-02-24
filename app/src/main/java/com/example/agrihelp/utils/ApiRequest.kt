package com.example.agrihelp.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


class ApiRequest
{
    companion object
    {
        @JvmStatic val BASE_URL = "http://192.168.108.14/Agrihelp/api"
        @JvmStatic val LOGIN_URL = "$BASE_URL/login.php"
        @JvmStatic val USER_URL = "$BASE_URL/user.php"
        @JvmStatic val CONTACTUS_URL = "$BASE_URL/contactus.php"
        @JvmStatic val MACHINES_URL = "$BASE_URL/machines.php"
        @JvmStatic val DISTRICTS_URL = "$BASE_URL/district.php"
        @JvmStatic val TALUKAS_URL = "$BASE_URL/taluka.php"
        @JvmStatic val VILLAGES_URL = "$BASE_URL/village.php"
        @JvmStatic val BOOKING_URL = "$BASE_URL/servicebooking.php"


        @JvmStatic
        fun get(url: String): ApiResponse
        {
            return send(Request.Builder().url(url).build())
        }

        @JvmStatic
        fun post(url: String, body: String): ApiResponse
        {
            return send(Request.Builder().url(url).post(body.toRequestBody()).build())
        }

        @JvmStatic
        fun put(url: String, body: String): ApiResponse
        {
            return send(Request.Builder().url(url).put(body.toRequestBody()).build())
        }

        @JvmStatic
        fun delete(url: String): ApiResponse
        {
            return send(Request.Builder().url(url).delete().build())
        }

        @JvmStatic
        private fun send(request: Request): ApiResponse {
            val client = OkHttpClient()
            val response = client.newCall(request).execute()

            return ApiResponse(
                code = response.code,
                message = response.body!!.string()
            )
        }
    }
}