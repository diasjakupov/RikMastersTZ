package com.example.rikmasterstz.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rikmasterstz.R
import com.example.rikmasterstz.ui.elements.CameraList
import com.example.rikmasterstz.ui.elements.DoorList
import com.example.rikmasterstz.ui.theme.LightGray
import com.example.rikmasterstz.ui.theme.OceanBlue

@Composable
fun MainScreen() {
    val tabs = remember {
        listOf(Tabs.CAMERA, Tabs.DOOR)
    }
    var tabIndex by remember() {
        mutableStateOf(0)
    }
    Column {
        ToolBar(tabs, tabIndex, onSelect = {
            tabIndex = it
        })

        Box(modifier = Modifier
            .background(LightGray)
            .padding(horizontal = 8.dp)
            ) {
            when (tabIndex) {
                0 -> CameraList(viewModel = viewModel())
                1 -> DoorList(viewModel = viewModel())
            }
        }

    }

}


@Composable
fun ToolBar(tabs: List<Tabs>, tabIndex: Int, onSelect: (idx: Int) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.background(LightGray)
    ) {
        Text(
            "Мой Дом",
            modifier = Modifier.padding(vertical = 20.dp),
            fontSize = 21.sp,
            fontFamily = FontFamily(
                Font(R.font.circe_font)
            )
        )
        TabRow(
            selectedTabIndex = tabIndex,
            containerColor = Color.Transparent
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = index == tabIndex,
                    onClick = {
                        onSelect(index)
                    },
                    text = {
                        Text(
                            text = tab.title, fontSize = 17.sp, fontFamily = FontFamily(
                                Font(R.font.circe_font)
                            ), color = Color.Black
                        )
                    }
                )
            }
        }
    }
}


@Composable
@Preview
fun ToolBar_Preview() {
    MaterialTheme {
        ToolBar(listOf(), 0) {}
    }
}