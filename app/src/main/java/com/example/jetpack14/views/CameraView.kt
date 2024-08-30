package com.example.jetpack14.views

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.rememberAsyncImagePainter
import com.example.jetpack14.R
import com.example.jetpack14.viewmodels.ScannerViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Objects

@Composable
fun CameraView(scannerVM:ScannerViewModel){
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    var image by remember { mutableStateOf<Uri>(Uri.EMPTY)}
    val file = context.createImageFile()
    val uri = FileProvider.getUriForFile(Objects.requireNonNull(context),context.packageName +".provider",file)
    val imageDefault = R.drawable.camara
    val permissionsCheckResult = ContextCompat.checkSelfPermission(context,android.Manifest.permission.CAMERA)
    val cameraLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicture()
    ) {
        image = uri
    }
    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) {
        if(it != null){
            scannerVM.showToast(context,"Permiso Concedido.")
            cameraLauncher.launch(uri)
        }else{
            scannerVM.showToast(context,"Permiso Denegado.")
        }
    }

    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally){
        Image(modifier = Modifier
            .clickable {
                if (permissionsCheckResult == PackageManager.PERMISSION_GRANTED) {
                    cameraLauncher.launch(uri)
                } else {
                    permissionLauncher.launch(android.Manifest.permission.CAMERA)
                }
            }
            .padding(16.dp, 8.dp),
            painter = rememberAsyncImagePainter(if(image.path?.isNotEmpty() == true)image else imageDefault),
            contentDescription = "").apply {
                scannerVM.onRecognizedText(image,context)
        }
        Spacer(modifier = Modifier.height(25.dp))
        val scrollState = rememberScrollState(0)
        Text(
            text = scannerVM.recognizedText,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .clickable {
                    clipboard.setText(AnnotatedString(scannerVM.recognizedText))
                    scannerVM.showToast(context,"Copiado.")
                })
    }
}


@SuppressLint("SimpleDateFormat")
fun Context.createImageFile():File{
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    return File.createTempFile(imageFileName,".jpg",externalCacheDir)
}