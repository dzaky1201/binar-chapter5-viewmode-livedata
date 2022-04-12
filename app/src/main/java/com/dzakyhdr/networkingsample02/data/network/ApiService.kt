package com.dzakyhdr.networkingsample02.data.network

import com.dzakyhdr.networkingsample02.data.model.CarResponseItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET(EndPoint.ListCar.GET_LIST_CAR)
    fun getAllCar(): Call<List<CarResponseItem>>

}

object ApiClient {
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(EndPoint.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }

}


object EndPoint {
    const val BASE_URL = "https://rent-cars-api.herokuapp.com"

    object ListCar {
        const val GET_LIST_CAR = "/admin/car"
    }
}
