package com.example.androidpracticumcustomview.ui.theme

import androidx.compose.foundation.background
import com.example.androidpracticumcustomview.R
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp

/*
Задание:
Реализуйте необходимые компоненты.
*/

@Composable
fun MainScreen(closeActivity: () -> Unit) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .clickable { closeActivity.invoke() },
            contentAlignment = Alignment.Center
        ) {

            CustomContainerCompose(
                firstChild = {
                    Text(
                        modifier = Modifier
                            .background(color = Color.Blue),
                        text = stringResource(R.string.first_text_compose),
                        fontSize = 34.sp
                    )
                },
                secondChild = {
                    Text(
                        modifier = Modifier
                            .background(color = Color.Yellow),
                        text = stringResource(R.string.second_text_compose),
                        fontSize = 34.sp
                    )
                }
            )
        }
    }
}