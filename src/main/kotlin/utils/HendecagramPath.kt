package utils

import androidx.compose.ui.graphics.Path
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

private val path = Path()
fun hendecagramPath(): Path {
    // Create 11-pointed star
    if (path.isEmpty.not()) return path
    for (starIndex in 0 until 11) {
        val angle = (5 * starIndex * 2 * PI / 11).toFloat()
        val x = 100 * sin(angle)
        val y = -100 * cos(angle)
        if (starIndex == 0) {
            path.moveTo(x, y)
        } else {
            path.lineTo(x, y)
        }
    }
    path.close()
    return path
}