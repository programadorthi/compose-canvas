package `01_drawing_basics`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asDesktopBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.graphics.nativeCanvas
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skija.Image
import org.jetbrains.skija.Paint
import org.jetbrains.skija.Rect
import kotlin.math.min

fun main() = Window {
    var localImageOne by remember { mutableStateOf<ImageBitmap?>(null) }
    var localImageTwo by remember { mutableStateOf<ImageBitmap?>(null) }
    var progress by remember { mutableStateOf(1f) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            // From resources
            localImageOne = imageFromResource("SeatedMonkey.jpg")
            localImageTwo = imageFromResource("FacePalm.jpg")
        }
    }
    Column {
        Slider(
            value = progress,
            onValueChange = { value ->
                progress = value
            }
        )
        Canvas(
            modifier = Modifier
                .weight(weight = 1f)
                .fillMaxWidth()
        ) {
            localImageOne?.let { imageOne ->
                val scale = min(
                    a = size.width / imageOne.width,
                    b = size.height / imageOne.height
                )
                val rect = Rect.makeWH(
                    scale * imageOne.width,
                    scale * imageOne.height
                )
                val x = (size.width - rect.width) / 2
                val y = (size.height - rect.height) / 2
                rect.offset(x, y)
                drawIntoCanvas { cvs ->
                    val canvas = cvs.nativeCanvas
                    canvas.drawImageRect(
                        Image.makeFromBitmap(imageOne.asDesktopBitmap()),
                        rect,
                        Paint().setAlphaf(1f - progress)
                    )
                    localImageTwo?.let { imageTwo ->
                        canvas.drawImageRect(
                            Image.makeFromBitmap(imageTwo.asDesktopBitmap()),
                            rect,
                            Paint().setAlphaf(progress)
                        )
                    }
                }
            }
        }
    }
}