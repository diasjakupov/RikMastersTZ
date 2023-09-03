package com.example.rikmasterstz.data.network

import android.util.Log
import com.example.rikmasterstz.data.network.NetworkConst.BASE_URL
import com.example.rikmasterstz.domain.network.NetworkCameraResult
import com.example.rikmasterstz.domain.network.NetworkDoorResult
import com.example.rikmasterstz.domain.network.Response
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject


class NetworkManagerImpl @Inject constructor(
    private val ktorClient: HttpClient
): NetworkManager {

    override suspend fun getAllCameras(): Response{
        val response = ktorClient.get("${BASE_URL}cameras")
        return when(response.status.value){
            in 200..900->{
                Response.Success(response.body<NetworkCameraResult>())
            }
            else->{
                Response.Error("Error")
            }
        }
    }

    override suspend fun getAllDoors(): Response {
        val response = ktorClient.get("${BASE_URL}doors")
        return when(response.status.value){
            in 200..900->{
                Response.Success(response.body<NetworkDoorResult>())
            }
            else->{
                Response.Error("Error")
            }
        }
    }
}