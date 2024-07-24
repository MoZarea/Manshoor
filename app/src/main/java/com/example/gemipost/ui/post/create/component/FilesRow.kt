package com.example.gemipost.ui.post.create.component

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun FilesRow(
    modifier: Modifier = Modifier,
    files: List<Uri>,
    onFileDelete: (Uri) -> Unit,
    onAddFile: () -> Unit,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
    ) {
        item {
            AddNewFileButton(Modifier.padding(horizontal = 8.dp), onClick = onAddFile)

        }
        items(files) { uri ->

            Box(

                modifier = Modifier
                    .size(64.dp)
                    .drawBehind {
                        drawRoundRect(
                            color = Color.Unspecified,
                            style = androidx.compose.ui.graphics.drawscope.Fill,
                            cornerRadius = CornerRadius(8.dp.toPx())
                        )
                    }
                    .clip(RoundedCornerShape(8.dp))
                    .clickable { onFileDelete(uri) }
                    .padding(horizontal = 2.dp)
            )
            {
                GlideImage(
                    imageModel = { uri }, // loading a network image using an URL.
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    )
                )


            }
        }

    }
}