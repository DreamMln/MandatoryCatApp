package com.example.mandatorycatapp.repository

import com.example.mandatorycatapp.models.Cat
import retrofit2.Call
import retrofit2.http.*

interface CatsService {
    @GET("cats")
    fun getAllCats(): Call<List<Cat>>

    @POST("cats")
    fun addCats(@Body cats: Cat): Call<Cat>

    @DELETE("cats/{id}")
    fun deleteCat(@Path("id") id: Int): Call<Cat>


}