package com.flexcode.authenticationapp.domain.repository


import com.flexcode.authenticationapp.data.remote.ApiService
import com.flexcode.authenticationapp.util.Resource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FPLrepository(private val apiService: ApiService) {
    suspend fun login(username: String, password: String): Resource<Boolean> {
        return try {
            val response = apiService.login(username, password)
            if (response.success) {
//                val sessionCookie = response.redirect_url?.substringAfter("session=").orEmpty()
                // Add session cookie to OkHttpClient instance to authenticate subsequent requests
                Resource.Success(true)
            } else {
                Resource.Error("Login failed")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Unknown error")
        }
    }

}
