package com.pusher.pusherchat

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.POST
import retrofit2.http.Body

interface ChatService {
    @POST("/message")
    fun postMessage(@Body body:Message): Call<Void>

    companion object {
        private const val BASE_URL = "http://10.0.2.2:8080/"

        fun create(): ChatService {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()
            return retrofit.create(ChatService::class.java)
        }
    }
}