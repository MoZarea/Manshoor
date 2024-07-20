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

public val OAuthProviderIcons.Google: ImageVector
    get() {
        if (_google != null) {
            return _google!!
        }
        _google = Builder(name = "Google", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 48.0f, viewportHeight = 48.0f).apply {
            path(fill = SolidColor(Color(0xFFFBBC05)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(10.3273f, 24.0f)
                curveTo(10.3273f, 22.4757f, 10.5804f, 21.0144f, 11.0323f, 19.6437f)
                lineTo(3.1235f, 13.6043f)
                curveTo(1.5821f, 16.7339f, 0.7136f, 20.2603f, 0.7136f, 24.0f)
                curveTo(0.7136f, 27.7365f, 1.581f, 31.2608f, 3.1202f, 34.3883f)
                lineTo(11.0248f, 28.3371f)
                curveTo(10.5772f, 26.9728f, 10.3273f, 25.5168f, 10.3273f, 24.0f)
            }
            path(fill = SolidColor(Color(0xFFEB4335)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(24.2136f, 10.1333f)
                curveTo(27.525f, 10.1333f, 30.5159f, 11.3067f, 32.8659f, 13.2267f)
                lineTo(39.7023f, 6.4f)
                curveTo(35.5364f, 2.7733f, 30.1955f, 0.5333f, 24.2136f, 0.5333f)
                curveTo(14.9269f, 0.5333f, 6.9454f, 5.8443f, 3.1235f, 13.6043f)
                lineTo(11.0323f, 19.6437f)
                curveTo(12.8546f, 14.112f, 18.0492f, 10.1333f, 24.2136f, 10.1333f)
            }
            path(fill = SolidColor(Color(0xFF34A853)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(24.2136f, 37.8667f)
                curveTo(18.0492f, 37.8667f, 12.8546f, 33.888f, 11.0323f, 28.3563f)
                lineTo(3.1235f, 34.3947f)
                curveTo(6.9454f, 42.1557f, 14.9269f, 47.4667f, 24.2136f, 47.4667f)
                curveTo(29.9455f, 47.4667f, 35.4178f, 45.4315f, 39.525f, 41.6181f)
                lineTo(32.0178f, 35.8144f)
                curveTo(29.8996f, 37.1488f, 27.2323f, 37.8667f, 24.2136f, 37.8667f)
            }
            path(fill = SolidColor(Color(0xFF4285F4)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(46.6455f, 24.0f)
                curveTo(46.6455f, 22.6133f, 46.4318f, 21.12f, 46.1114f, 19.7333f)
                lineTo(24.2136f, 19.7333f)
                lineTo(24.2136f, 28.8f)
                lineTo(36.8182f, 28.8f)
                curveTo(36.188f, 31.8912f, 34.4725f, 34.2677f, 32.0178f, 35.8144f)
                lineTo(39.525f, 41.6181f)
                curveTo(43.8393f, 37.6139f, 46.6455f, 31.6491f, 46.6455f, 24.0f)
            }
        }
        .build()
        return _google!!
    }

private var _google: ImageVector? = null
