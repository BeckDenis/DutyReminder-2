package denis.beck.reminder.domain.remindManager

import android.os.Parcelable
import denis.beck.common_core.extensions.setNextDayOfWeek
import denis.beck.common_core.extensions.toDateAndTimeString
import denis.beck.common_core.models.DayOfWeek
import denis.beck.common_core.models.ExtendedTimeStamp
import denis.beck.reminder.data.RemindEpoxyDataModel
import denis.beck.reminder.data.room.RemindEntity
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class RemindDomainModel(
    val id: Long = 0,
    val timestamp: Long,
    val message: String,
    val selectedDaysOfWeek: Set<DayOfWeek> = emptySet(),
) : Parcelable {

    companion object {
        fun fromEntity(entity: RemindEntity) = RemindDomainModel(
            id = entity.id,
            timestamp = entity.timestamp,
            message = entity.message,
            selectedDaysOfWeek = entity.selectedDayOfWeeks,
        )
    }

    fun toEntity() = RemindEntity(
        id = id,
        timestamp = timestamp,
        message = message,
        selectedDayOfWeeks = selectedDaysOfWeek,
    )

    fun toEpoxyDataModel(calendar: Calendar) = RemindEpoxyDataModel(
        id = id,
        timestamp = calendar.timeInMillis,
        message = message,
        extendedTimeStamp = ExtendedTimeStamp.fromCalendar(calendar),
        timeText = calendar.formatTime()
    )

    fun getNearestTimestamp(): Long {
        val calendar = Calendar.getInstance()

        val nextWeekDay = selectedDaysOfWeek
            .firstOrNull { weekDay ->
                weekDay.number > calendar.get(Calendar.DAY_OF_WEEK)
            } ?: selectedDaysOfWeek.first()

        calendar.setNextDayOfWeek(nextWeekDay.number)
        return calendar.timeInMillis
    }

    private fun Calendar.formatTime() =
        "${this.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()}:${this.get(Calendar.MINUTE).toDateAndTimeString()}"
}
