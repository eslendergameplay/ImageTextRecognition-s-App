package com.example.jetpack14.views

import android.media.Image
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.jetpack14.R
import com.example.jetpack14.viewmodels.ScannerViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CollectionGalleryView(scannerVM:ScannerViewModel){
    var imagesUris by remember { mutableStateOf<List<Uri>>(emptyList())}
    val multiplePhoto = rememberLauncherForActivityResult(contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10)) {
        imagesUris = it
    }
    val context = LocalContext.current
    Scaffold (floatingActionButton = { FloatingActionButton(onClick = {multiplePhoto.launch(
        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))}) {
        Icon(painter = painterResource(id = R.drawable.galeria), contentDescription = "")
    }}, modifier = Modifier.fillMaxSize()){paddingValues->
        Column (modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()){
            /*
            FlowColumn {
                imagesUris.forEach { uri->
                    AsyncImage(model = ImageRequest.Builder(context = context).data(uri).crossfade(enable = true).build(), contentDescription = "", contentScale = ContentScale.Crop,modifier = Modifier.size(120.dp).padding(start = 5.dp,end = 5.dp,top = 10.dp))
                }
            }

             */
            FlowRow {
                imagesUris.forEach { uri->
                    AsyncImage(model = ImageRequest.Builder(context = context).data(uri).crossfade(enable = true).build(), contentDescription = "", contentScale = ContentScale.Crop,modifier = Modifier.size(120.dp).padding(start = 5.dp,end = 5.dp,top = 10.dp))
                }
            }
        }
    }
}