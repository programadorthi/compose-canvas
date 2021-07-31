package `03_transforms`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asDesktopPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skija.Matrix33
import org.jetbrains.skija.Paint
import org.jetbrains.skija.PaintMode
import utils.hendecagramPath

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val paint = remember {
            Paint().apply {
                color = Color.Magenta.toArgb()
                mode = PaintMode.STROKE
                strokeWidth = 5f
            }
        }
        var path by remember { mutableStateOf(hendecagramPath().asDesktopPath()) }
        LaunchedEffect(Unit) {
            var matrix = Matrix33.makeScale(3f, 3f)
            matrix = matrix.makeConcat(Matrix33.makeTranslate(100f, 100f))
            matrix = matrix.makeConcat(Matrix33.makeRotate(360f / 22))

            path = path.transform(matrix)
        }
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { cvs ->
                val canvas = cvs.nativeCanvas
                canvas.drawPath(path, paint)
            }
        }
    }
}