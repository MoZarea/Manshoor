package com.example.gemipost.utils

sealed interface MimeType {

    enum class Image(val extension: String, val mimeType: String) : MimeType {
        JPG("jpg", "image/jpeg"),
        JPEG(
            "jpeg",
            "image/jpeg"
        ),
        PNG("png", "image/png"),
        GIF("gif", "image/gif"),
        BMP("bmp", "image/bmp"),
        PSD(
            "psd",
            "image/vnd.adobe.photoshop"
        ),
        TIF("tif", "image/tiff"),
        TIFF("tiff", "image/tiff"),
        SVG("svg", "image/svg+xml"),
    }

    enum class Video(val extension: String, val mimeType: String) : MimeType {
        MP4("mp4", "video/mp4"),
        AVI("avi", "video/x-msvideo"),
        MOV("mov", "video/quicktime"),
        WMV("wmv", "video/x-ms-wmv"),
        WEBM("webm", "video/webm"),
        MPEG("mpeg", "video/mpeg"),
        MPG(
            "mpg",
            "video/mpeg"
        ),
    }

    enum class Application(val extension: String, val mimeType: String) : MimeType {
        APK(
            "apk",
            "application/vnd.android.package-archive"
        ),
        EXE("exe", "application/octet-stream"),
        UNKNOWN("unknown", "application/octet-stream"),
        PDF("pdf", "application/pdf"),
        TXT("txt", "text/plain"),
        JS(
            "js",
            "application/javascript"
        ),
        JSON("json", "application/json"),
        XML("xml", "application/xml"),
        ZIP(
            "zip",
            "application/zip"
        ),
        RAR("rar", "application/x-rar-compressed"),
        TAR("tar", "application/x-tar"),
        _7Z(
            "7z",
            "application/x-7z-compressed"
        ),
        DOC("doc", "application/msword"),
        DOCX("docx", "application/msword"),
        XLS(
            "xls",
            "application/vnd.ms-excel"
        ),
        XLSX("xlsx", "application/vnd.ms-excel"),
        PPT(
            "ppt",
            "application/vnd.ms-powerpoint"
        ),
        PPTX("pptx", "application/vnd.ms-powerpoint"),
        RTF(
            "rtf",
            "application/rtf"
        ),
        XLSB(
            "xlsb",
            "application/vnd.ms-excel.sheet.binary.macroEnabled.12"
        ),
        XLSM("xlsm", "application/vnd.ms-excel.sheet.macroEnabled.12"),
        XLTM(
            "xltm",
            "application/vnd.ms-excel.template.macroEnabled.12"
        ),
        XLTX("xltx", "application/vnd.openxmlformats-officedocument.spreadsheetml.template"),
        XLW(
            "xlw",
            "application/vnd.ms-excel"
        ),
        POTX(
            "potx",
            "application/vnd.openxmlformats-officedocument.presentationml.template"
        ),
        PPAM("ppam", "application/vnd.ms-powerpoint.addin.macroEnabled.12"),
        PPTM(
            "pptm",
            "application/vnd.ms-powerpoint.presentation.macroEnabled.12"
        ),
        SLDM("sldm", "application/vnd.ms-powerpoint.slide.macroEnabled.12"),
        PPSM(
            "ppsm",
            "application/vnd.ms-powerpoint.slideshow.macroEnabled.12"
        ),
        POTM("potm", "application/vnd.ms-powerpoint.template.macroEnabled.12"),
        THMX(
            "thmx",
            "application/vnd.ms-officetheme"
        ),
        AI("ai", "application/postscript"), EPS("eps", "application/postscript"),

    }

    enum class Text(val extension: String, val mimeType: String) : MimeType {
        TXT("txt", "text/plain"),
        HTML(
            "html",
            "text/html"
        ),
        HTM("htm", "text/html"),
        CSS("css", "text/css"),
        CSV("csv", "text/csv"),
    }


    enum class Audio(val extension: String, val mimeType: String) : MimeType {
        MP3("mp3", "audio/mpeg"),
        WAV("wav", "audio/wav"),
        OGG("ogg", "audio/ogg"),
        FLAC("flac", "audio/flac"),
    }

    enum class Font(val extension: String, val mimeType: String) : MimeType {
        WOFF("woff", "font/woff"), WOFF2("woff2", "font/woff2"), TTF(
            "ttf",
            "font/ttf"
        ),
        OTF("otf", "font/otf"),


    }

    companion object {
        fun getExtensionFromMimeType(mimeType: MimeType): String {
            return when (mimeType) {
                is Image -> mimeType.extension
                is Video -> mimeType.extension
                is Application -> mimeType.extension
                is Text -> mimeType.extension
                is Audio -> mimeType.extension
                is Font -> mimeType.extension
            }
        }

        fun getFullMimeType(mimeType: MimeType): String {
            return when (mimeType) {
                is Image -> mimeType.mimeType
                is Video -> mimeType.mimeType
                is Application -> mimeType.mimeType
                is Text -> mimeType.mimeType
                is Audio -> mimeType.mimeType
                is Font -> mimeType.mimeType
            }
        }

        fun getMimeTypeFromFileName(fileName: String): MimeType {
            when (val extension = fileName.substringAfterLast('.', "")) {
                in Image.entries.map { it.extension } -> {
                    return Image.entries.first { it.extension == extension }
                }

                in Video.entries.map { it.extension } -> {
                    return Video.entries.first { it.extension == extension }
                }

                in Application.entries.map { it.extension } -> {
                    return Application.entries.first { it.extension == extension }
                }

                in Text.entries.map { it.extension } -> {
                    return Text.entries.first { it.extension == extension }
                }

                in Audio.entries.map { it.extension } -> {
                    return Audio.entries.first { it.extension == extension }
                }

                else -> {
                    return Font.entries.first { it.extension == extension }
                }
            }
        }
    }
}

