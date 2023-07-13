package ru.shtykin.weatherapp.data.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import ru.shtykin.weatherapp.data.model.CurrentWeatherDto


interface ApiService {

    @GET("v1/current.json?key=a407be13669f42cc8e3150053231307&&aqi=no")
    fun getCurrentWeather (@Query("q") city: String): Call<CurrentWeatherDto>

//    suspend fun getCurrentWeather(@Path city: String): Response<CurrentWeatherDto>

//    @GET("v1/current.json?key=a407be13669f42cc8e3150053231307&q=Moscow&aqi=no")
//    suspend fun getCurrentWeather(): Response<CurrentWeatherDto>

//    @GET("/")
//    suspend fun checkServer(): String
//
//    @POST("/admin/login")
//    suspend fun authAdmin(
//        @Body authAdmin: AuthAdminDto
//    ): Response<TokenDto>
//
//    @POST("/admin/users")
//    suspend fun getAllUsers(): Response<List<UserDto>>
//
//    @POST("/admin/user")
//    suspend fun getUser(
//        @Body login: LoginDto
//    ): Response<UserDto>
//
//    @POST("/admin/role")
//    suspend fun setRole(
//        @Body changeRoleDto: ChangeRoleDto
//    ): Response<ChangeRoleDto>
//
//    @POST("/admin/owner")
//    suspend fun addOwner(
//        @Body addOwnerDto: AddOwnerDto
//    ): Response<AddOwnerDto>


}