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

public val FeedIcons.Attachment1: ImageVector
    get() {
        if (_attachment1 != null) {
            return _attachment1!!
        }
        _attachment1 = Builder(name = "Attachment1", defaultWidth = 800.0.dp, defaultHeight =
                800.0.dp, viewportWidth = 20.0f, viewportHeight = 20.0f).apply {
            path(fill = SolidColor(Color(0xFF000000)), stroke = SolidColor(Color(0x00000000)),
                    strokeLineWidth = 1.0f, strokeLineCap = Butt, strokeLineJoin = Miter,
                    strokeLineMiter = 4.0f, pathFillType = EvenOdd) {
                moveTo(16.008f, 4.0333f)
                lineTo(16.008f, 4.0333f)
                curveTo(16.5572f, 4.0353f, 17.002f, 4.4851f, 17.0f, 5.0381f)
                lineTo(16.9294f, 18.0024f)
                curveTo(16.9264f, 19.1084f, 16.0329f, 20.003f, 14.9335f, 20.0f)
                lineTo(4.9839f, 19.9769f)
                curveTo(3.8855f, 19.9739f, 2.997f, 19.0743f, 3.0f, 17.9683f)
                lineTo(3.0796f, 1.9986f)
                curveTo(3.0826f, 0.8916f, 3.9761f, -0.003f, 5.0745f, 0.0f)
                lineTo(11.0442f, 0.015f)
                curveTo(12.1436f, 0.0181f, 13.0321f, 0.9177f, 13.0291f, 2.0236f)
                lineTo(12.9605f, 13.9861f)
                curveTo(12.9575f, 15.0931f, 12.064f, 15.9878f, 10.9656f, 15.9847f)
                lineTo(8.9757f, 15.9788f)
                curveTo(7.8763f, 15.9758f, 6.9878f, 15.0761f, 6.9908f, 13.9691f)
                lineTo(7.0505f, 5.012f)
                curveTo(7.0524f, 4.459f, 7.4992f, 4.0122f, 8.0484f, 4.0132f)
                curveTo(8.5976f, 4.0153f, 9.0424f, 4.465f, 9.0404f, 5.0181f)
                lineTo(8.9837f, 12.9733f)
                curveTo(8.9827f, 13.5264f, 9.4264f, 13.9761f, 9.9756f, 13.9781f)
                curveTo(10.5258f, 13.9792f, 10.9726f, 13.5323f, 10.9736f, 12.9794f)
                lineTo(11.0362f, 3.0204f)
                curveTo(11.0372f, 2.4675f, 10.5935f, 2.0167f, 10.0433f, 2.0156f)
                lineTo(6.0645f, 2.0066f)
                curveTo(5.5143f, 2.0056f, 5.0675f, 2.4524f, 5.0665f, 3.0054f)
                lineTo(4.9929f, 16.9675f)
                curveTo(4.9909f, 17.5236f, 5.4376f, 17.9753f, 5.9888f, 17.9764f)
                lineTo(13.9405f, 17.9934f)
                curveTo(14.4917f, 17.9954f, 14.9404f, 17.5456f, 14.9424f, 16.9906f)
                lineTo(15.0101f, 5.032f)
                curveTo(15.0121f, 4.4791f, 15.4588f, 4.0313f, 16.008f, 4.0333f)
            }
        }
        .build()
        return _attachment1!!
    }

private var _attachment1: ImageVector? = null
