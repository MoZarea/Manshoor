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

public val OAuthProviderIcons.Microsoft: ImageVector
    get() {
        if (_microsoft != null) {
            return _microsoft!!
        }
        _microsoft = Builder(name = "Microsoft", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFFF35325)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(1.0f, 1.0f)
                horizontalLineToRelative(6.5f)
                verticalLineToRelative(6.5f)
                horizontalLineTo(1.0f)
                verticalLineTo(1.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF81BC06)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.5f, 1.0f)
                horizontalLineTo(15.0f)
                verticalLineToRelative(6.5f)
                horizontalLineTo(8.5f)
                verticalLineTo(1.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFF05A6F0)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(1.0f, 8.5f)
                horizontalLineToRelative(6.5f)
                verticalLineTo(15.0f)
                horizontalLineTo(1.0f)
                verticalLineTo(8.5f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFFBA08)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(8.5f, 8.5f)
                horizontalLineTo(15.0f)
                verticalLineTo(15.0f)
                horizontalLineTo(8.5f)
                verticalLineTo(8.5f)
                close()
            }
        }
        .build()
        return _microsoft!!
    }

private var _microsoft: ImageVector? = null
