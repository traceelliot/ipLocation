package com.LuisMakeApp.iplocation.network

import com.LuisMakeApp.iplocation.model.IpInfo
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query

//https://api.ipgeolocation.io/ipgeo?apiKey=API_KEY&ip=1.1.1.1&fields=

private const val BASE_URL = "https://api.ipgeolocation.io/"

//Retrofit Builder and convert Json to user object
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL)
    .build()

//Interface which inform how use API
interface RestApiService{
    @GET("ipgeo?apiKey=391354b1f23b449e901af9f882a5ac89&fields=continent_name,country_name,city,zipcode,isp")
    suspend fun getIpInfo(
        @Query("ip")ip: String
    ): IpInfo
}

object RestApi{
    val retrofitService: RestApiService by lazy {
        retrofit.create(RestApiService::class.java)
    }
}

