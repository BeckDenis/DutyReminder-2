package denis.beck.reminder.data.room

import denis.beck.reminder.domain.remindManager.RemindDomainModel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import denis.beck.common.models.DayOfWeek

@Entity(tableName = "remind")
data class RemindEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val timestamp: Long,
    @ColumnInfo val message: String,
    @ColumnInfo val selectedDayOfWeeks: Set<DayOfWeek>
)