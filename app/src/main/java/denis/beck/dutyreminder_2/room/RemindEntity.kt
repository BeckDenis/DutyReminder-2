package denis.beck.dutyreminder_2.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import denis.beck.dutyreminder_2.epoxy.models.DayOfWeek
import denis.beck.dutyreminder_2.models.RemindDomainModel

@Entity(tableName = "remind")
data class RemindEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo val timestamp: Long,
    @ColumnInfo val message: String,
    @ColumnInfo val selectedDayOfWeeks: Set<DayOfWeek>
) {
    fun toDomain() = RemindDomainModel(
        id = id,
        timestamp = timestamp,
        message = message,
        selectedDayOfWeeks = selectedDayOfWeeks,
    )
}