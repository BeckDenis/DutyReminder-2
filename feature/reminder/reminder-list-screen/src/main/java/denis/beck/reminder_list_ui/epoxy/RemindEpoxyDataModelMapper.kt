package denis.beck.reminder_list_ui.epoxy

import denis.beck.common_core.extensions.getNextMonthCalendar
import denis.beck.common_core.extensions.getPreviousMonthCalendar
import denis.beck.common_core.extensions.setFirstDayOfMonth
import denis.beck.common_core.extensions.setNextDayOfWeek
import denis.beck.reminder.domain.remindManager.RemindDomainModel
import denis.beck.reminder.data.RemindEpoxyDataModel
import java.util.Calendar
import javax.inject.Inject

class RemindEpoxyDataModelMapper @Inject constructor() {

    fun map(models: List<RemindDomainModel>, onClickListener: (remindId: Long) -> Unit): List<RemindEpoxyDataModel> {
        val result = mutableListOf<RemindEpoxyDataModel>()
        val calendar = Calendar.getInstance()
        val startTimestamp = calendar.getPreviousMonthCalendar().apply {
            setFirstDayOfMonth()
        }.timeInMillis
        val endTimestamp = calendar.getNextMonthCalendar(2).apply {
            setFirstDayOfMonth()
        }.timeInMillis

        models.forEach { remind ->
            if (remind.selectedDaysOfWeek.isEmpty()) {
                if (remind.timestamp in startTimestamp..endTimestamp) {
                    calendar.apply {
                        timeInMillis = remind.timestamp
                    }
                    val model = remind.toEpoxyDataModel(calendar).apply {
                        this.onClickListener = {
                            onClickListener.invoke(remind.id)
                        }
                    }
                    result.add(model)
                }
            } else {
                remind.selectedDaysOfWeek.forEach { dayOfWeek ->
                    calendar.apply {
                        timeInMillis = remind.timestamp
                        set(Calendar.DAY_OF_WEEK, dayOfWeek.number)
                        if (timeInMillis < remind.timestamp) {
                            calendar.setNextDayOfWeek(dayOfWeek.number)
                        }
                    }

                    if (calendar.timeInMillis < endTimestamp) {
                        // тут нужна оптимизация (если установим 1968 год к примеру, то цикл начнется с него
                        while (calendar.timeInMillis < endTimestamp) {
                            if (calendar.timeInMillis in startTimestamp..endTimestamp) {
                                val model = remind.toEpoxyDataModel(calendar).apply {
                                    this.onClickListener = {
                                        onClickListener.invoke(remind.id)
                                    }
                                }
                                result.add(model)
                            }

                            calendar.setNextDayOfWeek(dayOfWeek.number)
                        }
                    }

                }
            }
        }

        return result
    }
}