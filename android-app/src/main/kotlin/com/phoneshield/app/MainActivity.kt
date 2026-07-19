package com.phoneshield.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import com.phoneshield.app.ui.auth.LoginActivity
import com.phoneshield.app.ui.dashboard.DashboardActivity
import com.phoneshield.app.utils.SessionManager

class MainActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        sessionManager = SessionManager(this)

        // Check if user is logged in
        if (sessionManager.isLoggedIn()) {
            // Navigate to Dashboard
            startActivity(Intent(this, DashboardActivity::class.java))
        } else {
            // Navigate to Login
            startActivity(Intent(this, LoginActivity::class.java))
        }

        // Close MainActivity
        finish()
    }
}
