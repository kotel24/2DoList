package ru.sumin.a2dolist.presentation.welcome

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.sumin.a2dolist.R
import ru.sumin.a2dolist.presentation.ui.theme.LightBackgroundColor
import ru.sumin.a2dolist.presentation.ui.theme.MainBlue


@Composable
fun WelcomeScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = LightBackgroundColor // Основной светлый фон
    ) {
        // Фоновые фигуры
        BackgroundShapes(modifier = Modifier.fillMaxSize())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp), // Общий горизонтальный отступ
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(180.dp)) // Отступ сверху

            // Логотип/Заголовок
            Text(
                text = "2DoList",
                color = Color.Black,
                fontSize = 80.sp,
                fontFamily = FontFamily( Font(R.font.itim_regular, FontWeight.Normal)),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Приветствие
            Text(
                text = "Wellcome!",
                color = Color.Black,
                fontSize = 40.sp,
                fontFamily = FontFamily( Font(R.font.itim_regular, FontWeight.Normal)),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Центральная иллюстрация
            Image(
                painter = painterResource(id = R.drawable.onboarding_illustartion),
                contentDescription = "Task List Illustration",
                modifier = Modifier
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(0.7f))

            // Кнопка "Let's Go"
            Button(
                onClick = { /* TODO: Обработка нажатия */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .padding(horizontal = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainBlue
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Let's Go",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(98.dp))
        }
    }
}

@Composable
fun BackgroundShapes(modifier: Modifier = Modifier) {
    val LightBlueShape = Color(0xFFC7E0FF).copy(alpha = 0.8f) // Цвет из вашего кода

    Canvas(modifier = modifier) {

        drawCircle(
            color = LightBlueShape,
            radius = 150.dp.toPx(), // Немного уменьшим радиус
            center = Offset(x = (80).dp.toPx(), y = (-20).dp.toPx()) // Сдвинем
        )

        drawCircle(
            color = LightBlueShape,
            radius = 150.dp.toPx(), // Меньший радиус
            center = Offset(x = (-20).dp.toPx(), y = (80).dp.toPx()) // Сдвинем его правее и ниже
        )

        // 3. Большой круг (основной)
        drawCircle(
            color = LightBlueShape,
            radius = 120.dp.toPx(),
            center = Offset(x = size.width + (60).dp.toPx(), y = size.height + (-20).dp.toPx())
        )

        // 4. Малый круг (дополнительный)
        drawCircle(
            color = LightBlueShape,
            radius = 120.dp.toPx(),
            center = Offset(x = size.width + (-20).dp.toPx(), y = size.height + (60).dp.toPx())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen()
}