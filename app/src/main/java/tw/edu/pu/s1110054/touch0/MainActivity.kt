package tw.edu.pu.s1110054.touch0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
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

// 顏色常數
val ColorRed = Color(0xFFFF0000)
val ColorOrange = Color(0xFFFFA500)
val ColorYellow = Color(0xFFFFFF00)
val ColorGreen = Color(0xFF008000)
val ColorBlue = Color(0xFF0000FF)
val ColorIndigo = Color(0xFF4B0082)
val ColorPurple = Color(0xFF800080)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Touch0Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Greeting(name = "Android")
                        DrawCircle() // 確保這個繪製圓形的功能被呼叫
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
    // 記錄所有的觸摸點
    val paths = remember { mutableStateListOf<Points>() }

    // 設定顏色變數
    var currentColor = remember { mutableStateOf<Color>(Color.Black) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    // 每次觸摸時，新增點
                    paths.add(Points(offset.x, offset.y))
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val path = Path()
            // 遍歷每一個點，繪製路徑
            paths.forEachIndexed { index, point ->
                if (index == 0) {
                    path.moveTo(point.x, point.y)
                } else {
                    path.lineTo(point.x, point.y)
                }
            }

            // 設置顏色並繪製路徑
            drawPath(path, color = currentColor.value, style = androidx.compose.ui.graphics.drawscope.Stroke(width = 5f, join = StrokeJoin.Round))
        }
    }
}

data class Points(val x: Float, val y: Float)
