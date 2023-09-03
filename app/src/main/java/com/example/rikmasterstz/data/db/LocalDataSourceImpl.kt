package com.example.rikmasterstz.data.db

import com.example.rikmasterstz.domain.models.CameraModel
import com.example.rikmasterstz.domain.models.CameraRealm
import com.example.rikmasterstz.domain.models.DoorModel
import com.example.rikmasterstz.domain.models.DoorRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.executeTransactionAwait
import io.realm.kotlin.where
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject


class LocalDataSourceImpl @Inject constructor(
    private val realmConfig: RealmConfiguration
) : LocalDataSource {
    private fun mapCamera(entry: CameraRealm): CameraModel {
        return CameraModel(
            name = entry.name,
            snapshot = entry.snapshot,
            room = entry.room,
            id = entry.id,
            favorites = entry.favorites,
            rec = entry.rec
        )
    }

    private fun mapCamera(entry: CameraModel): CameraRealm {
        return CameraRealm(
            name = entry.name ?: "",
            snapshot = entry.snapshot,
            room = entry.room,
            id = entry.id ?: 0,
            favorites = entry.favorites,
            rec = entry.rec
        )
    }

    private fun mapDoor(entry: DoorRealm): DoorModel {
        return DoorModel(
            name = entry.name,
            snapshot = entry.snapshot,
            room = entry.room,
            id = entry.id,
            favorites = entry.favorites,
        )
    }

    private fun mapDoor(entry: DoorModel): DoorRealm {
        return DoorRealm(
            name = entry.name ?: "",
            snapshot = entry.snapshot,
            room = entry.room,
            id = entry.id ?: 0,
            favorites = entry.favorites,
        )
    }

    override suspend fun updateCameraModel(id: Int, name: String) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            val entry = it.where(CameraRealm::class.java).equalTo("id", id).findFirst()
            entry?.name = name
        }
    }

    override suspend fun checkCameraFavorite(id: Int, favorite: Boolean) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            val entry = it.where(CameraRealm::class.java).equalTo("id", id).findFirst()
            entry?.favorites = favorite
        }
    }

    override suspend fun insertDoorEntries(doors: List<DoorModel>) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmTransaction.insert(doors.map {
                mapDoor(it)
            })
        }
    }

    override suspend fun retrieveCameraModels(): List<CameraModel> {
        val realm = Realm.getInstance(realmConfig)
        val cameras = mutableListOf<CameraModel>()

        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            cameras.addAll(realmTransaction
                .where(CameraRealm::class.java)
                .findAll()
                .map {
                    mapCamera(it)
                }
            )
        }
        return cameras
    }

    override suspend fun retrieveDoorModels(): List<DoorModel> {
        val realm = Realm.getInstance(realmConfig)
        val doors = mutableListOf<DoorModel>()

        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            doors.addAll(realmTransaction
                .where(DoorRealm::class.java)
                .findAll()
                .map {
                    mapDoor(it)
                }
            )
        }
        return doors
    }

    override suspend fun updateDoorModel(id: Int, name: String) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            val entry = it.where(DoorRealm::class.java).equalTo("id", id).findFirst()
            entry?.name = name
        }
    }

    override suspend fun checkDoorFavorite(id: Int, favorite: Boolean) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) {
            val entry = it.where(DoorRealm::class.java).equalTo("id", id).findFirst()
            entry?.favorites = favorite
        }
    }

    override suspend fun insertCameraEntries(cameras: List<CameraModel>) {
        val realm = Realm.getInstance(realmConfig)
        realm.executeTransactionAwait(Dispatchers.IO) { realmTransaction ->
            realmTransaction.insert(cameras.map {
                mapCamera(it)
            })
        }
    }
}