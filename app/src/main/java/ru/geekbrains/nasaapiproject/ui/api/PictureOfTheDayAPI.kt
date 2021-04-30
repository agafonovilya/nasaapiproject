package ru.geekbrains.nasaapiproject.ui.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String): Call<PodServerResponseData>

    @GET("planetary/apod")
    fun getSomeQuantityOfPictures(@Query("api_key") apiKey: String,
                                  @Query("count") quantity: Int)
                                    : Call<ArrayList<PodServerResponseData>>
}