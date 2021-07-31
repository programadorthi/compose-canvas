package `03_transforms`

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asDesktopBitmap
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.jetbrains.skija.Image
import org.jetbrains.skija.Matrix33
import org.jetbrains.skija.Point
import utils.MatrixDisplay
import utils.TouchPoint

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        val recompose = currentRecomposeScope
        val matrixDisplay = remember { MatrixDisplay() }
        val touchPoints = remember { arrayOfNulls<TouchPoint>(size = 3) }
        var imageSize by remember { mutableStateOf(Size.Zero) }
        var localImage by remember { mutableStateOf<ImageBitmap?>(null) }
        var matrix by remember { mutableStateOf(Matrix33()) }
        var currentDragTouchPoint by remember { mutableStateOf<TouchPoint?>(null) }

        LaunchedEffect(Unit) {
            // From resources
            localImage = imageFromResource("SeatedMonkey.jpg")

            touchPoints[0] = TouchPoint(x = 100f, y = 100f)                       // upper-left corner
            touchPoints[1] = TouchPoint(x = localImage!!.width + 100f, y = 100f)  // upper-right corner
            touchPoints[2] = TouchPoint(x = 100f, y = localImage!!.height + 100f) // lower-left corner

            imageSize = Size(width = localImage!!.width.toFloat(), height = localImage!!.height.toFloat())
            matrix = computeMatrix(size = imageSize, touchPoints = touchPoints)
        }
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragCancel = {
                            currentDragTouchPoint = null
                        },
                        onDragEnd = {
                            currentDragTouchPoint = null
                        },
                        onDragStart = { point ->
                            val touchPoint = touchPoints
                                .firstOrNull { touchPoint -> touchPoint!!.pointInCircle(point) }
                            if (touchPoint != null) {
                                touchPoint.updateCenter(point)
                                matrix = computeMatrix(size = imageSize, touchPoints = touchPoints)
                                currentDragTouchPoint = touchPoint
                            }
                        },
                        onDrag = { change, _ ->
                            currentDragTouchPoint?.run {
                                updateCenter(change.position)
                                matrix = computeMatrix(size = imageSize, touchPoints = touchPoints)
                                recompose.invalidate() // Is it fine call invalidate manually?
                            }
                        }
                    )
                }
        ) {
            if (localImage != null) {
                val canvas = drawContext.canvas.nativeCanvas
                canvas.save()
                canvas.setMatrix(matrix)
                canvas.drawImage(
                    Image.makeFromBitmap(localImage!!.asDesktopBitmap()),
                    100f,
                    100f
                )
                canvas.restore()

                val matrixSize = matrixDisplay.measure(matrix)
                matrixDisplay.paint(
                    matrix = matrix,
                    canvas = canvas,
                    location = Point(
                        size.width - matrixSize.width,
                        size.height - matrixSize.height
                    )
                )
                touchPoints.forEach { it?.paint(canvas) }
            }
        }
    }
}

private fun computeMatrix(
    size: Size,
    touchPoints: Array<TouchPoint?>
): Matrix33 {
    val pointUpperLeft = touchPoints[0]!!.center
    val pointUpperRight = touchPoints[1]!!.center
    val pointLowerLeft = touchPoints[2]!!.center

    // Scale transform
    val scaleMatrix = Matrix33.makeScale(1 / size.width, 1 / size.height)

    // Affine transform
    val affineMatrix = Matrix33(
        pointUpperRight.x - pointUpperLeft.x, // scaleX
        pointUpperRight.y - pointUpperLeft.y, // skewY
        pointUpperLeft.x,                     // translateX
        pointLowerLeft.x - pointUpperLeft.x,  // skewX
        pointLowerLeft.y - pointUpperLeft.y,  // scaleY
        pointUpperLeft.y,                     // translateY
        0f,                                   // x-perspective
        0f,                                   // y-perspective
        1f                                    // z-perspective
    )

    val result = scaleMatrix.makeConcat(affineMatrix)
    return result.makeConcat(Matrix33.IDENTITY)
}
