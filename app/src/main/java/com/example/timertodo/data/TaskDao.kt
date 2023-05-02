package com.example.timertodo.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import androidx.room.Update
import com.example.timertodo.utils.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDateTime

@Dao
interface TaskDao {
    @Query("select * from task order by timeLimit")
    fun getAll(): Flow<List<Task>>

    @Query("select * from task where id = :id")
    suspend fun getTaskFromId(id: Int): Task?

    @Insert
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Update
    suspend fun update(task: Task)
}

class TaskConverter {
    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime?): String {
        return localDateTime.toString()
    }

    @TypeConverter
    fun toLocalDateTime(localDateTime: String): LocalDateTime? {
        if (localDateTime == "null") return null
        return LocalDateTime.parse(localDateTime)
    }

    @TypeConverter
    fun toMutableState(boolean: Boolean): MutableState<Boolean> {
        return mutableStateOf(boolean)
    }

    @TypeConverter
    fun fromMutableState(mutableState: MutableState<Boolean>): Boolean {
        return mutableState.value
    }

}

@Database(entities = [Task::class], version = 1, exportSchema = false)
@TypeConverters(TaskConverter::class)
abstract class AppDatabase : androidx.room.RoomDatabase() {
    abstract fun taskDao(): TaskDao
}