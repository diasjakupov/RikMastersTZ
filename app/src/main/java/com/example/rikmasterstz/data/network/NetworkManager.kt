package com.example.rikmasterstz.data.network

import com.example.rikmasterstz.domain.network.Response

interface NetworkManager {
    suspend fun getAllCameras(): Response
    suspend fun getAllDoors(): Response
}