package com.phoneshield.app.ui.dashboard

import android.os.Bundle
import android.content.Intent
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.phoneshield.app.R
import com.phoneshield.app.ui.auth.LoginActivity
import com.phoneshield.app.ui.lock.AppLockActivity
import com.phoneshield.app.ui.antivirus.AntivirusActivity
import com.phoneshield.app.ui.cleanup.CleanupActivity
import com.phoneshield.app.utils.SessionManager

class DashboardActivity : AppCompatActivity() {

    private lateinit var sessionManager: SessionManager
    private lateinit var userEmailTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        sessionManager = SessionManager(this)

        // Initialize views
        userEmailTextView = findViewById(R.id.userEmailTextView)
        val appLockButton: Button = findViewById(R.id.appLockButton)
        val antivirusButton: Button = findViewById(R.id.antivirusButton)
        val cleanupButton: Button = findViewById(R.id.cleanupButton)
        val logoutButton: Button = findViewById(R.id.logoutButton)

        // Display user email
        val userEmail = sessionManager.getUserEmail()
        userEmailTextView.text = "Welcome, $userEmail"

        // App Lock button
        appLockButton.setOnClickListener {
            startActivity(Intent(this, AppLockActivity::class.java))
        }

        // Antivirus button
        antivirusButton.setOnClickListener {
            startActivity(Intent(this, AntivirusActivity::class.java))
        }

        // Cleanup button
        cleanupButton.setOnClickListener {
            startActivity(Intent(this, CleanupActivity::class.java))
        }

        // Logout button
        logoutButton.setOnClickListener {
            sessionManager.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
