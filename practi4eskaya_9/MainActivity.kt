package com.example.practi4eskaya_9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.dp

// Кастомный шрифт
val CustomFontFamily = FontFamily(
    Font(R.font.roboto_regular, FontWeight.Normal),
    Font(R.font.roboto_regular, FontWeight.Bold)
)

// Цветовая схема
val CustomColorScheme = lightColorScheme(
    primary = Color(0xFF6200EA),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC5),
    background = Color(0xFFF3F4F6),
    onBackground = Color.Black
)

// Типографика с кастомным шрифтом
val CustomTypography = Typography(
    bodyLarge = TextStyle(
        fontSize = 20.sp,
        fontFamily = CustomFontFamily,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = Color.Black
    )
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = CustomColorScheme,
                typography = CustomTypography
            ) {
                StudentInfoScreen()
            }
        }
    }
}

@Composable
fun StudentInfoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Фон изображения
        Image(
            painter = painterResource(id = R.drawable.background_image), // Укажите свой фон в ресурсах
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
        // Центрирование текста
        Text(
            text = "ФИО: Некрасов Глеб Андреевич\nНомер группы: ИКБО-28-22",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStudentInfoScreen() {
    MaterialTheme(
        colorScheme = CustomColorScheme,
        typography = CustomTypography
    ) {
        StudentInfoScreen()
    }
}
