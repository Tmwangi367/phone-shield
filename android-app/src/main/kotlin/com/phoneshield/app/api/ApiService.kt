package com.phoneshield.app.api

import com.phoneshield.app.models.LoginRequest
import com.phoneshield.app.models.RegisterRequest
import com.phoneshield.app.ui.auth.LoginResponse
import com.phoneshield.app.ui.auth.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @POST("/api/auth/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/api/auth/register")
    fun register(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/api/auth/logout")
    fun logout(@Header("Authorization") token: String): Call<LogoutResponse>

    @GET("/api/user/profile")
    fun getUserProfile(@Header("Authorization") token: String): Call<UserProfile>

    @POST("/api/antivirus/scan")
    fun startAntivirusScan(@Header("Authorization") token: String): Call<ScanResponse>

    @GET("/api/antivirus/results")
    fun getScanResults(@Header("Authorization") token: String): Call<ScanResultsResponse>
}

data class LogoutResponse(
    val message: String
)

data class UserProfile(
    val userId: String,
    val email: String,
    val createdAt: String
)

data class ScanResponse(
    val scanId: String,
    val status: String
)

data class ScanResultsResponse(
    val threats: List<ThreatData>,
    val scanDate: String
)

data class ThreatData(
    val id: String,
    val name: String,
    val type: String,
    val severity: String
)
