package com.example.rikmasterstz.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import kotlinx.serialization.Serializable

open class CameraRealm(
    var name: String = "",
    var snapshot: String? = null,
    var room: String? = null,
    @PrimaryKey var id: Int = 0,
    var favorites: Boolean = false,
    var rec: Boolean = false
): RealmObject()