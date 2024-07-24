package com.example.gemipost.ui.post.create.component

import android.content.ContentResolver
import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable

@Composable
fun pickVisualMedia(contentResolver: ContentResolver,metadata:(ImageMetadata)->Unit): ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?> {
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            uri?.let { selectedUri ->

                // Getting file name
                val fileNameColumn = arrayOf(OpenableColumns.DISPLAY_NAME)
                val nameCursor =
                    contentResolver.query(selectedUri, fileNameColumn, null, null, null)
                nameCursor?.moveToFirst()
                val fileName = nameCursor?.getString(0)
                nameCursor?.close()

                // Getting file type (MIME type)
                val fileType = contentResolver.getType(selectedUri)

                // Getting file size
                val fileSizeColumn = arrayOf(OpenableColumns.SIZE)
                val sizeCursor =
                    contentResolver.query(selectedUri, fileSizeColumn, null, null, null)
                sizeCursor?.moveToFirst()
                val fileSize = sizeCursor?.getLong(0) ?: 0L
                sizeCursor?.close()
                metadata(ImageMetadata(uri = selectedUri, fileName = fileName ?: "", fileType = fileType ?: "", fileSize = fileSize))

            }
        }
    )
    return galleryLauncher
}

data class ImageMetadata(
    val id : String = "",
    val uri : Uri,
    val fileName: String,
    val fileType: String,
    val fileSize: Long
)