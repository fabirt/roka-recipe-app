package com.fabirt.roka.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "instructions")
data class DatabaseInstruction(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "recipe_id")
    val recipeId: Int,
    val number: Int,
    val step: String
)