package ru.techcrat.test.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "points_table")
data class LocalPoint(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val longitude: Double,
    val latitude: Double,
    val areaName: String
)