package denis.beck.dutyreminder_2.models

import android.os.Parcelable
import denis.beck.dutyreminder_2.epoxy.models.DayOfWeek
import denis.beck.dutyreminder_2.epoxy.models.ExtendedTimeStamp
import denis.beck.dutyreminder_2.epoxy.models.RemindEpoxyDataModel
import denis.beck.dutyreminder_2.room.RemindEntity
import denis.beck.dutyreminder_2.utils.setNextDayOfWeek
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import kotlinx.parcelize.Parcelize
import java.util.Calendar

@Parcelize
data class RemindDomainModel(
    val id: Long = 0,
    val timestamp: Long,
    val message: String,
    val selectedDaysOfWeek: Set<DayOfWeek> = emptySet(),
) : Parcelable {
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
