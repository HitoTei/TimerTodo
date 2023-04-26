package com.example.timertodo.ui.task

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun TaskCardPrev() {
    var checked by remember { mutableStateOf(false) }
    TaskCard(
        modifier = Modifier.fillMaxWidth(),
        text = "Todo",
        checked = checked,
        onCheckedChange = { checked = it },
        onClose = { /* do nothing */ }
    )
}

@Composable
fun TaskCard(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                Text(text)
            }
            IconButton(onClick = {
                onClose()
            }) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.Delete),
                    contentDescription = "Delete"
                )
            }
        }
    }
}
