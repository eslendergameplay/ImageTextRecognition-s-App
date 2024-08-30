package com.example.jetpack14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.jetpack14.ui.theme.JetPack14Theme
import com.example.jetpack14.viewmodels.ScannerViewModel
import com.example.jetpack14.views.TabsView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel : ScannerViewModel by viewModels()
            JetPack14Theme {
                Surface (modifier = Modifier.fillMaxSize()){
                    TabsView(scannerVM = viewModel)
                }
            }
        }
    }
}
