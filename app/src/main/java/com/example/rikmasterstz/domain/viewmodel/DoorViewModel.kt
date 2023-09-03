package com.example.rikmasterstz.domain.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rikmasterstz.data.db.LocalDataSourceImpl
import com.example.rikmasterstz.data.network.NetworkManagerImpl
import com.example.rikmasterstz.domain.models.CameraModel
import com.example.rikmasterstz.domain.models.DoorModel
import com.example.rikmasterstz.domain.network.NetworkCameraResult
import com.example.rikmasterstz.domain.network.NetworkDoorResult
import com.example.rikmasterstz.domain.network.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DoorViewModel @Inject constructor(
    private val networkManagerImpl: NetworkManagerImpl,
    private val localDataSourceImpl: LocalDataSourceImpl
) : ViewModel() {
    private val _doors: MutableLiveData<Response> = MutableLiveData()
    val doors: LiveData<Response> = _doors

    val isRefreshing = MutableLiveData<Boolean>(false)


    private suspend fun retrieveFromDB(): List<DoorModel> {
        return localDataSourceImpl.retrieveDoorModels()
    }

    fun fetchAllDoors(force: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val fromDB = retrieveFromDB()
            if (fromDB.isNotEmpty() && !force) {
                _doors.postValue(
                    Response.Success(
                        data =
                        fromDB
                    )
                )
            } else {
                val response: Response =
                    when (val fromNetwork = networkManagerImpl.getAllDoors()) {
                        is Response.Loading -> {
                            fromNetwork
                        }

                        is Response.Error -> {
                            fromNetwork
                        }

                        is Response.Success<*> -> {
                            val doors =
                                (fromNetwork as Response.Success<NetworkDoorResult>).data.data
                            if (force) {
                                localDataSourceImpl.deleteDoors()
                            }
                            localDataSourceImpl.insertDoorEntries(doors)
                            Response.Success(doors)
                        }
                    }
                _doors.postValue(response)
            }

        }
    }

    fun checkAsFavorite(id: Int, favorite: Boolean) {
        viewModelScope.launch {
            localDataSourceImpl.checkDoorFavorite(id, favorite)
            if (_doors.value is Response.Success<*>) {
                val updatedList = (_doors.value as Response.Success<List<DoorModel>>).data.map {
                    if (it.id == id) {
                        it.favorites = favorite
                    }
                    it
                }
                _doors.postValue(Response.Success(updatedList))
            }
        }
    }

    fun updateDoorEntry(id: Int, name: String) {
        viewModelScope.launch {
            localDataSourceImpl.updateDoorModel(id, name)
            if (_doors.value is Response.Success<*>) {
                val updatedList = (doors.value as Response.Success<List<DoorModel>>).data.map {
                    if (it.id == id) {
                        it.name = name
                    }
                    it
                }
                _doors.postValue(Response.Success(updatedList))
            }
        }
    }
}