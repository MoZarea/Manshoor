package com.example.gemipost.ui.auth.login.components.imagevectors.oauthprovidericons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.example.gemipost.ui.auth.login.components.imagevectors.OAuthProviderIcons

public val OAuthProviderIcons.Linkedin: ImageVector
    get() {
        if (_linkedin != null) {
            return _linkedin!!
        }
        _linkedin = Builder(name = "Linkedin", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 48.0f, viewportHeight = 48.0f).apply {
            path(fill = SolidColor(Color(0xFF0077B5)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(24.0f, 24.0f)
                moveToRelative(-20.0f, 0.0f)
                arcToRelative(20.0f, 20.0f, 0.0f, true, true, 40.0f, 0.0f)
                arcToRelative(20.0f, 20.0f, 0.0f, true, true, -40.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(18.7747f, 14.2839f)
                curveTo(18.7747f, 15.529f, 17.8267f, 16.5366f, 16.3442f, 16.5366f)
                curveTo(14.9194f, 16.5366f, 13.9713f, 15.529f, 14.0007f, 14.2839f)
                curveTo(13.9713f, 12.9783f, 14.9193f, 12.0f, 16.3726f, 12.0f)
                curveTo(17.8267f, 12.0f, 18.7463f, 12.9783f, 18.7747f, 14.2839f)
                close()
                moveTo(14.1199f, 32.8191f)
                verticalLineTo(18.3162f)
                horizontalLineTo(18.6271f)
                verticalLineTo(32.8181f)
                horizontalLineTo(14.1199f)
                verticalLineTo(32.8191f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(22.2393f, 22.9446f)
                curveTo(22.2393f, 21.1357f, 22.1797f, 19.5935f, 22.1201f, 18.3182f)
                horizontalLineTo(26.0351f)
                lineTo(26.2432f, 20.305f)
                horizontalLineTo(26.3322f)
                curveTo(26.9254f, 19.3854f, 28.4079f, 17.9927f, 30.8101f, 17.9927f)
                curveTo(33.7752f, 17.9927f, 35.9995f, 19.9502f, 35.9995f, 24.219f)
                verticalLineTo(32.821f)
                horizontalLineTo(31.4922f)
                verticalLineTo(24.7838f)
                curveTo(31.4922f, 22.9144f, 30.8404f, 21.6399f, 29.2093f, 21.6399f)
                curveTo(27.9633f, 21.6399f, 27.2224f, 22.4999f, 26.9263f, 23.3297f)
                curveTo(26.8071f, 23.6268f, 26.7484f, 24.0412f, 26.7484f, 24.4574f)
                verticalLineTo(32.821f)
                horizontalLineTo(22.2411f)
                verticalLineTo(22.9446f)
                horizontalLineTo(22.2393f)
                close()
            }
        }
        .build()
        return _linkedin!!
    }

private var _linkedin: ImageVector? = null
