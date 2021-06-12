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
import utils.hslToRgb
import utils.hsvToRgb

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
                text = " RGB = %x ".format(hslColor.toArgb()),
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
                text = " RGB = %x ".format(hsvColor.toArgb()),
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier
                    .align(alignment = Alignment.Center)
                    .background(color = Color.Black)
            )
        }
    }
}