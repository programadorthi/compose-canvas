package `01_drawing_basics`

import androidx.compose.desktop.Window
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skija.Image
import java.net.HttpURLConnection
import java.net.URL

fun main() = Window {
    var localImage by remember { mutableStateOf<ImageBitmap?>(null) }
    var webImage by remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            // From resources
            localImage = imageFromResource("monkey.png")
            // From web
            val url = URL("http://www.panamaan.com/s/cc_images/cache_4299217.jpg?t=1496860569")
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            connection.inputStream.use { input ->
                webImage = Image.makeFromEncoded(input.readBytes()).asImageBitmap()
            }
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (webImage != null) {
            drawImage(
                image = webImage!!
            )
        }
        if (localImage != null && webImage != null) {
            drawImage(
                image = localImage!!,
                dstOffset = IntOffset(
                    x = ((size.width - webImage!!.width) / 2).toInt(),
                    y = (webImage!!.height)
                ),
                dstSize = IntSize(
                    width = webImage!!.width,
                    height = localImage!!.height
                )
            )
        }
    }
}