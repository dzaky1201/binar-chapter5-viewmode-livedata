package com.dzakyhdr.networkingsample02.data.network

import com.dzakyhdr.networkingsample02.data.auth.LoginRequest
import com.dzakyhdr.networkingsample02.data.model.CarResponseItem
import com.dzakyhdr.networkingsample02.data.model.RegisterResponseItem
import com.dzakyhdr.networkingsample02.data.auth.RegisterRequest
import com.dzakyhdr.networkingsample02.data.model.LoginResponseItem
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import java.util.concurrent.TimeUnit


interface ApiService {

    @GET(EndPoint.ListCar.GET_LIST_CAR)
    fun getAllCar(): Call<List<CarResponseItem>>

    @POST(EndPoint.Register.REGISTER_USER)
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponseItem>


    @POST(EndPoint.Login.LOGIN_USER)
    fun loginUser(@Body request: LoginRequest): Call<LoginResponseItem>

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

    object Register {
        const val REGISTER_USER = "/admin/auth/register"
    }

    object Login {
        const val LOGIN_USER = "/admin/auth/login"
    }
}
