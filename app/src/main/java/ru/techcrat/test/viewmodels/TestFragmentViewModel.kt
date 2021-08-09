package ru.techcrat.test.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.techcrat.test.data.Area
import ru.techcrat.test.data.AreasDatabase
import ru.techcrat.test.data.FullArea
import ru.techcrat.test.data.LocalPoint
import ru.techcrat.test.repositories.AreaRepository
import javax.inject.Inject

class TestFragmentViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    val _getAllPoints: MutableLiveData<List<LocalPoint>> = MutableLiveData()
    val getAllPoints: LiveData<List<LocalPoint>> = _getAllPoints

    val _readAllData: MutableLiveData<List<Area>> = MutableLiveData()
    val readAllData: LiveData<List<Area>> = _readAllData

    private val repository: AreaRepository

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            _getAllPoints.postValue(repository.getAllPoints())
            _readAllData.postValue(repository.readAllData())

        }

    }

    init {

        val areaDao = AreasDatabase.getDatabase(application).areaDao()
        repository = AreaRepository(areaDao)

    }

}