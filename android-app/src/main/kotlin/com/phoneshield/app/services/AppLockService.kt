package com.phoneshield.app.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.phoneshield.app.database.AppDatabase
import com.phoneshield.app.database.LockedAppEntity
import kotlinx.coroutines.*

class AppLockService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.Main + Job())
    private lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)
        Log.d("AppLockService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AppLockService", "Service started")

        serviceScope.launch {
            monitorLockedApps()
        }

        return START_STICKY
    }

    private suspend fun monitorLockedApps() {
        val lockedApps = database.lockedAppDao().getAllLockedApps()
        Log.d("AppLockService", "Monitoring ${lockedApps.size} locked apps")
        
        // TODO: Implement app lock logic
        // - Monitor app launches
        // - Show lock screen for protected apps
        // - Verify biometric/password authentication
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        Log.d("AppLockService", "Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
