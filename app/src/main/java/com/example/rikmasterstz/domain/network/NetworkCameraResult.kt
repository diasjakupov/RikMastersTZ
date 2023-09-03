package com.example.rikmasterstz.domain.network

import com.example.rikmasterstz.domain.models.CameraModel
import kotlinx.serialization.Serializable

@Serializable
data class NetworkCameraResult(
    val success: Boolean,
    val data: NetworkCameraResultData
)

@Serializable
data class NetworkCameraResultData(
    val cameras: List<CameraModel>
)