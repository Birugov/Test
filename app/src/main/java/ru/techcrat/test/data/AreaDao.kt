package ru.techcrat.test.data


import androidx.room.*

@Dao
interface AreaDao {

    @Insert
    suspend fun addArea(area: Area)

    @Query("SELECT*FROM area_table")
    fun getAllAreas(): List<Area>

    @Query("SELECT*FROM points_table")
    fun getAllPoints(): List<LocalPoint>

    @Insert
    suspend fun insertPoint(point:LocalPoint)


    @Query("DELETE FROM points_table WHERE areaName= :areaName")
    suspend fun deletePoints(areaName: String)


    @Query("DELETE FROM area_table WHERE areaName= :areaName")
    suspend fun deleteArea(areaName: String)
}
