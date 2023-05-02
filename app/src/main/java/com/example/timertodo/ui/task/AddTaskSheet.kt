import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.timertodo.ui.picker.MyDatePickerDialog
import com.example.timertodo.ui.picker.MyTimePickerDialog
import com.example.timertodo.utils.toFormattedLocalDateString
import com.example.timertodo.utils.toFormattedLocalTimeString
import com.example.timertodo.utils.withLocalDate
import com.example.timertodo.utils.withLocalTime
import java.time.LocalDateTime

@Composable
fun AddTaskSheet(onConfirmed: (String, LocalDateTime?) -> Unit, onCanceled: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var dateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 45.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            colors = TextFieldDefaults.colors()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {

            TextButton(
                onClick = { showDatePicker = true },
                shape = ShapeDefaults.ExtraSmall
            ) {
                if (dateTime == null) {
                    Text(
                        text = "Date",
                        style = MaterialTheme.typography.displaySmall
                    )
                } else {
                    val formattedText = dateTime!!.toFormattedLocalDateString()
                    Text(
                        text = formattedText,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
            TextButton(
                onClick = { showTimePicker = true },
                shape = ShapeDefaults.ExtraSmall
            ) {
                if (dateTime == null) {
                    Text(
                        text = "Time",
                        style = MaterialTheme.typography.displaySmall
                    )
                } else {
                    val formattedText = dateTime!!.toFormattedLocalTimeString()
                    Text(
                        text = formattedText,
                        style = MaterialTheme.typography.displaySmall
                    )
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(0.8f),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                modifier = Modifier
                    .size(height = 35.dp, width = 100.dp)
                    .padding(end = 8.dp),
                shape = ShapeDefaults.Small,
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onCanceled()
                    text = ""
                    dateTime = null
                }) {
                Text(text = "CANCEL")
            }
            Button(
                modifier = Modifier.size(height = 35.dp, width = 100.dp),
                shape = ShapeDefaults.Small,
                contentPadding = PaddingValues(0.dp),
                onClick = {
                    onConfirmed(text, dateTime)
                    text = ""
                    dateTime = null
                }) {
                Text(text = "OK")
            }
        }
        if (showDatePicker) MyDatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            onConfirm = {
                dateTime = (dateTime ?: LocalDateTime.now()).withLocalDate(it)
                it.atStartOfDay()
            }
        )
        if (showTimePicker) MyTimePickerDialog(
            onDismissRequest = { showTimePicker = false },
            onConfirm = {
                dateTime = (dateTime ?: LocalDateTime.now()).withLocalTime(it)
            }
        )
    }
}