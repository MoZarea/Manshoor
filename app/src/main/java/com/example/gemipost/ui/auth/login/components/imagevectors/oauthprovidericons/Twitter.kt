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

public val OAuthProviderIcons.Twitter: ImageVector
    get() {
        if (_twitter != null) {
            return _twitter!!
        }
        _twitter = Builder(name = "Twitter", defaultWidth = 800.0.dp, defaultHeight = 800.0.dp,
                viewportWidth = 589.0f, viewportHeight = 589.0f).apply {
            path(fill = SolidColor(Color(0xFF2daae1)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(294.5f, 294.5f)
                moveToRelative(-294.5f, 0.0f)
                arcToRelative(294.5f, 294.5f, 0.0f, true, true, 589.0f, 0.0f)
                arcToRelative(294.5f, 294.5f, 0.0f, true, true, -589.0f, 0.0f)
            }
            path(fill = SolidColor(Color(0xFFffffff)), stroke = null, strokeLineWidth = 0.0f,
                    strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                    pathFillType = NonZero) {
                moveTo(483.329f, 177.353f)
                curveToRelative(-13.891f, 6.164f, -28.811f, 10.331f, -44.498f, 12.204f)
                curveToRelative(16.01f, -9.587f, 28.275f, -24.779f, 34.066f, -42.86f)
                arcToRelative(154.78f, 154.78f, 0.0f, false, true, -49.209f, 18.801f)
                curveToRelative(-14.125f, -15.056f, -34.267f, -24.456f, -56.551f, -24.456f)
                curveToRelative(-42.773f, 0.0f, -77.462f, 34.675f, -77.462f, 77.473f)
                curveToRelative(0.0f, 6.064f, 0.683f, 11.98f, 1.996f, 17.66f)
                curveToRelative(-64.389f, -3.236f, -121.474f, -34.079f, -159.684f, -80.945f)
                curveToRelative(-6.672f, 11.446f, -10.491f, 24.754f, -10.491f, 38.953f)
                curveToRelative(0.0f, 26.875f, 13.679f, 50.587f, 34.464f, 64.477f)
                arcToRelative(77.122f, 77.122f, 0.0f, false, true, -35.097f, -9.686f)
                verticalLineToRelative(0.979f)
                curveToRelative(0.0f, 37.54f, 26.701f, 68.842f, 62.145f, 75.961f)
                curveToRelative(-6.511f, 1.784f, -13.344f, 2.716f, -20.413f, 2.716f)
                curveToRelative(-4.998f, 0.0f, -9.847f, -0.473f, -14.584f, -1.364f)
                curveToRelative(9.859f, 30.769f, 38.471f, 53.166f, 72.363f, 53.799f)
                curveToRelative(-26.515f, 20.785f, -59.925f, 33.175f, -96.212f, 33.175f)
                curveToRelative(-6.25f, 0.0f, -12.427f, -0.373f, -18.491f, -1.104f)
                curveToRelative(34.291f, 21.988f, 75.006f, 34.824f, 118.759f, 34.824f)
                curveToRelative(142.496f, 0.0f, 220.428f, -118.052f, 220.428f, -220.428f)
                curveToRelative(0.0f, -3.361f, -0.074f, -6.697f, -0.236f, -10.021f)
                arcToRelative(157.855f, 157.855f, 0.0f, false, false, 38.707f, -40.158f)
                close()
            }
        }
        .build()
        return _twitter!!
    }

private var _twitter: ImageVector? = null
