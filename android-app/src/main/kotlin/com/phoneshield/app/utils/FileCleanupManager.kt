package com.phoneshield.app.utils

import android.content.Context
import android.os.Environment
import android.util.Log
import java.io.File

class FileCleanupManager(private val context: Context) {

    suspend fun cleanupJunkFiles(): Long {
        var totalDeletedSize = 0L

        try {
            // Clean cache
            totalDeletedSize += cleanDirectory(context.cacheDir)

            // Clean temp files
            val tempDir = File(Environment.getExternalStorageDirectory(), "TEMP")
            if (tempDir.exists()) {
                totalDeletedSize += cleanDirectory(tempDir)
            }

            // Clean app-specific junk
            totalDeletedSize += cleanAppSpecificJunk()

            Log.d("FileCleanupManager", "Total cleaned: $totalDeletedSize bytes")
        } catch (e: Exception) {
            Log.e("FileCleanupManager", "Cleanup error: ${e.message}")
        }

        return totalDeletedSize
    }

    private fun cleanDirectory(directory: File): Long {
        var deletedSize = 0L

        try {
            if (!directory.exists()) return 0L

            directory.listFiles()?.forEach { file ->
                if (file.isFile) {
                    deletedSize += file.length()
                    file.delete()
                } else if (file.isDirectory) {
                    deletedSize += cleanDirectory(file)
                }
            }

            Log.d("FileCleanupManager", "Cleaned directory: ${directory.absolutePath}")
        } catch (e: Exception) {
            Log.e("FileCleanupManager", "Error cleaning directory: ${e.message}")
        }

        return deletedSize
    }

    private fun cleanAppSpecificJunk(): Long {
        var deletedSize = 0L

        try {
            val downloadDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val files = downloadDir.listFiles() ?: arrayOf()

            files.forEach { file ->
                if (isJunkFile(file)) {
                    deletedSize += file.length()
                    file.delete()
                }
            }
        } catch (e: Exception) {
            Log.e("FileCleanupManager", "Error cleaning app junk: ${e.message}")
        }

        return deletedSize
    }

    private fun isJunkFile(file: File): Boolean {
        val junkExtensions = listOf(".tmp", ".bak", ".log", ".cache")
        val fileName = file.name.lowercase()

        return junkExtensions.any { fileName.endsWith(it) }
    }

    fun cleanupAfterAppUninstall(packageName: String): Long {
        var deletedSize = 0L

        try {
            // Clean app cache
            val appCacheDir = File(context.cacheDir, packageName)
            if (appCacheDir.exists()) {
                deletedSize += cleanDirectory(appCacheDir)
            }

            // Clean app data
            val appDataDir = File(context.filesDir, packageName)
            if (appDataDir.exists()) {
                deletedSize += cleanDirectory(appDataDir)
            }

            Log.d("FileCleanupManager", "Cleaned app data: $packageName ($deletedSize bytes)")
        } catch (e: Exception) {
            Log.e("FileCleanupManager", "Error cleaning app data: ${e.message}")
        }

        return deletedSize
    }
}
