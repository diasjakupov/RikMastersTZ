package com.example.rikmasterstz.domain.models

import kotlinx.serialization.Serializable


@Serializable
data class DoorModel(
    var name: String = "",
    var room: String? = null,
    var id: Int = 0,
    var snapshot: String? = null,
    var favorites: Boolean = false
)
