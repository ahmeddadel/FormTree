package com.lumiform.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lumiform.data.local.db.entity.CachedJsonEntity

/**
 * @created 17/07/2025 - 12:04 AM
 * @project FormTree
 * @author adell
 */
@Dao
interface CachedJsonDao {

    @Query("SELECT * FROM cached_json LIMIT 1")
    suspend fun getLastCached(): CachedJsonEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(cache: CachedJsonEntity)

    @Query("DELETE FROM cached_json")
    suspend fun clearCache(): Int

    @Query("SELECT * FROM cached_json")
    suspend fun getAllCached(): List<CachedJsonEntity>
}