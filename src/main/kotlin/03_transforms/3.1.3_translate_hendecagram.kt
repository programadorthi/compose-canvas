package `03_transforms`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.window.application
import utils.hendecagramPath
import utils.randomColor

fun main() = application {
    androidx.compose.ui.window.Window(onCloseRequest = ::exitApplication) {
        val path = hendecagramPath()
        Canvas(modifier = Modifier.fillMaxSize()) {
            for (x in 100 until (size.width.toInt() + 100) step 200) {
                for (y in 100 until (size.height.toInt() + 100) step 200) {
                    withTransform(
                        transformBlock = {
                            translate(left = x.toFloat(), top = y.toFloat())
                        },
                        drawBlock = {
                            drawPath(
                                path = path,
                                color = randomColor()
                            )
                        }
                    )
                }
            }
        }
    }
}