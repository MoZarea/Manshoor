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

public val OAuthProviderIcons.Facebook: ImageVector
    get() {
        if (_facebook != null) {
            return _facebook!!
        }
        _facebook = Builder(name = "Facebook", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 16.0f, viewportHeight = 16.0f).apply {
            path(fill = SolidColor(Color(0xFF1877F2)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(15.0f, 8.0f)
                arcToRelative(7.0f, 7.0f, 0.0f, false, false, -7.0f, -7.0f)
                arcToRelative(7.0f, 7.0f, 0.0f, false, false, -1.094f, 13.915f)
                verticalLineToRelative(-4.892f)
                horizontalLineTo(5.13f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(1.777f)
                verticalLineTo(6.458f)
                curveToRelative(0.0f, -1.754f, 1.045f, -2.724f, 2.644f, -2.724f)
                curveToRelative(0.766f, 0.0f, 1.567f, 0.137f, 1.567f, 0.137f)
                verticalLineToRelative(1.723f)
                horizontalLineToRelative(-0.883f)
                curveToRelative(-0.87f, 0.0f, -1.14f, 0.54f, -1.14f, 1.093f)
                verticalLineTo(8.0f)
                horizontalLineToRelative(1.941f)
                lineToRelative(-0.31f, 2.023f)
                horizontalLineTo(9.094f)
                verticalLineToRelative(4.892f)
                arcTo(7.001f, 7.001f, 0.0f, false, false, 15.0f, 8.0f)
                close()
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(10.725f, 10.023f)
                lineTo(11.035f, 8.0f)
                horizontalLineTo(9.094f)
                verticalLineTo(6.687f)
                curveToRelative(0.0f, -0.553f, 0.27f, -1.093f, 1.14f, -1.093f)
                horizontalLineToRelative(0.883f)
                verticalLineTo(3.87f)
                reflectiveCurveToRelative(-0.801f, -0.137f, -1.567f, -0.137f)
                curveToRelative(-1.6f, 0.0f, -2.644f, 0.97f, -2.644f, 2.724f)
                verticalLineTo(8.0f)
                horizontalLineTo(5.13f)
                verticalLineToRelative(2.023f)
                horizontalLineToRelative(1.777f)
                verticalLineToRelative(4.892f)
                arcToRelative(7.037f, 7.037f, 0.0f, false, false, 2.188f, 0.0f)
                verticalLineToRelative(-4.892f)
                horizontalLineToRelative(1.63f)
                close()
            }
        }
        .build()
        return _facebook!!
    }

private var _facebook: ImageVector? = null
