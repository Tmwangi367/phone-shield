package com.phoneshield.app.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Delete
import androidx.room.Query

@Dao
interface LockedAppDao {

    @Insert
    suspend fun insertLockedApp(lockedApp: LockedAppEntity): Long

    @Delete
    suspend fun deleteLockedApp(lockedApp: LockedAppEntity)

    @Query("SELECT * FROM locked_apps")
    suspend fun getAllLockedApps(): List<LockedAppEntity>

    @Query("SELECT * FROM locked_apps WHERE packageName = :packageName")
    suspend fun getLockedApp(packageName: String): LockedAppEntity?

    @Query("DELETE FROM locked_apps WHERE packageName = :packageName")
    suspend fun deleteLockedApp(packageName: String)
}
