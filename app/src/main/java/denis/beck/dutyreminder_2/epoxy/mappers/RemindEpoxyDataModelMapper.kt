package denis.beck.dutyreminder_2.epoxy.mappers

import denis.beck.dutyreminder_2.epoxy.models.RemindEpoxyDataModel
import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.utils.getNextMonthCalendar
import denis.beck.dutyreminder_2.utils.getPreviousMonthCalendar
import denis.beck.dutyreminder_2.utils.setFirstDayOfMonth
import denis.beck.dutyreminder_2.utils.setNextDayOfWeek
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import java.util.Calendar

class RemindEpoxyDataModelMapper {

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