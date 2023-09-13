package denis.beck.dutyreminder_2.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val timestamp: Long,
    @ColumnInfo val message: String,
)