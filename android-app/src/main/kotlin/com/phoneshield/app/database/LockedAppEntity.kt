package com.phoneshield.app.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locked_apps")
data class LockedAppEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val packageName: String,
    val appName: String,
    val passwordHash: String,
    val useBiometric: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
