package com.example.timertodo.ui.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun TaskCardPrev() {
    var checked by remember { mutableStateOf(false) }
    TaskTile(
        modifier = Modifier.fillMaxWidth(),
        text = "Todo",
        checked = checked,
        onCheckedChange = { checked = it },
        onClose = { /* do nothing */ }
    )
}

@Composable
fun TaskTile(
    modifier: Modifier = Modifier,
    text: String,
    timeLimit: String? = null,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onClose: () -> Unit,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(0.9f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = checked, onCheckedChange = onCheckedChange)
                Text(text)
            }
            if (timeLimit != null) Text(
                modifier = Modifier.padding(start = 50.dp),
                text = timeLimit
            )
        }
        IconButton(onClick = {
            onClose()
        }) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }

}
