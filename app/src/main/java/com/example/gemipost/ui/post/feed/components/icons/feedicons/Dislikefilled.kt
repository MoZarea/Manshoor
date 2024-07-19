package com.gp.socialapp.presentation.post.feed.components.icons.feedicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.post.feed.components.icons.FeedIcons

public val FeedIcons.Dislikefilled: ImageVector
    get() {
        if (_dislikefilled != null) {
            return _dislikefilled!!
        }
        _dislikefilled = Builder(name = "Dislikefilled", defaultWidth = 800.0.dp, defaultHeight =
                800.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF1C274C)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(20.2699f, 8.4851f)
                lineTo(20.9754f, 12.5648f)
                curveTo(21.1516f, 13.5838f, 20.368f, 14.5158f, 19.335f, 14.5158f)
                horizontalLineTo(14.1539f)
                curveTo(13.6404f, 14.5158f, 13.2494f, 14.9767f, 13.3325f, 15.484f)
                lineTo(13.9952f, 19.5286f)
                curveTo(14.1028f, 20.1857f, 14.0721f, 20.858f, 13.9049f, 21.5025f)
                curveTo(13.7664f, 22.0364f, 13.3545f, 22.465f, 12.8128f, 22.6391f)
                lineTo(12.6678f, 22.6856f)
                curveTo(12.3404f, 22.7908f, 11.9831f, 22.7663f, 11.6744f, 22.6176f)
                curveTo(11.3347f, 22.4539f, 11.0861f, 22.1553f, 10.994f, 21.8001f)
                lineTo(10.5183f, 19.9663f)
                curveTo(10.3669f, 19.3828f, 10.1465f, 18.8195f, 9.8622f, 18.2874f)
                curveTo(9.4468f, 17.5098f, 8.8047f, 16.8875f, 8.1371f, 16.3123f)
                lineTo(6.6984f, 15.0725f)
                curveTo(6.2927f, 14.7229f, 6.0797f, 14.1994f, 6.1258f, 13.6656f)
                lineTo(6.938f, 4.2729f)
                curveTo(7.0125f, 3.4114f, 7.7328f, 2.75f, 8.5966f, 2.75f)
                horizontalLineTo(13.2452f)
                curveTo(16.7265f, 2.75f, 19.6975f, 5.1756f, 20.2699f, 8.4851f)
                close()
            }
            path(fill = SolidColor(Color(0xFF1C274C)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(2.9677f, 15.2651f)
                curveTo(3.3689f, 15.2824f, 3.7126f, 14.9806f, 3.7472f, 14.5804f)
                lineTo(4.7188f, 3.3439f)
                curveTo(4.7812f, 2.6221f, 4.2127f, 2.0f, 3.4867f, 2.0f)
                curveTo(2.8029f, 2.0f, 2.25f, 2.5547f, 2.25f, 3.2373f)
                verticalLineTo(14.5158f)
                curveTo(2.25f, 14.9174f, 2.5664f, 15.2478f, 2.9677f, 15.2651f)
                close()
            }
        }
        .build()
        return _dislikefilled!!
    }

private var _dislikefilled: ImageVector? = null
