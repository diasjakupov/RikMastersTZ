package com.example.rikmasterstz.domain.network

import com.example.rikmasterstz.domain.models.CameraModel
import com.example.rikmasterstz.domain.models.DoorModel
import kotlinx.serialization.Serializable


@Serializable
data class NetworkDoorResult(
    val success: Boolean,
    val data: List<DoorModel>
)
