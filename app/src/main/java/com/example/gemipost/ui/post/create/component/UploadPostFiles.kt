package com.gp.socialapp.presentation.post.create.component

import com.example.gemipost.data.post.source.remote.model.PostAttachment
import com.gp.socialapp.presentation.material.utils.MimeType
import com.mohamedrejeb.calf.core.PlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.getName
import com.mohamedrejeb.calf.io.readByteArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun uploadPostFiles(
    scope: CoroutineScope,
    files: List<KmpFile>,
    context: PlatformContext,
    onAddFile: (PostAttachment) -> Unit,
) {
    scope.launch {
        files.forEach { file ->
            val image = file.readByteArray(context)
            val type = MimeType.getMimeTypeFromFileName(file.getName(context) ?: "")
            val extension = MimeType.getExtensionFromMimeType(type)
            onAddFile(
                PostAttachment(
                    file = image,
                    name = file.getName(context) ?: "",
                    type = extension,
                    size = image.size.toLong()
                )
            )
        }
    }
}