package com.gp.socialapp.presentation.material.components.imageVectors.materialicon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.material.components.imageVectors.MaterialIcon

public val MaterialIcon.File: ImageVector
    get() {
        if (_file != null) {
            return _file!!
        }
        _file = Builder(
            name = "File", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
            viewportWidth = 24.0f, viewportHeight = 24.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF09244B)), stroke = SolidColor(Color(0x00000000)),
                strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                strokeLineMiter = 4.0f, pathFillType = EvenOdd
            ) {
                moveTo(12.0f, 2.0f)
                lineTo(12.0f, 8.5f)
                curveTo(12.0f, 9.3284f, 12.6716f, 10.0f, 13.5f, 10.0f)
                lineTo(20.0f, 10.0f)
                lineTo(20.0f, 20.0f)
                curveTo(20.0f, 21.1046f, 19.1046f, 22.0f, 18.0f, 22.0f)
                lineTo(6.0f, 22.0f)
                curveTo(4.8954f, 22.0f, 4.0f, 21.1046f, 4.0f, 20.0f)
                lineTo(4.0f, 4.0f)
                curveTo(4.0f, 2.8954f, 4.8954f, 2.0f, 6.0f, 2.0f)
                lineTo(12.0f, 2.0f)
                close()
                moveTo(12.0f, 18.0f)
                curveTo(11.4477f, 18.0f, 11.0f, 18.4477f, 11.0f, 19.0f)
                curveTo(11.0f, 19.5523f, 11.4477f, 20.0f, 12.0f, 20.0f)
                curveTo(12.5523f, 20.0f, 13.0f, 19.5523f, 13.0f, 19.0f)
                curveTo(13.0f, 18.4477f, 12.5523f, 18.0f, 12.0f, 18.0f)
                close()
                moveTo(12.0f, 12.0f)
                curveTo(10.6193f, 12.0f, 9.5f, 13.1193f, 9.5f, 14.5f)
                curveTo(9.5f, 15.0523f, 9.9477f, 15.5f, 10.5f, 15.5f)
                curveTo(11.0523f, 15.5f, 11.5f, 15.0523f, 11.5f, 14.5f)
                curveTo(11.5f, 14.2239f, 11.7239f, 14.0f, 12.0f, 14.0f)
                curveTo(12.2761f, 14.0f, 12.5f, 14.2239f, 12.5f, 14.5f)
                curveTo(12.5f, 14.6589f, 12.427f, 14.8002f, 12.3087f, 14.8934f)
                curveTo(12.0896f, 15.0661f, 11.7792f, 15.3172f, 11.5252f, 15.6297f)
                curveTo(11.351f, 15.844f, 11.1406f, 16.2239f, 11.1406f, 16.5781f)
                curveTo(11.1406f, 16.9324f, 11.375f, 17.4219f, 12.0f, 17.4219f)
                curveTo(12.4015f, 17.4219f, 12.8466f, 17.0478f, 13.2543f, 16.705f)
                curveTo(13.3543f, 16.6209f, 13.4521f, 16.5387f, 13.5464f, 16.4644f)
                curveTo(14.1254f, 16.0083f, 14.5f, 15.2976f, 14.5f, 14.5f)
                curveTo(14.5f, 13.1193f, 13.3807f, 12.0f, 12.0f, 12.0f)
                close()
                moveTo(14.0f, 2.0434f)
                curveTo(14.3759f, 2.123f, 14.7241f, 2.3099f, 15.0f, 2.5858f)
                lineTo(19.4142f, 7.0f)
                curveTo(19.6901f, 7.2759f, 19.8771f, 7.6241f, 19.9566f, 8.0f)
                lineTo(14.0f, 8.0f)
                lineTo(14.0f, 2.0434f)
                close()
            }
        }
            .build()
        return _file!!
    }

private var _file: ImageVector? = null
