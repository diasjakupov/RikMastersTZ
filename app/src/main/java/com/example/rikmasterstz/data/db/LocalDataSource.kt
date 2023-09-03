package com.example.rikmasterstz.data.db

import com.example.rikmasterstz.domain.models.CameraModel
import com.example.rikmasterstz.domain.models.CameraRealm
import com.example.rikmasterstz.domain.models.DoorModel

interface LocalDataSource {
    suspend fun retrieveCameraModels(): List<CameraModel>
    suspend fun retrieveDoorModels(): List<DoorModel>
    suspend fun insertCameraEntries(cameras: List<CameraModel>)
    suspend fun updateCameraModel(id: Int, name: String)
    suspend fun checkCameraFavorite(id: Int, favorite: Boolean)
    suspend fun updateDoorModel(id: Int, name: String)
    suspend fun checkDoorFavorite(id: Int, favorite: Boolean)
    suspend fun insertDoorEntries(doors: List<DoorModel>)
    suspend fun deleteDoors()
    suspend fun deleteCameras()
}