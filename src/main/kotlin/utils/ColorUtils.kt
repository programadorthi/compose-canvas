package utils

import androidx.compose.ui.graphics.Color
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.random.Random

fun randomColor(): Color =
    Color((Random.Default.nextFloat() * 0xFFFFFF).toInt()).copy(alpha = 1f)

// Credit and adapted from https://github.com/ajalt/colormath
fun hslToRgb(hue: Float, saturation: Float, lightness: Float): Color {
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

// Credit and adapted from https://github.com/ajalt/colormath
fun hsvToRgb(hue: Float, saturation: Float, value: Float): Color {
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