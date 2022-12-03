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
fun PieGraph(graphData: List<GraphData>, arcSize: Size = Size(200f, 200f)) {
  val animation = remember { Animatable(1f) }
  LaunchedEffect(animation) {
    animation.animateTo(
      360f,
      animationSpec =
      repeatable(1, animation = tween(durationMillis = 1500, easing = LinearEasing))
    )
  }
  var data = mutableListOf<Float>()
  graphData.forEach { data.add(it.data) }
  val total = remember { data.sum() }
  val totalWeights = remember { data.map { it * 100 / total } }
  val totalAngles = remember { totalWeights.map { 360 * it / 100 } }
  Canvas(modifier = Modifier.fillMaxSize()) {
    var startAngle = 0f
    var labelY = 0f
    for (i in graphData.indices) {
      var angleSize = (totalAngles[i] * 0.5f) + animation.value
      var topLeft = Offset((size.width - arcSize.width), (size.height - arcSize.height) / 2f)
      drawArc(
        color = graphData[i].color,
        startAngle = startAngle,
        sweepAngle = if (angleSize > totalAngles[i]) totalAngles[i] else angleSize,
        useCenter = true,
        topLeft = topLeft,
        size = arcSize,
      )
      drawArc(
        color = Color.Black,
        startAngle = startAngle,
        sweepAngle = if (angleSize > totalAngles[i]) totalAngles[i] else angleSize,
        useCenter = true,
        topLeft = topLeft,
        size = arcSize,
        style = Stroke(width = 2.dp.toPx())
      )
      drawIntoCanvas { ctx ->
        var rectX = 10f
        val labelStr = "${graphData[i].name} (${graphData[i].data.toString()})"
        var rectSize = textHeight(labelStr)
        var labelX = rectX + rectSize * 2f
        drawText(ctx, labelStr, Offset(labelX, labelY))
        drawRect(
          color = graphData[i].color,
          topLeft = Offset(rectX, labelY),
          size = Size(rectSize, rectSize),
          style = Fill
        )
        drawRect(
          color = Color.Black,
          topLeft = Offset(rectX, labelY),
          size = Size(rectSize, rectSize),
          style = Stroke(width = 2.dp.toPx())
        )
        labelY += textHeight(labelStr) * 2f
      }
      startAngle += totalAngles[i]
    }
  }
}
