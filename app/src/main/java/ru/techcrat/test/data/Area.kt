package ru.techcrat.test.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.yandex.mapkit.geometry.Point

@Entity(tableName = "area_table")
data class Area(
    @PrimaryKey(autoGenerate = true)
    val areaId: Int,
    val areaName: String,

)