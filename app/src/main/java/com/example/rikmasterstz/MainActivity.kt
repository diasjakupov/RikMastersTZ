package com.example.rikmasterstz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rikmasterstz.data.network.NetworkManager
import com.example.rikmasterstz.data.network.NetworkManagerImpl
import com.example.rikmasterstz.domain.network.NetworkCameraResult
import com.example.rikmasterstz.domain.network.Response
import com.example.rikmasterstz.ui.screen.MainScreen
import com.example.rikmasterstz.ui.theme.RikMastersTZTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var ntManager: NetworkManagerImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RikMastersTZTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}
