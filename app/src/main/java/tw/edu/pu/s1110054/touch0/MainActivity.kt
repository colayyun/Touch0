package tw.edu.pu.s1110054.touch0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import tw.edu.pu.s1110054.touch0.ui.theme.Touch0Theme

// 資料類，用於保存手指的路徑和顏色
data class FingerPath(val path: Path, val color: Color)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Touch0Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(name = "Android")
                        DrawCircle()
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = "多指觸控Compose實例",
                fontFamily = FontFamily(Font(R.font.finger)),
                fontSize = 25.sp,
                color = Color.Blue,
                modifier = modifier
            )

            Image(
                painter = painterResource(id = R.drawable.hand),
                contentDescription = "手掌圖片",
                alpha = 0.7f,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.Blue)
            )
        }
        Text(
            text = "作者：洪可芸",
            fontFamily = FontFamily(Font(R.font.finger)),
            fontSize = 25.sp,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DrawCircle() {
    // 保存所有手指的路徑
    val fingerPaths = remember { mutableStateListOf<FingerPath>() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .detectMultiTouch(fingerPaths) // 使用自定義的多指觸控偵測
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            fingerPaths.forEach { fingerPath ->
                drawPath(
                    path = fingerPath.path,
                    color = fingerPath.color,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(
                        width = 5f,
                        join = StrokeJoin.Round
                    )
                )
            }
        }
    }
}

// 擴展函數：偵測多指觸控
@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.detectMultiTouch(fingerPaths: MutableList<FingerPath>): Modifier {
    return this.pointerInput(Unit) {
        awaitPointerEventScope {
            val colors = listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow, Color.Magenta)
            while (true) {
                val event = awaitPointerEvent()
                event.changes.forEach { pointerInputChange ->
                    val position = pointerInputChange.position
                    val pointerId = pointerInputChange.id.hashCode()

                    if (pointerInputChange.pressed) {
                        // 如果是新手指，初始化路徑和顏色
                        if (pointerId >= fingerPaths.size) {
                            fingerPaths.add(
                                FingerPath(
                                    Path().apply { moveTo(position.x, position.y) },
                                    colors[pointerId % colors.size]
                                )
                            )
                        } else {
                            // 否則延續該路徑
                            fingerPaths[pointerId].path.lineTo(position.x, position.y)
                        }
                    }
                }
            }
        }
    }
}
