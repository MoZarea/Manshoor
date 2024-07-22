package com.example.gemipost.utils

import androidx.compose.ui.graphics.vector.ImageVector
import com.gp.socialapp.presentation.material.components.imageVectors.MaterialIcon
import com.example.gemipost.ui.theme.imageVectors.materialicon.Ai
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Avi
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Bmp
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Csv
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Doc
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Docx
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Eps
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Exe
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.File
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Giff
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Html
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Jpg
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Mov
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Mp3
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Mp4
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Mpeg
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Pdf
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Png
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Ppt
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Psd
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Rar
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Svg
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Tiff
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Txt
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Wav
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Xml
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Xsl
import com.gp.socialapp.presentation.material.components.imageVectors.materialicon.Zip

fun getFileImageVector(type: MimeType): ImageVector {
    return when (type) {
        MimeType.Text.CSV -> MaterialIcon.Csv
        MimeType.Text.HTML -> MaterialIcon.Html
        MimeType.Image.BMP -> MaterialIcon.Bmp
        MimeType.Image.GIF -> MaterialIcon.Giff
        MimeType.Image.JPG -> MaterialIcon.Jpg
        MimeType.Image.JPEG -> MaterialIcon.Jpg
        MimeType.Image.PNG -> MaterialIcon.Png
        MimeType.Image.PSD -> MaterialIcon.Psd
        MimeType.Image.SVG -> MaterialIcon.Svg
        MimeType.Image.TIFF -> MaterialIcon.Tiff
        MimeType.Video.AVI -> MaterialIcon.Avi
        MimeType.Video.MOV -> MaterialIcon.Mov
        MimeType.Video.MP4 -> MaterialIcon.Mp4
        MimeType.Video.MPEG -> MaterialIcon.Mpeg
        MimeType.Audio.MP3 -> MaterialIcon.Mp3
        MimeType.Audio.WAV -> MaterialIcon.Wav
        MimeType.Application.AI -> MaterialIcon.Ai
        MimeType.Application.DOC -> MaterialIcon.Doc
        MimeType.Application.DOCX -> MaterialIcon.Docx
        MimeType.Application.EPS -> MaterialIcon.Eps
        MimeType.Application.EXE -> MaterialIcon.Exe
        MimeType.Application.PDF -> MaterialIcon.Pdf
        MimeType.Application.PPT -> MaterialIcon.Ppt
        MimeType.Application.PPTX -> MaterialIcon.Ppt
        MimeType.Application.RAR -> MaterialIcon.Rar
        MimeType.Application.TXT -> MaterialIcon.Txt
        MimeType.Application.XML -> MaterialIcon.Xml
        MimeType.Application.ZIP -> MaterialIcon.Zip
        MimeType.Application.XLS -> MaterialIcon.Xsl
        MimeType.Application.XLSX -> MaterialIcon.Xsl
        else -> MaterialIcon.File
    }
}