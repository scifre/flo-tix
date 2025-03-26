package com.example.e_ticket

import android.content.Context
import android.util.Log

import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@androidx.compose.ui.tooling.preview.Preview
@Composable
fun QrScanner(navController: NavController = NavController(LocalContext.current)){
    var hasScanned by remember { mutableStateOf(false) }
    var scannedCode by remember { mutableStateOf("") }

    Scaffold {paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            //complete this function call
            QrScannerView(
                onQrCodeScanned = { scannedData ->
                    if(!hasScanned){
                        hasScanned = true
                        scannedData?.let {
                            scannedCode = it
                            navController.navigate("result/$it")
                        }
                    }

                }
            )


        }
    }
}

@Composable
fun QrScannerView(onQrCodeScanned: (String?) -> Unit) {
    val context = LocalContext.current
    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                post { startCamera(context, this, onQrCodeScanned) }
            }
        }
    )
}

private fun startCamera(
    context: Context, previewView: PreviewView, onQrCodeScanned: (String?) -> Unit
) {
    val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
    val executor = Executors.newSingleThreadExecutor()

    cameraProviderFuture.addListener({
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis.setAnalyzer(executor) { imageProxy ->
            processImage(imageProxy, onQrCodeScanned)
        }

        try {
            cameraProvider.unbindAll()  // ✅ Ensure old instances are removed before rebinding
            cameraProvider.bindToLifecycle(context as LifecycleOwner, cameraSelector, preview, imageAnalysis)
        } catch (exc: Exception) {
            Log.e("CameraX", "Use case binding failed", exc)
        }

    }, ContextCompat.getMainExecutor(context))
}


@OptIn(ExperimentalGetImage::class)
private fun processImage(imageProxy: ImageProxy, onQrCodeScanned: (String?) -> Unit) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val scanner = BarcodeScanning.getClient()

        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    if (barcode.valueType == Barcode.TYPE_TEXT || barcode.valueType == Barcode.TYPE_URL) {
                        Log.d("QRScanner", "QR Code: ${barcode.displayValue}")
                        onQrCodeScanned(barcode.displayValue)

                    }
                }
            }
            .addOnFailureListener { e -> Log.e("QRScanner", "Error scanning QR", e) }
            .addOnCompleteListener { imageProxy.close() }
    }
}


