package com.example.rikmasterstz.domain.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.rikmasterstz.data.db.LocalDataSourceImpl
import com.example.rikmasterstz.data.network.NetworkManagerImpl
import com.example.rikmasterstz.domain.models.CameraModel
import com.example.rikmasterstz.domain.network.NetworkCameraResult
import com.example.rikmasterstz.domain.network.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(
    private val networkManagerImpl: NetworkManagerImpl,
    private val localDataSourceImpl: LocalDataSourceImpl
) : ViewModel() {
    private val _cameras: MutableLiveData<Response> = MutableLiveData()
    val camera: LiveData<Response> = _cameras

    val isRefreshing = MutableLiveData<Boolean>(false)

    private suspend fun retrieveFromDB(): List<CameraModel> {
        return localDataSourceImpl.retrieveCameraModels()
    }

    fun fetchAllCameras(force: Boolean = false) {
        viewModelScope.launch(Dispatchers.IO) {
            val fromDB = retrieveFromDB()
            Log.e("TAG FROM DB", "$fromDB")
            if (fromDB.isNotEmpty() && !force) {
                _cameras.postValue(
                    Response.Success(
                        data =
                        fromDB

                    )
                )
            } else {
                val response: Response =
                    when (val fromNetwork = networkManagerImpl.getAllCameras()) {
                        is Response.Loading -> {
                            fromNetwork
                        }

                        is Response.Error -> {
                            fromNetwork
                        }

                        is Response.Success<*> -> {
                            val cameras =
                                (fromNetwork as Response.Success<NetworkCameraResult>).data.data.cameras
                            if(force){
                                localDataSourceImpl.deleteCameras()
                            }
                            localDataSourceImpl.insertCameraEntries(cameras)
                            Response.Success(cameras)
                        }
                    }
                _cameras.postValue(response)
            }

        }
    }

    fun checkAsFavorite(id: Int, favorite: Boolean) {
        viewModelScope.launch {
            localDataSourceImpl.checkCameraFavorite(id, favorite)
            if (_cameras.value is Response.Success<*>) {
                val updatedList = (_cameras.value as Response.Success<List<CameraModel>>).data.map {
                    if (it.id == id) {
                        it.favorites = favorite
                    }
                    it
                }
                _cameras.postValue(Response.Success(updatedList))
            }
        }
    }

    fun updateCameraEntry(id: Int, name: String) {
        viewModelScope.launch {
            localDataSourceImpl.updateCameraModel(id, name)
            if (_cameras.value is Response.Success<*>) {
                val updatedList = (_cameras.value as Response.Success<List<CameraModel>>).data.map {
                    if (it.id == id) {
                        it.name = name
                    }
                    it
                }
                _cameras.postValue(Response.Success(updatedList))
            }
        }
    }
}