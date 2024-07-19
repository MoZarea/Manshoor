package com.gp.socialapp.presentation.post.feed.components.icons.feedicons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Round
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp
import com.gp.socialapp.presentation.post.feed.components.icons.FeedIcons

public val FeedIcons.Attachment2: ImageVector
    get() {
        if (_attachment2 != null) {
            return _attachment2!!
        }
        _attachment2 = Builder(name = "Attachment2", defaultWidth = 800.0.dp, defaultHeight =
                800.0.dp, viewportWidth = 24.0f, viewportHeight = 24.0f).apply {
            path(fill = SolidColor(Color(0x00000000)), stroke = SolidColor(Color(0xFF000000)),
                    strokeLineWidth = 1.5f, strokeLineCap = Round, strokeLineJoin =
                    StrokeJoin.Companion.Round, strokeLineMiter = 4.0f, pathFillType = NonZero) {
                moveTo(20.0f, 10.9696f)
                lineTo(11.9628f, 18.5497f)
                curveTo(10.9782f, 19.4783f, 9.6427f, 20.0f, 8.2503f, 20.0f)
                curveTo(6.8578f, 20.0f, 5.5224f, 19.4783f, 4.5378f, 18.5497f)
                curveTo(3.5531f, 17.6211f, 3.0f, 16.3616f, 3.0f, 15.0483f)
                curveTo(3.0f, 13.7351f, 3.5531f, 12.4756f, 4.5378f, 11.547f)
                moveTo(14.429f, 6.8867f)
                lineTo(7.004f, 13.8812f)
                curveTo(6.6758f, 14.1907f, 6.4914f, 14.6106f, 6.4914f, 15.0483f)
                curveTo(6.4914f, 15.4861f, 6.6758f, 15.9059f, 7.004f, 16.2154f)
                curveTo(7.3322f, 16.525f, 7.7774f, 16.6989f, 8.2415f, 16.6989f)
                curveTo(8.7057f, 16.6989f, 9.1508f, 16.525f, 9.479f, 16.2154f)
                lineTo(13.502f, 12.4254f)
                moveTo(8.5564f, 7.7569f)
                lineTo(12.575f, 3.9669f)
                curveTo(13.2314f, 3.3478f, 14.1217f, 3.0f, 15.05f, 3.0f)
                curveTo(15.9783f, 3.0f, 16.8686f, 3.3478f, 17.525f, 3.9669f)
                curveTo(18.1814f, 4.5859f, 18.5502f, 5.4256f, 18.5502f, 6.3011f)
                curveTo(18.5502f, 7.1766f, 18.1814f, 8.0163f, 17.525f, 8.6354f)
                lineTo(16.5f, 9.601f)
            }
        }
        .build()
        return _attachment2!!
    }

private var _attachment2: ImageVector? = null
