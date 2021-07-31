package `01_drawing_basics`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skija.Font
import org.jetbrains.skija.FontMgr
import org.jetbrains.skija.Paint

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val font = remember {
            FontMgr.getDefault()
                .matchFamily("Arial")
                .use {
                    Font(it.getTypeface(0), 40f)
                }
        }
        val fontSpacing = font.spacing
        val x = 20f
        val indent = 100
        var y = fontSpacing
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { cvs ->
                val canvas = cvs.nativeCanvas
                canvas.drawString("Canvas height and width:", x, y, font, Paint())
                y += fontSpacing
                canvas.drawString("%f x %f".format(size.width, size.height), x + indent, y, font, Paint())
                y += fontSpacing * 2
                canvas.drawString("Canvas size", x, y, font, Paint())
                y += fontSpacing
                canvas.drawString(size.toString(), x + indent, y, font, Paint())
            }
        }
    }
}