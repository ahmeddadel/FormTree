package com.lumiform.data.local.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @created 17/07/2025 - 12:03 AM
 * @project FormTree
 * @author adell
 */
@Entity(tableName = "cached_json")
data class CachedJsonEntity(
    @PrimaryKey val id: Int = 0,
    val json: String
)