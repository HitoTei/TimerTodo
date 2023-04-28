import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.timertodo.ui.picker.MyDatePickerDialog
import com.example.timertodo.ui.picker.MyTimePickerDialog
import com.example.timertodo.utils.toFormattedLocalDateString
import com.example.timertodo.utils.toFormattedLocalTimeString
import com.example.timertodo.utils.withLocalDate
import com.example.timertodo.utils.withLocalTime
import java.time.LocalDateTime

@Composable
fun AddTaskSheet(onConfirmed: (String, LocalDateTime?) -> Unit, onCanceled: () -> Unit){
    var text by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }
    var dateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    TextField(value = text, onValueChange = { text = it })
    Row {

        Button(onClick = { showDatePicker = true }) {
            if (dateTime == null) {
                Text(text = "Date")
            } else {
                val formattedText = dateTime!!.toFormattedLocalDateString()
                Text(text = formattedText)
            }
        }
        Button(onClick = { showTimePicker = true }) {
            if (dateTime == null) {
                Text(text = "Time")
            } else {
                val formattedText = dateTime!!.toFormattedLocalTimeString()
                Text(text = formattedText)
            }
        }
    }
    Row {
        Button(onClick = {
            onCanceled()
            text = ""
            dateTime = null
        }) {
            Text(text = "Cancel")
        }
        Button(onClick = {
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