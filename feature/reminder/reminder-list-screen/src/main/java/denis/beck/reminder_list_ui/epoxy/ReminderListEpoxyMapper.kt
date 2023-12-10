package denis.beck.reminder_list_ui.epoxy

import denis.beck.common_core.models.DayOfWeek
import denis.beck.common_core.models.ExtendedTimeStamp
import denis.beck.epoxy.EpoxyDataModel
import denis.beck.reminder.data.RemindEpoxyDataModel
import java.util.Calendar
import javax.inject.Inject

class ReminderListEpoxyMapper @Inject constructor() {
    fun map(models: List<RemindEpoxyDataModel>): List<EpoxyDataModel> {
        val calendar = Calendar.getInstance()
        val resultList = mutableListOf<EpoxyDataModel>()

        var id = 10000
        var previousModel = ExtendedTimeStamp()
        models
            .forEach { remind ->
                when (remind.extendedTimeStamp.equalsType(previousModel)) {
                    ExtendedTimeStamp.EqualsType.UP_TO_YEAR -> {
                        calendar.apply { timeInMillis = remind.timestamp }
                        val month = calendar.monthName
                        val day = calendar.get(Calendar.DAY_OF_MONTH)
                        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                        val text = "$month\n$day, ${DayOfWeek.byCalendarDayOfWeek(dayOfWeek).fullName}"
                        resultList.add(DateEpoxyDataModel(text, id++))
                    }
                    ExtendedTimeStamp.EqualsType.UP_TO_MONTH -> {
                        calendar.apply { timeInMillis = remind.timestamp }
                        val day = calendar.get(Calendar.DAY_OF_MONTH)
                        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                        val text = "$day, ${DayOfWeek.byCalendarDayOfWeek(dayOfWeek).fullName}"
                        resultList.add(DateEpoxyDataModel(text, id++))
                    }

                    ExtendedTimeStamp.EqualsType.UP_TO_NOTHING -> {
                        calendar.apply { timeInMillis = remind.timestamp }
                        val year = calendar.get(Calendar.YEAR)
                        val month = calendar.monthName
                        val day = calendar.get(Calendar.DAY_OF_MONTH)
                        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                        val text = "$year, $month\n$day, ${DayOfWeek.byCalendarDayOfWeek(dayOfWeek).fullName}"
                        resultList.add(DateEpoxyDataModel(text, id++))
                    }

                    else -> {}
                }
                previousModel = remind.extendedTimeStamp
                resultList.add(remind)
            }
        return resultList
    }

    private val Calendar.monthName: String
        get() = when (this.get(Calendar.MONTH)) {
            0 -> "January"
            1 -> "February"
            2 -> "March"
            3 -> "April"
            4 -> "May"
            5 -> "June"
            6 -> "July"
            7 -> "August"
            8 -> "September"
            9 -> "October"
            10 -> "November"
            11 -> "December"
            else -> "Undefemder"
        }
}