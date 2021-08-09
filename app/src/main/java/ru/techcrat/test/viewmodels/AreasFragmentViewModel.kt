package ru.techcrat.test.viewmodels

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import ru.techcrat.test.data.Area
import ru.techcrat.test.data.AreasDatabase
import ru.techcrat.test.data.LocalPoint
import ru.techcrat.test.data.relations.AreaWithPoints
import ru.techcrat.test.repositories.AreaRepository
import javax.inject.Inject

class AreasFragmentViewModel @Inject constructor(application: Application) :
    AndroidViewModel(application) {

    var _readAllData: MutableLiveData<List<Area>> = MutableLiveData()
    val readAllData: LiveData<List<Area>> = _readAllData

    private val repository: AreaRepository

    init {
        val areaDao = AreasDatabase.getDatabase(application).areaDao()
        repository = AreaRepository(areaDao)

    }

    fun initData() {
        viewModelScope.launch(Dispatchers.IO) {
            _readAllData.postValue(repository.readAllData())
        }
    }

    fun deleteData(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deletePoints(name)
            repository.deleteAreas(name)
        }
    }

}
