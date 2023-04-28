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
                Text(text = "${dateTime!!.year}/${dateTime!!.monthValue}/${dateTime!!.dayOfMonth}")
            }
        }
        Button(onClick = { showTimePicker = true }) {
            if (dateTime == null) {
                Text(text = "Time")
            } else {
                Text(text = "${dateTime!!.hour}:${dateTime!!.minute}")
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
            dateTime = (dateTime ?: LocalDateTime.now()).withYear(it.year)
                .withMonth(it.monthValue)
                .withDayOfMonth(it.dayOfMonth)
        }
    )
    if (showTimePicker) MyTimePickerDialog(
        onDismissRequest = { showTimePicker = false },
        onConfirm = {
            dateTime = (dateTime ?: LocalDateTime.now()).withHour(it.hour).withMinute(it.minute)
        }
    )
}