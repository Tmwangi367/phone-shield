package com.phoneshield.app.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.phoneshield.app.utils.FileCleanupManager
import kotlinx.coroutines.*

class CleanupService : Service() {

    private val serviceScope = CoroutineScope(Dispatchers.IO + Job())
    private lateinit var cleanupManager: FileCleanupManager

    override fun onCreate() {
        super.onCreate()
        cleanupManager = FileCleanupManager(this)
        Log.d("CleanupService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("CleanupService", "Cleanup service started")

        serviceScope.launch {
            performCleanup()
        }

        return START_STICKY
    }

    private suspend fun performCleanup() {
        try {
            val deletedSize = cleanupManager.cleanupJunkFiles()
            Log.d("CleanupService", "Cleanup completed: ${deletedSize / 1024 / 1024} MB freed")
            
            // TODO: Notify user of cleanup results
        } catch (e: Exception) {
            Log.e("CleanupService", "Cleanup failed: ${e.message}")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
        Log.d("CleanupService", "Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
