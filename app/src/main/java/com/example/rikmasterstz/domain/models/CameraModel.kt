package com.example.rikmasterstz.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.serialization.Serializable


@Serializable
data class CameraModel(
    var name: String?,
    var snapshot: String?,
    var room: String?,
    var id: Int?,
    var favorites: Boolean,
    var rec: Boolean
)
