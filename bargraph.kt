import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import drawText
import textHeight
import textSize
import textWidth
import kotlin.math.max

data class GraphData(var data: Float, var name: String, var color: Color)

@Composable
fun BarGraph(
    data: List<GraphData>,
    stepScale: Int = 25,
    spacing: Float = 25f,
    barsWidth: Float = 25f,
    barsGap: Float = 100f
) {
    var maxData = 0f
    data.forEach { maxData = max(maxData, it.data) }
    val roundedMax = Math.ceil((maxData / stepScale).toDouble()) * stepScale.toDouble()
    var yValues = mutableListOf<Int>()
    for (i in 0..roundedMax.toInt()) {
        if (i == 0 || i % stepScale == 0) {
            yValues.add(i)
        }
    }
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawIntoCanvas { canvas ->
            val offsetY = size.height * 0.9f
            var xpos = textWidth(maxData.toString())
            var ypos = offsetY - textHeight("0")
            var topOfGraph = 0f
            yValues.forEach {
                drawText(canvas, it.toString(), Offset(0f, ypos))
                topOfGraph = ypos + textHeight("0")
                drawLine(Color(0xCC000000), Offset(0f, topOfGraph), Offset(size.width, topOfGraph))
                ypos -= textHeight(it.toString()) + spacing.toFloat()
            }
            var index = 0
            data.forEach {
                var percentage = 100f / ((roundedMax.toFloat() / it.data) * 100f)
                var topY = offsetY - (topOfGraph + ((offsetY - topOfGraph) * percentage) - topOfGraph)
                if (topY < 0f) {
                    topY = 0f
                }
                drawRect(
                    color = it.color,
                    topLeft = Offset(xpos, topY),
                    size = Size(barsWidth, offsetY - topY),
                    style = Fill
                )
                drawRect(
                    color = Color.Black,
                    topLeft = Offset(xpos, topY),
                    size = Size(barsWidth, offsetY - topY),
                    style = Stroke(width = 2.dp.toPx())
                )
                drawText(
                    canvas,
                    it.name,
                    Offset(
                        ((xpos + barsWidth / 2) - (textWidth(it.name) / 2)),
                        offsetY + textHeight(it.name) - textSize
                    )
                )
                xpos += barsGap
                index += 1
            }
        }
    }
}