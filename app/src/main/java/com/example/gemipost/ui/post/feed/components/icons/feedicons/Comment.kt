package com.gp.socialapp.presentation.post.feed.components.icons.feedicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.post.feed.components.icons.FeedIcons

public val FeedIcons.Comment: ImageVector
    get() {
        if (_comment != null) {
            return _comment!!
        }
        _comment = Builder(name = "Comment", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 32.0f, viewportHeight = 32.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(30.0f, 22.0f)
                curveTo(30.0f, 23.463f, 28.473f, 25.0f, 27.0f, 25.0f)
                lineTo(19.0f, 25.0f)
                lineTo(16.0f, 29.0f)
                lineTo(13.0f, 25.0f)
                lineTo(5.0f, 25.0f)
                curveTo(3.527f, 25.0f, 2.0f, 23.463f, 2.0f, 22.0f)
                lineTo(2.0f, 5.0f)
                curveTo(2.0f, 3.537f, 3.527f, 2.0f, 5.0f, 2.0f)
                lineTo(27.0f, 2.0f)
                curveTo(28.473f, 2.0f, 30.0f, 3.537f, 30.0f, 5.0f)
                lineTo(30.0f, 22.0f)
                lineTo(30.0f, 22.0f)
                close()
                moveTo(26.667f, 0.0f)
                lineTo(5.333f, 0.0f)
                curveTo(2.388f, 0.0f, 0.0f, 2.371f, 0.0f, 5.297f)
                lineTo(0.0f, 22.187f)
                curveTo(0.0f, 25.111f, 2.055f, 27.0f, 5.0f, 27.0f)
                lineTo(11.639f, 27.0f)
                lineTo(16.0f, 32.001f)
                lineTo(20.361f, 27.0f)
                lineTo(27.0f, 27.0f)
                curveTo(29.945f, 27.0f, 32.0f, 25.111f, 32.0f, 22.187f)
                lineTo(32.0f, 5.297f)
                curveTo(32.0f, 2.371f, 29.612f, 0.0f, 26.667f, 0.0f)
                lineTo(26.667f, 0.0f)
                close()
            }
        }
        .build()
        return _comment!!
    }

private var _comment: ImageVector? = null
