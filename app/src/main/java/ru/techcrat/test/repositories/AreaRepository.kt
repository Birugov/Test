package ru.techcrat.test.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.techcrat.test.data.Area
import ru.techcrat.test.data.AreaDao
import ru.techcrat.test.data.LocalPoint



class AreaRepository(private val areaDao: AreaDao) {

    var readAllData: List<Area> = ArrayList()
    var getAllPoints: List<LocalPoint> = ArrayList()

     suspend fun getAllPoints(): List<LocalPoint> {
        getAllPoints = areaDao.getAllPoints()
        return getAllPoints
    }

      suspend fun readAllData():List<Area>{
        readAllData = areaDao.getAllAreas()
        return  readAllData
    }



    suspend fun addArea(area: Area){
        areaDao.addArea(area)
    }

    suspend fun addPoints(point:LocalPoint){
        areaDao.insertPoint(point)
    }

    suspend fun deleteAreas(areaName:String){
        areaDao.deleteArea(areaName)
    }

    suspend fun deletePoints(areaName:String){
        areaDao.deletePoints(areaName)
    }
}