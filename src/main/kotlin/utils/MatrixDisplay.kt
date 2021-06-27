package utils

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import org.jetbrains.skija.*
import kotlin.math.max

class MatrixDisplay {
    private val font = FontMgr.getDefault()
        .matchFamily("Arial")
        .use {
            Font(it.getTypeface(0)).apply {
                size = 48f
            }
        }
    private val matrixPaint = Paint().apply {
        color = Color.Black.toArgb()
        mode = PaintMode.FILL
        strokeWidth = 2f
    }

    var perspectiveFormat: String = "%.0f"

    fun measure(matrix: Matrix33): Size =
        measureAndPaint(matrix = matrix, location = Point.ZERO, canvas = null, paint = false)

    fun paint(matrix: Matrix33, location: Point, canvas: Canvas): Size =
        measureAndPaint(matrix = matrix, location = location, canvas = canvas, paint = true)

    private fun measureAndPaint(
        matrix: Matrix33,
        location: Point,
        canvas: Canvas?,
        paint: Boolean
    ): Size {
        val texts = Array(matrix.mat.size) { "" }
        val bounds = Array(matrix.mat.size) { Rect.makeWH(0f, 0f) }
        val widths = Array(3) { 0f }
        for (index in matrix.mat.indices) {
            val row = index % 3
            val column = index / 3
            // Format string differently based on row
            val template = when {
                row == 2 -> "%.0f"
                column == 2 -> perspectiveFormat
                else -> "%.2f"
            }
            texts[index] = template.format(matrix.mat[index])
            // Measure string with a '-' even if one is not present
            val isNegative = texts[index][0] == '-'
            val text = "${if (isNegative) "" else "-"}${texts[index]}"
            bounds[index] = font.measureText(text, matrixPaint)
            // Get maximum width for each column
            widths[column] = max(widths[column], bounds[index].width)
            // Measure the text again without the '-' in front
            bounds[index] = font.measureText(texts[index], matrixPaint)
        }

        val horzGap = font.size
        val horzMarg = font.size
        val vertMarg = font.spacing / 4f
        val totalWidth = widths[0] + widths[1] + widths[2] + 2 * horzGap + 2 * horzMarg
        val totalHeight = 3 * font.spacing + 2 * vertMarg

        if (paint) {
            val savedMode = matrixPaint.mode
            for (index in matrix.mat.indices) {
                val row = index % 3
                val column = index / 3
                var x = location.x + horzMarg
                for (colIndex in 0 until column) {
                    x += widths[colIndex] + horzGap
                }
                var y = location.y + vertMarg + row * font.spacing
                x += widths[column] - bounds[index].width
                y += (font.spacing - bounds[index].height) / 2 - bounds[index].top
                matrixPaint.mode = PaintMode.FILL
                canvas?.drawString(
                    texts[index],
                    x,
                    y,
                    font,
                    matrixPaint
                )
            }
            matrixPaint.mode = PaintMode.STROKE
            canvas?.drawLine(
                location.x + horzMarg / 2,
                location.y + vertMarg,
                location.x + horzMarg / 2,
                location.y + totalHeight - vertMarg,
                matrixPaint
            )
            canvas?.drawLine(
                location.x + totalWidth - horzMarg / 2,
                location.y + vertMarg,
                location.x + totalWidth - horzMarg / 2,
                location.y + totalHeight - vertMarg,
                matrixPaint
            )
            matrixPaint.mode = savedMode
        }
        return Size(width = totalWidth, height = totalHeight)
    }
}