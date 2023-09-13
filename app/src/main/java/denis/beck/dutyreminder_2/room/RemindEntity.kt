package denis.beck.dutyreminder_2.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import denis.beck.dutyreminder_2.models.Remind

@Entity(tableName = "remind")
data class RemindEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo val timestamp: Long,
    @ColumnInfo val message: String,
) {
    fun toRemind() = Remind(
        id = id,
        timestamp = timestamp,
        message = message
    )
}