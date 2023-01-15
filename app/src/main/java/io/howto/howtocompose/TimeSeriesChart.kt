// Adopted from https://github.com/aqua30/GraphCompose
package io.howto.howtocompose

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.himanshoe.charty.common.axis.AxisConfig
import com.himanshoe.charty.line.LineChart
import com.himanshoe.charty.line.config.LineConfig
import com.himanshoe.charty.line.model.LineData
import kotlin.random.Random

@Composable
fun TimeSeriesChart(label: String, readings: List<Number>) {
    Row(
        modifier = Modifier
            .border(2.dp, Color.Black)
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        LineChart(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(100.dp),
            color = Color.Green,
            lineData = readings.map {
                LineData("", it.toFloat())
            },
            lineConfig = LineConfig(hasSmoothCurve = true),
            axisConfig = AxisConfig(
                showAxis = false,
                isAxisDashed = false,
                showUnitLabels = false,
                showXLabels = false,
                textColor = Color.Black
            )
        )
        Text(label)
    }
}


@Preview(showBackground = true)
@Composable
fun DemoChart() {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Box(modifier = Modifier.padding(vertical = 4.dp)) {
            TimeSeriesChart(label = "血氧水平", readings = (0 until 20).map {
                Random.nextDouble(150.0, 250.0)
            })
        }
        Box(modifier = Modifier.padding(vertical = 4.dp)) {
            TimeSeriesChart(label = "血氧水平", readings = (0 until 20).map {
                Random.nextDouble(150.0, 250.0)
            })
        }
        Box(modifier = Modifier.padding(vertical = 4.dp)) {
            TimeSeriesChart(label = "血氧水平", readings = (0 until 20).map {
                Random.nextDouble(150.0, 250.0)
            })
        }
    }
}