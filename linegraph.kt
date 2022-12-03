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
fun LineChart(data: List<Float>){
  Card(
    modifier = Modifier.fillMaxWidth().height(36.dp).padding(16.dp), elevation = 10.dp
  ) {
    Column(
      modifier = Modifier.padding(16.dp).wrapContentSize(align = Alignment.BottomStart)
    ) {
      Canvas (
        modifier = Modifier.fillMaxWidth().height(200.dp)
      )
      {
        val distance = size.width / (data.size + 1)
        var currentX = 0f
        val maxValue : Float = (data.maxOrNull() ?: 0) as Float
        val points = mutableListOf<Float>()

        data.forEachIndexed{ index, item ->
          if (data.size >= index + 2) {
            val y0 = (maxValue - item) * (size.height / maxValue)
            points.add(y0)
            drawLine(start = Offset(currentX ,points[index]), end = Offset(currentX +
                    10, points[index + 1]),
              color = Color(0xFFFF0000),
              strokeWidth = 8f)
            currentX += distance
          }
        }
      }
    }
  }
}