package com.example.gemipost.ui.auth.login.components.imagevectors.oauthprovidericons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.gemipost.ui.auth.login.components.imagevectors.OAuthProviderIcons

public val OAuthProviderIcons.Github: ImageVector
    get() {
        if (_github != null) {
            return _github!!
        }
        _github = Builder(name = "Github", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF161514)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(8.0f, 1.0f)
                curveTo(4.133f, 1.0f, 1.0f, 4.13f, 1.0f, 7.993f)
                curveToRelative(0.0f, 3.09f, 2.006f, 5.71f, 4.787f, 6.635f)
                curveToRelative(0.35f, 0.064f, 0.478f, -0.152f, 0.478f, -0.337f)
                curveToRelative(0.0f, -0.166f, -0.006f, -0.606f, -0.01f, -1.19f)
                curveToRelative(-1.947f, 0.423f, -2.357f, -0.937f, -2.357f, -0.937f)
                curveToRelative(-0.319f, -0.808f, -0.778f, -1.023f, -0.778f, -1.023f)
                curveToRelative(-0.635f, -0.434f, 0.048f, -0.425f, 0.048f, -0.425f)
                curveToRelative(0.703f, 0.05f, 1.073f, 0.72f, 1.073f, 0.72f)
                curveToRelative(0.624f, 1.07f, 1.638f, 0.76f, 2.037f, 0.582f)
                curveToRelative(0.063f, -0.452f, 0.244f, -0.76f, 0.444f, -0.935f)
                curveToRelative(-1.554f, -0.176f, -3.188f, -0.776f, -3.188f, -3.456f)
                curveToRelative(0.0f, -0.763f, 0.273f, -1.388f, 0.72f, -1.876f)
                curveToRelative(-0.072f, -0.177f, -0.312f, -0.888f, 0.07f, -1.85f)
                curveToRelative(0.0f, 0.0f, 0.586f, -0.189f, 1.924f, 0.716f)
                arcTo(6.711f, 6.711f, 0.0f, false, true, 8.0f, 4.381f)
                curveToRelative(0.595f, 0.003f, 1.194f, 0.08f, 1.753f, 0.236f)
                curveToRelative(1.336f, -0.905f, 1.923f, -0.717f, 1.923f, -0.717f)
                curveToRelative(0.382f, 0.963f, 0.142f, 1.674f, 0.07f, 1.85f)
                curveToRelative(0.448f, 0.49f, 0.72f, 1.114f, 0.72f, 1.877f)
                curveToRelative(0.0f, 2.686f, -1.638f, 3.278f, -3.197f, 3.45f)
                curveToRelative(0.251f, 0.216f, 0.475f, 0.643f, 0.475f, 1.296f)
                curveToRelative(0.0f, 0.934f, -0.009f, 1.688f, -0.009f, 1.918f)
                curveToRelative(0.0f, 0.187f, 0.127f, 0.404f, 0.482f, 0.336f)
                arcTo(6.996f, 6.996f, 0.0f, false, false, 15.0f, 7.993f)
                arcTo(6.997f, 6.997f, 0.0f, false, false, 8.0f, 1.0f)
                close()
            }
        }
        .build()
        return _github!!
    }

private var _github: ImageVector? = null
