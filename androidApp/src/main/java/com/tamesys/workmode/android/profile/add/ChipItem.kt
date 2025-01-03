package com.tamesys.workmode.android.profile.add

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChipItem(text: String,
             trailingIcon: @Composable (() -> Unit)? = null,
             onClick: () -> Unit = {}) {
    FilterChip(
        modifier = Modifier.padding(end = 4.dp),
        onClick = onClick,
        trailingIcon = trailingIcon,
        border = BorderStroke(1.dp, Color(0xFF3B3A3C)),
        label = {
            Text(text)
        },
        selected = false
    )
}