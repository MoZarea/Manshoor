package com.example.gemipost.ui.auth.login.components.imagevectors.oauthprovidericons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.gemipost.ui.auth.login.components.imagevectors.OAuthProviderIcons

public val OAuthProviderIcons.Discord: ImageVector
    get() {
        if (_discord != null) {
            return _discord!!
        }
        _discord = Builder(name = "Discord", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 1024.0f, viewportHeight = 1024.0f).apply {
            path(fill = SolidColor(Color(0xFF5865f2)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(512.0f, 512.0f)
                moveToRelative(-512.0f, 0.0f)
                arcToRelative(512.0f, 512.0f, 0.0f, true, true, 1024.0f, 0.0f)
                arcToRelative(512.0f, 512.0f, 0.0f, true, true, -1024.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(689.43f, 349.0f)
                arcToRelative(422.21f, 422.21f, 0.0f, false, false, -104.22f, -32.32f)
                arcToRelative(1.58f, 1.58f, 0.0f, false, false, -1.68f, 0.79f)
                arcToRelative(294.11f, 294.11f, 0.0f, false, false, -13.0f, 26.66f)
                arcToRelative(389.78f, 389.78f, 0.0f, false, false, -117.05f, 0.0f)
                arcToRelative(269.75f, 269.75f, 0.0f, false, false, -13.18f, -26.66f)
                arcToRelative(1.64f, 1.64f, 0.0f, false, false, -1.68f, -0.79f)
                arcTo(421.0f, 421.0f, 0.0f, false, false, 334.44f, 349.0f)
                arcToRelative(1.49f, 1.49f, 0.0f, false, false, -0.69f, 0.59f)
                curveToRelative(-66.37f, 99.17f, -84.55f, 195.9f, -75.63f, 291.41f)
                arcToRelative(1.76f, 1.76f, 0.0f, false, false, 0.67f, 1.2f)
                arcToRelative(424.58f, 424.58f, 0.0f, false, false, 127.85f, 64.63f)
                arcToRelative(1.66f, 1.66f, 0.0f, false, false, 1.8f, -0.59f)
                arcToRelative(303.45f, 303.45f, 0.0f, false, false, 26.15f, -42.54f)
                arcToRelative(1.62f, 1.62f, 0.0f, false, false, -0.89f, -2.25f)
                arcToRelative(279.6f, 279.6f, 0.0f, false, true, -39.94f, -19.0f)
                arcToRelative(1.64f, 1.64f, 0.0f, false, true, -0.16f, -2.72f)
                curveToRelative(2.68f, -2.0f, 5.37f, -4.1f, 7.93f, -6.22f)
                arcToRelative(1.58f, 1.58f, 0.0f, false, true, 1.65f, -0.22f)
                curveToRelative(83.79f, 38.26f, 174.51f, 38.26f, 257.31f, 0.0f)
                arcToRelative(1.58f, 1.58f, 0.0f, false, true, 1.68f, 0.2f)
                curveToRelative(2.56f, 2.11f, 5.25f, 4.23f, 8.0f, 6.24f)
                arcToRelative(1.64f, 1.64f, 0.0f, false, true, -0.14f, 2.72f)
                arcToRelative(262.37f, 262.37f, 0.0f, false, true, -40.0f, 19.0f)
                arcToRelative(1.63f, 1.63f, 0.0f, false, false, -0.87f, 2.28f)
                arcToRelative(340.72f, 340.72f, 0.0f, false, false, 26.13f, 42.52f)
                arcToRelative(1.62f, 1.62f, 0.0f, false, false, 1.8f, 0.61f)
                arcToRelative(423.17f, 423.17f, 0.0f, false, false, 128.0f, -64.63f)
                arcToRelative(1.64f, 1.64f, 0.0f, false, false, 0.67f, -1.18f)
                curveToRelative(10.68f, -110.44f, -17.88f, -206.38f, -75.7f, -291.42f)
                arcToRelative(1.3f, 1.3f, 0.0f, false, false, -0.63f, -0.63f)
                close()
                moveTo(427.09f, 582.85f)
                curveToRelative(-25.23f, 0.0f, -46.0f, -23.16f, -46.0f, -51.6f)
                reflectiveCurveToRelative(20.38f, -51.6f, 46.0f, -51.6f)
                curveToRelative(25.83f, 0.0f, 46.42f, 23.36f, 46.0f, 51.6f)
                curveToRelative(0.02f, 28.44f, -20.37f, 51.6f, -46.0f, 51.6f)
                close()
                moveTo(597.22f, 582.85f)
                curveToRelative(-25.23f, 0.0f, -46.0f, -23.16f, -46.0f, -51.6f)
                reflectiveCurveToRelative(20.38f, -51.6f, 46.0f, -51.6f)
                curveToRelative(25.83f, 0.0f, 46.42f, 23.36f, 46.0f, 51.6f)
                curveToRelative(0.01f, 28.44f, -20.17f, 51.6f, -46.0f, 51.6f)
                close()
            }
        }
        .build()
        return _discord!!
    }

private var _discord: ImageVector? = null
