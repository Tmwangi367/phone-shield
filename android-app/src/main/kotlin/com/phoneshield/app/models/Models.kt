package com.phoneshield.app.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class RegisterRequest(
    val email: String,
    val password: String
)

data class ThreatInfo(
    val packageName: String,
    val threatType: String,
    val severity: String,
    val description: String
)

data class LockedApp(
    val id: Int,
    val packageName: String,
    val appName: String,
    val passwordHash: String,
    val useBiometric: Boolean
)
