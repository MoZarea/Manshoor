package com.gp.socialapp.presentation.post.feed.components.icons.feedicons

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
import com.gp.socialapp.presentation.post.feed.components.icons.FeedIcons

public val FeedIcons.Likefilled: ImageVector
    get() {
        if (_likefilled != null) {
            return _likefilled!!
        }
        _likefilled = Builder(name = "Likefilled", defaultWidth = 800.0.dp, defaultHeight =
                800.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0xFF1C274C)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(20.2699f, 16.265f)
                lineTo(20.9754f, 12.1852f)
                curveTo(21.1516f, 11.1662f, 20.368f, 10.2342f, 19.335f, 10.2342f)
                horizontalLineTo(14.1539f)
                curveTo(13.6404f, 10.2342f, 13.2494f, 9.7733f, 13.3325f, 9.266f)
                lineTo(13.9952f, 5.2214f)
                curveTo(14.1028f, 4.5644f, 14.0721f, 3.892f, 13.9049f, 3.2475f)
                curveTo(13.7664f, 2.7136f, 13.3545f, 2.285f, 12.8128f, 2.1109f)
                lineTo(12.6678f, 2.0643f)
                curveTo(12.3404f, 1.9592f, 11.9831f, 1.9836f, 11.6744f, 2.1324f)
                curveTo(11.3347f, 2.2961f, 11.0861f, 2.5947f, 10.994f, 2.9499f)
                lineTo(10.5183f, 4.7837f)
                curveTo(10.3669f, 5.3672f, 10.1465f, 5.9304f, 9.8622f, 6.4626f)
                curveTo(9.4468f, 7.2402f, 8.8047f, 7.8625f, 8.1371f, 8.4377f)
                lineTo(6.6984f, 9.6775f)
                curveTo(6.2927f, 10.0271f, 6.0797f, 10.5506f, 6.1258f, 11.0844f)
                lineTo(6.938f, 20.4771f)
                curveTo(7.0125f, 21.3386f, 7.7328f, 22.0f, 8.5966f, 22.0f)
                horizontalLineTo(13.2452f)
                curveTo(16.7265f, 22.0f, 19.6975f, 19.5744f, 20.2699f, 16.265f)
                close()
            }
            path(fill = SolidColor(Color(0xFF1C274C)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = EvenOdd) {
                moveTo(2.9677f, 9.4851f)
                curveTo(3.3689f, 9.4678f, 3.7126f, 9.7696f, 3.7472f, 10.1698f)
                lineTo(4.7188f, 21.4063f)
                curveTo(4.7812f, 22.1281f, 4.2127f, 22.7502f, 3.4867f, 22.7502f)
                curveTo(2.8029f, 22.7502f, 2.25f, 22.1954f, 2.25f, 21.5129f)
                verticalLineTo(10.2344f)
                curveTo(2.25f, 9.8328f, 2.5664f, 9.5024f, 2.9677f, 9.4851f)
                close()
            }
        }
        .build()
        return _likefilled!!
    }

private var _likefilled: ImageVector? = null
