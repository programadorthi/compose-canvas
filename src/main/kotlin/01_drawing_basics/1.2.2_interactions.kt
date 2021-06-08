package `01_drawing_basics`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.sp
import kotlin.math.floor
import kotlin.math.roundToInt

fun main() = Window {
    var hueSlider by remember { mutableStateOf(0f) }
    var saturationSlider by remember { mutableStateOf(100f) }
    var lightnessSlider by remember { mutableStateOf(50f) }
    var hueValue by remember { mutableStateOf(100f) }
    val hslColor = hslToRgb(
        hue = hueSlider,
        saturation = saturationSlider,
        lightness = lightnessSlider
    )
    val hsvColor = hsvToRgb(
        hue = hueSlider,
        saturation = saturationSlider,
        value = hueValue
    )
    Column {
        Slider(
            value = hueSlider,
            valueRange = 0f..360f,
            onValueChange = { value ->
                hueSlider = value
            }
        )
        Text(
            text = "Hue = ${hueSlider.toInt()}",
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally
            )
        )
        Slider(
            value = saturationSlider,
            valueRange = 0f..100f,
            onValueChange = { value ->
                saturationSlider = value
            }
        )
        Text(
            text = "Saturation = ${saturationSlider.toInt()}",
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally
            )
        )
        Slider(
            value = lightnessSlider,
            valueRange = 0f..100f,
            onValueChange = { value ->
                lightnessSlider = value
            }
        )
        Text(
            text = "Lightness = ${lightnessSlider.toInt()}",
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally
            )
        )
        Box(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxWidth()
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRect(color = hslColor)
            }
            Text(
                text = "RGB = %x".format(hslColor.toArgb()),
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .background(color = Color.Black)
            )
        }
        Slider(
            value = hueValue,
            valueRange = 0f..100f,
            onValueChange = { value ->
                hueValue = value
            }
        )
        Text(
            text = "Value = ${hueValue.toInt()}",
            modifier = Modifier.align(
                alignment = Alignment.CenterHorizontally
            )
        )
        Box(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxWidth()
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                drawRect(color = hsvColor)
            }
            Text(
                text = "RGB = %x".format(hsvColor.toArgb()),
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .background(color = Color.Black)
            )
        }
    }
}

// Credit and adapted from https://github.com/ajalt/colormath
private fun hslToRgb(hue: Float, saturation: Float, lightness: Float): Color {
    val h = hue / 360.0
    val s = saturation / 100.0
    val l = lightness / 100.0
    if (s == 0.0) {
        val v = (l * 255).roundToInt()
        return Color(v, v, v)
    }
    val t2 = when {
        l < 0.5 -> l * (1 + s)
        else -> l + s - l * s
    }
    val t1 = 2 * l - t2
    val rgb = arrayOf(0.0, 0.0, 0.0)
    for (i in 0..2) {
        var t3: Double = h + 1.0 / 3.0 * -(i - 1.0)
        if (t3 < 0) t3 += 1.0
        if (t3 > 1) t3 -= 1.0
        val v: Double = when {
            6 * t3 < 1 -> t1 + (t2 - t1) * 6 * t3
            2 * t3 < 1 -> t2
            3 * t3 < 2 -> t1 + (t2 - t1) * (2.0 / 3.0 - t3) * 6
            else -> t1
        }
        rgb[i] = v
    }
    return Color(
        (rgb[0] * 255).roundToInt(),
        (rgb[1] * 255).roundToInt(),
        (rgb[2] * 255).roundToInt()
    )
}
private fun hsvToRgb(hue: Float, saturation: Float, value: Float): Color {
    val h = hue / 60
    val s = saturation / 100
    var v = value / 100
    val hi = floor(h) % 6

    val f = h - floor(h)
    val p = 255 * v * (1 - s)
    val q = 255 * v * (1 - (s * f))
    val t = 255 * v * (1 - (s * (1 - f)))
    v *= 255

    val (r, g, b) = when (hi.roundToInt()) {
        0 -> Triple(v, t, p)
        1 -> Triple(q, v, p)
        2 -> Triple(p, v, t)
        3 -> Triple(p, q, v)
        4 -> Triple(t, p, v)
        else -> Triple(v, p, q)
    }
    return Color(r.roundToInt(), g.roundToInt(), b.roundToInt())
}