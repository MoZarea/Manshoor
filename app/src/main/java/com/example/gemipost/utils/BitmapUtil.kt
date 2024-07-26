package com.example.gemipost.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import coil.ImageLoader
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.gp.socialapp.util.Result.Loading.onSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

suspend fun urlToBitmap(
    scope: CoroutineScope,
    imageURLs: List<String>,
    context: Context,
): List<Bitmap> = scope.async {
    val deferredBitmaps = imageURLs.map { url ->
        async(Dispatchers.IO) {
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(url)
                .allowHardware(false)
                .build()
            val result = loader.execute(request)
            when (result) {
                is SuccessResult -> (result.drawable as BitmapDrawable).bitmap
                is ErrorResult -> {
                    Log.d("BitmapUtil", "Error: ${result.throwable}")
                    null
                }
                else -> {
                    Log.d("BitmapUtil", "Error: Unknown")
                    null
                }
            }
        }
    }
    deferredBitmaps.awaitAll().filterNotNull()
}.await()

//suspend fun urlToBitmap(
//    scope: CoroutineScope,
//    imageURLs: List<String>,
//    context: Context,
//) : List<Bitmap> {
//    val images = mutableListOf<Bitmap>().apply {
//        imageURLs.forEach { url ->
//            var bitmap: Bitmap? = null
//            val loadBitmap = scope.launch(Dispatchers.IO) {
//                val loader = ImageLoader(context)
//                val request = ImageRequest.Builder(context)
//                    .data(url)
//                    .allowHardware(false)
//                    .build()
//                val result = loader.execute(request)
//                if (result is SuccessResult) {
//                    bitmap = (result.drawable as BitmapDrawable).bitmap
//                } else if (result is ErrorResult) {
//                    cancel(result.throwable.localizedMessage ?: "ErrorResult", result.throwable)
//                }
//            }
//            loadBitmap.invokeOnCompletion { throwable ->
//                bitmap?.let {
//                    this.add(it)
//                } ?: throwable?.let {
//                    Log.d("BitmapUtil", "Error: $it")
//                } ?: { Log.d("BitmapUtil", "Error: Unknown") }
//            }
//
//        }
//    }
//}