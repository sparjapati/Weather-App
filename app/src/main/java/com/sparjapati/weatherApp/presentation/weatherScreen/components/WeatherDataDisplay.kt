package com.sparjapati.weatherApp.presentation.weatherScreen.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun WeatherDataDisplay(
    value: Int,
    unit: String,
    iconRes: Int,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    textStyle: TextStyle = TextStyle(),
    iconTint: Color = Color.White,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = contentDescription,
            modifier = Modifier.size(25.dp),
            tint = iconTint
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = "$value$unit",
//            textStyle = textStyle
        )
    }
}