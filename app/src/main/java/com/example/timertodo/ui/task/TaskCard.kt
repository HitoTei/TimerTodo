package com.example.timertodo.ui.task

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TaskCardPrev() {
    var checked by remember { mutableStateOf(false)}
    TaskCard(text = "Todo", checked) {
        checked = it
    }
}

@Composable
fun TaskCard(text: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = checked, onCheckedChange = onCheckedChange)
            Text(text)
        }
    }
}