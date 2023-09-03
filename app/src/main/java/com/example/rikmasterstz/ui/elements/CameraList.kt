package com.example.rikmasterstz.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.rikmasterstz.domain.models.CameraModel
import com.example.rikmasterstz.domain.network.NetworkCameraResult
import com.example.rikmasterstz.domain.network.Response
import com.example.rikmasterstz.domain.viewmodel.CameraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraList(
    viewModel: CameraViewModel
) {
    LaunchedEffect(key1 = true, block = {
        viewModel.fetchAllCameras()
    })

    val response = viewModel.camera.observeAsState(initial = Response.Loading)
    when (response.value) {
        is Response.Loading -> {

        }

        is Response.Error -> {

        }

        is Response.Success<*> -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items((response.value as Response.Success<List<CameraModel>>).data) {
                    var text by remember {
                        mutableStateOf(it.name)
                    }
                    var isAlertDialogShown by remember {
                        mutableStateOf(false)
                    }
                    Camera(
                        image = it.snapshot ?: "",
                        cameraName = it.name ?: "",
                        isFavorite = it.favorites,
                        onFavorite = {
                            it.id?.let { it1 -> viewModel.checkAsFavorite(it1, !it.favorites) }
                        }, onRename = {
                            isAlertDialogShown = true
                        })

                    if (isAlertDialogShown) {
                        AlertDialog(
                            onDismissRequest = { isAlertDialogShown = false },
                            text = {
                                TextField(value = text ?: "", onValueChange = { newText ->
                                    text = newText
                                })
                            },
                            confirmButton = {
                                Button(onClick = {
                                    it.id?.let { it1 ->
                                        viewModel.updateCameraEntry(
                                            it1,
                                            text ?: ""
                                        )
                                    }
                                    isAlertDialogShown = false
                                }) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { isAlertDialogShown = false }) {
                                    Text(text = "Cancel")
                                }
                            })
                    }

                }
            }

        }
    }
}