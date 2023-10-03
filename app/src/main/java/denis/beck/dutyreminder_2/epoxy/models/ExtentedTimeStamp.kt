package denis.beck.dutyreminder_2.epoxy.models

import java.util.Calendar

data class ExtendedTimeStamp(
    val year: Int = -1,
    val month: Int = -1,
    val day: Int = -1,
    val hour: Int = -1,
) {
    companion object {
        fun fromCalendar(calendar: Calendar) = ExtendedTimeStamp(
            year = calendar.get(Calendar.YEAR),
            month = calendar.get(Calendar.MONTH),
            day = calendar.get(Calendar.DAY_OF_MONTH),
            hour = calendar.get(Calendar.HOUR_OF_DAY),
        )
    }

    fun equalsType(otherModel: ExtendedTimeStamp): EqualsType = when {
        !this.equalsUpToYear(otherModel) -> EqualsType.UP_TO_NOTHING
        !this.equalsUpToMonth(otherModel) -> EqualsType.UP_TO_YEAR
        !this.equalsUpToDay(otherModel) -> EqualsType.UP_TO_MONTH
        !this.equalsUpToHour(otherModel) -> EqualsType.UP_TO_DAY
        else -> EqualsType.UP_TO_HOUR
    }

    private fun equalsUpToYear(otherModel: ExtendedTimeStamp) = year == otherModel.year
    private fun equalsUpToMonth(otherModel: ExtendedTimeStamp) = month == otherModel.month
    private fun equalsUpToDay(otherModel: ExtendedTimeStamp) = day == otherModel.day
    private fun equalsUpToHour(otherModel: ExtendedTimeStamp) = hour == otherModel.hour

    enum class EqualsType{
        UP_TO_YEAR,
        UP_TO_MONTH,
        UP_TO_DAY,
        UP_TO_HOUR,
        UP_TO_NOTHING
    }
}
