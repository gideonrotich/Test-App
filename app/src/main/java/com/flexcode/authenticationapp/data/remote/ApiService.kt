package com.flexcode.authenticationapp.data.remote

import com.flexcode.authenticationapp.data.remote.request.AuthRequest
import com.flexcode.authenticationapp.data.remote.response.AuthResponse
import com.flexcode.authenticationapp.data.remote.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {


    @FormUrlEncoded
    @POST("accounts/login/")
    @Headers(
        "User-Agent: Dalvik"
    )
    suspend fun login(

        @Field("login") login: String,
        @Field("password") password: String,
        @Field("redirect_uri") redirect_uri: String = "https://fantasy.premierleague.com/a/login",
        @Field("app") app: String = "plfpl-web"
    ) : LoginResponse


    @POST("api/register")
    suspend fun registerUser(
        @Body registerRequest: AuthRequest
    ) : AuthResponse

    @GET("api/user/{id}")
    suspend fun getUserDetails()

}