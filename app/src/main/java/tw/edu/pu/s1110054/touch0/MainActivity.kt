package tw.edu.pu.s1110054.touch0

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import tw.edu.pu.s1110054.touch0.ui.theme.Touch0Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Touch0Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(
        text = "Hello, $name! Welcome to Jetpack Compose!",
        fontFamily = FontFamily(Font(R.font.finger)), // 確保 finger.ttf 存在於 res/font 資料夾
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

