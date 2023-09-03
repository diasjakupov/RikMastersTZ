package com.example.rikmasterstz.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.rikmasterstz.domain.models.CameraModel
import com.example.rikmasterstz.domain.models.DoorModel
import com.example.rikmasterstz.domain.network.Response
import com.example.rikmasterstz.domain.viewmodel.CameraViewModel
import com.example.rikmasterstz.domain.viewmodel.DoorViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun DoorList(
    viewModel: DoorViewModel
) {
    LaunchedEffect(key1 = true, block = {
        viewModel.fetchAllDoors()
    })

    val refreshing = viewModel.isRefreshing.observeAsState(false)

    val pulling = rememberPullRefreshState(
        refreshing = refreshing.value,
        onRefresh = { viewModel.fetchAllDoors(true) })

    val response = viewModel.doors.observeAsState(initial = Response.Loading)
    when (response.value) {
        is Response.Loading -> {

        }

        is Response.Error -> {

        }

        is Response.Success<*> -> {
            Box(
                modifier = Modifier
                    .pullRefresh(pulling)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items((response.value as Response.Success<List<DoorModel>>).data) {
                        var text by remember {
                            mutableStateOf(it.name)
                        }
                        var isAlertDialogShown by remember {
                            mutableStateOf(false)
                        }
                        DoorBlock(
                            snapshot = it.snapshot,
                            name = it.name ?: "",
                            isFavorite = it.favorites,
                            onFavorite = {
                                it.id.let { it1 -> viewModel.checkAsFavorite(it1, !it.favorites) }
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
                                            viewModel.updateDoorEntry(
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
                PullRefreshIndicator(
                    refreshing = refreshing.value,
                    state = pulling,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }

        }
    }
}