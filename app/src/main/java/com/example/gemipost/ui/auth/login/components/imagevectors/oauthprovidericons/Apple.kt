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

public val OAuthProviderIcons.Apple: ImageVector
    get() {
        if (_apple != null) {
            return _apple!!
        }
        _apple = Builder(name = "Apple", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 1024.0f, viewportHeight = 1024.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = null, strokeLineWidth = 0.0f,
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
                moveTo(407.2f, 722.1f)
                curveToRelative(-10.1f, -6.7f, -19.0f, -15.0f, -26.5f, -24.5f)
                curveToRelative(-8.2f, -9.9f, -15.7f, -20.3f, -22.7f, -31.0f)
                curveToRelative(-16.3f, -23.9f, -29.1f, -50.0f, -38.0f, -77.5f)
                curveToRelative(-10.7f, -32.0f, -15.8f, -62.7f, -15.8f, -92.7f)
                curveToRelative(0.0f, -33.5f, 7.2f, -62.7f, 21.4f, -87.2f)
                curveToRelative(10.4f, -19.2f, 26.0f, -35.2f, 44.8f, -46.5f)
                curveToRelative(18.1f, -11.3f, 39.2f, -17.5f, 60.6f, -17.9f)
                curveToRelative(7.5f, 0.0f, 15.6f, 1.1f, 24.1f, 3.2f)
                curveToRelative(6.2f, 1.7f, 13.6f, 4.5f, 22.8f, 7.9f)
                curveToRelative(11.7f, 4.5f, 18.1f, 7.2f, 20.3f, 7.9f)
                curveToRelative(6.8f, 2.6f, 12.6f, 3.6f, 17.1f, 3.6f)
                curveToRelative(3.4f, 0.0f, 8.3f, -1.1f, 13.8f, -2.8f)
                curveToRelative(3.1f, -1.1f, 9.0f, -3.0f, 17.3f, -6.6f)
                curveToRelative(8.2f, -3.0f, 14.8f, -5.5f, 19.9f, -7.5f)
                curveToRelative(7.9f, -2.3f, 15.5f, -4.5f, 22.4f, -5.5f)
                curveToRelative(8.3f, -1.3f, 16.6f, -1.7f, 24.5f, -1.1f)
                curveToRelative(15.1f, 1.1f, 29.0f, 4.3f, 41.4f, 9.0f)
                curveToRelative(21.7f, 8.7f, 39.3f, 22.4f, 52.4f, 41.8f)
                curveToRelative(-5.5f, 3.4f, -10.7f, 7.4f, -15.5f, 11.7f)
                curveToRelative(-10.4f, 9.2f, -19.2f, 20.0f, -26.2f, 32.1f)
                curveToRelative(-9.2f, 16.4f, -13.9f, 35.0f, -13.7f, 53.7f)
                curveToRelative(0.3f, 23.1f, 6.2f, 43.4f, 17.9f, 61.0f)
                curveToRelative(8.3f, 12.8f, 19.3f, 23.8f, 32.7f, 32.7f)
                curveToRelative(6.6f, 4.5f, 12.4f, 7.6f, 17.9f, 9.6f)
                curveToRelative(-2.6f, 8.0f, -5.4f, 15.8f, -8.6f, 23.5f)
                curveToRelative(-7.4f, 17.2f, -16.2f, 33.7f, -26.7f, 49.3f)
                curveToRelative(-9.2f, 13.4f, -16.5f, 23.5f, -22.0f, 30.1f)
                curveToRelative(-8.6f, 10.2f, -16.8f, 17.9f, -25.2f, 23.4f)
                curveToRelative(-9.2f, 6.1f, -19.9f, 9.3f, -31.0f, 9.3f)
                curveToRelative(-7.5f, 0.3f, -14.9f, -0.6f, -22.0f, -2.7f)
                curveToRelative(-6.2f, -2.0f, -12.3f, -4.3f, -18.3f, -6.9f)
                curveToRelative(-6.2f, -2.9f, -12.7f, -5.3f, -19.3f, -7.2f)
                curveToRelative(-8.1f, -2.1f, -16.4f, -3.2f, -24.8f, -3.1f)
                curveToRelative(-8.5f, 0.0f, -16.8f, 1.1f, -24.7f, 3.1f)
                curveToRelative(-6.6f, 1.9f, -13.0f, 4.2f, -19.3f, 6.9f)
                curveToRelative(-9.0f, 3.7f, -14.8f, 6.2f, -18.2f, 7.2f)
                curveToRelative(-6.9f, 2.0f, -14.0f, 3.3f, -21.1f, 3.7f)
                curveToRelative(-11.1f, 0.0f, -21.4f, -3.2f, -31.7f, -9.6f)
                verticalLineToRelative(-0.4f)
                close()
                moveTo(553.3f, 328.5f)
                curveToRelative(-14.5f, 7.2f, -28.3f, 10.3f, -42.1f, 9.3f)
                curveToRelative(-2.1f, -13.8f, 0.0f, -27.9f, 5.8f, -43.4f)
                curveToRelative(5.1f, -13.2f, 11.9f, -25.2f, 21.3f, -35.8f)
                curveToRelative(9.8f, -11.1f, 21.5f, -20.3f, 34.8f, -26.9f)
                curveToRelative(14.1f, -7.2f, 27.5f, -11.1f, 40.3f, -11.7f)
                curveToRelative(1.7f, 14.5f, 0.0f, 28.8f, -5.3f, 44.1f)
                curveToRelative(-4.9f, 13.6f, -12.1f, 26.2f, -21.3f, 37.5f)
                curveToRelative(-9.3f, 11.1f, -20.8f, 20.3f, -33.8f, 26.9f)
                horizontalLineToRelative(0.3f)
                close()
            }
        }
        .build()
        return _apple!!
    }

private var _apple: ImageVector? = null
