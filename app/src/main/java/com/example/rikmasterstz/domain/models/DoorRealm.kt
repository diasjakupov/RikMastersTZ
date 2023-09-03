package com.example.rikmasterstz.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class DoorRealm(
    var name: String = "",
    var room: String? = null,
    @PrimaryKey var id: Int = 0,
    var snapshot: String? = null,
    var favorites: Boolean = false
): RealmObject()