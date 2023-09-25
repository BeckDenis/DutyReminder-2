package denis.beck.dutyreminder_2.models

import android.content.Context
import android.os.Parcelable
import denis.beck.dutyreminder_2.epoxy.models.DayOfWeek
import denis.beck.dutyreminder_2.room.RemindEntity
import denis.beck.dutyreminder_2.utils.setNextDayOfWeek
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class RemindDomainModel(
    val id: Long = 0,
    val timestamp: Long,
    val message: String,
    val selectedDayOfWeeks: Set<DayOfWeek> = emptySet(),
) : Parcelable {
    fun toEntity() = RemindEntity(
        id = id,
        timestamp = timestamp,
        message = message,
        selectedDayOfWeeks = selectedDayOfWeeks,
    )

    fun getNearestTimestamp(): Long {
        val calendar = Calendar.getInstance()

        val nextWeekDay = selectedDayOfWeeks
            .firstOrNull { weekDay ->
                weekDay.number > calendar.get(Calendar.DAY_OF_WEEK)
            } ?: selectedDayOfWeeks.first()

        calendar.setNextDayOfWeek(nextWeekDay.number)
        return calendar.timeInMillis
    }
}
