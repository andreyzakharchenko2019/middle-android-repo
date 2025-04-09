package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


/*
Задание:
Реализуйте необходимые компоненты;
Создайте проверку что дочерних элементов не более 2-х;
Предусмотрите обработку ошибок рендера дочерних элементов.
Задание по желанию:
Предусмотрите параметризацию длительности анимации.
 */
@Composable
fun CustomContainerCompose(
    firstChild: @Composable (() -> Unit)?,
    secondChild: @Composable (() -> Unit)?,
    animationDuration: Int = 5000
) {
    // Блок создания и инициализации переменных

    var firstChildVisible by remember { mutableStateOf(false) }
    var secondChildVisible by remember { mutableStateOf(false) }

    val childAlpha by animateFloatAsState(
        targetValue = if (firstChildVisible) 1f else 0f,
        animationSpec = tween(durationMillis = animationDuration)
    )

    val screenHeight = with(LocalDensity.current) {
        LocalContext.current.resources
            .displayMetrics.heightPixels / density
    }

    val startPosition = remember { 0f }
    val targetPositionForFirstChild =
        remember {  screenHeight * -0.4f }
    val targetPositionForSecondChild =
        remember { screenHeight * 0.4f }


    val positionForFirstChild by animateFloatAsState(
        targetValue =
            if (firstChildVisible) targetPositionForFirstChild else
                startPosition,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = LinearEasing
        )
    )

    val positionForSecondChild by animateFloatAsState(
        targetValue =
            if (secondChildVisible) targetPositionForSecondChild else
                startPosition,
        animationSpec = tween(
            durationMillis = animationDuration,
            easing = LinearEasing
        )
    )


    // Блок активации анимации при первом запуске
    LaunchedEffect(Unit) {
        firstChildVisible = true
        secondChildVisible = true
    }

    // Основной контейнер
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Magenta),
        contentAlignment = Alignment.Center
    ) {
        firstChild?.let {
            Box(
                modifier = Modifier
                    .offset(y = positionForFirstChild.dp)
                    .alpha(childAlpha)

            ) {
                it()
            }
        }
        secondChild?.let {
            Box(
                modifier = Modifier
                    .offset(y = positionForSecondChild.dp)
                    .alpha(childAlpha)

            ) {
                it()
            }
        }
    }
}