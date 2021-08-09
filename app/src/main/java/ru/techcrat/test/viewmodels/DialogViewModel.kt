package ru.techcrat.test.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.techcrat.test.data.Area
import ru.techcrat.test.data.AreasDatabase
import ru.techcrat.test.data.LocalPoint
import ru.techcrat.test.repositories.AreaRepository
import javax.inject.Inject

class DialogViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    private val repository: AreaRepository

    init {
        val areaDao = AreasDatabase.getDatabase(application).areaDao()
        repository = AreaRepository(areaDao)

    }

    fun addArea(area: Area) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addArea(area)
        }
    }

    fun addPoints(points: List<Point>, name: String) {
        for (point in points) {
            val latitude = point.latitude
            val longitude = point.longitude
            val point = LocalPoint(0, longitude, latitude, name)
            viewModelScope.launch {
                repository.addPoints(point)
            }
        }
    }

}