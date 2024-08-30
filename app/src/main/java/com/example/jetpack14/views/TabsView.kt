package com.example.jetpack14.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack14.viewmodels.ScannerViewModel


@Composable
fun TabsView(scannerVM:ScannerViewModel){
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("Galeria.","Camara.","Coleccion.")
    Column (modifier = Modifier.padding(top = 20.dp)) {
        TabRow(
            selectedTabIndex = selectedTab,
            contentColor = Color.Black,
            indicator = { tabpostion ->
                SecondaryIndicator(modifier = Modifier.tabIndicatorOffset(tabpostion[selectedTab]))}) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index, onClick = { selectedTab = index },text = {Text(text = title)})
            }
        }
        when(selectedTab){
            0->{GalleryView(scannerVM).apply { scannerVM.cleanText() }}
            1->{CameraView(scannerVM).apply { scannerVM.cleanText() }}
            2->{CollectionGalleryView(scannerVM).apply { scannerVM.cleanText() }}
        }

    }
}