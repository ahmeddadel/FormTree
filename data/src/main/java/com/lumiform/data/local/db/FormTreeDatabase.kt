package com.lumiform.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lumiform.data.local.db.dao.CachedJsonDao
import com.lumiform.data.local.db.entity.CachedJsonEntity

/**
 * @created 17/07/2025 - 12:16 AM
 * @project FormTree
 * @author adell
 */
@Database(entities = [CachedJsonEntity::class], version = 1)
abstract class FormTreeDatabase : RoomDatabase() {
    abstract fun cachedJsonDao(): CachedJsonDao
}