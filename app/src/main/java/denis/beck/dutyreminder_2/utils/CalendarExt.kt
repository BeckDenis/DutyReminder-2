package denis.beck.dutyreminder_2.utils

import java.util.Calendar

fun Calendar.toDateAndTimeString(): String {
    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH).toDateAndTimeString()
    val dayOfMonth = this.get(Calendar.DAY_OF_MONTH).toDateAndTimeString()
    val hoursOfDay = this.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()
    val minute = this.get(Calendar.MINUTE).toDateAndTimeString()

    return "$hoursOfDay:$minute\n$dayOfMonth.$month.$year"
}

fun Calendar.toDateString(): String {
    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH).toDateAndTimeString()
    val dayOfMonth = this.get(Calendar.DAY_OF_MONTH).toDateAndTimeString()

    return "$dayOfMonth.$month.$year"
}

fun Calendar.toTimeString(): String {
    val hoursOfDay = this.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()
    val minute = this.get(Calendar.MINUTE).toDateAndTimeString()

    return "$hoursOfDay:$minute"
}

fun Calendar.toDateAndTimeLogString(): String {
    val year = this.get(Calendar.YEAR)
    val month = this.get(Calendar.MONTH).toDateAndTimeString()
    val dayOfMonth = this.get(Calendar.DAY_OF_MONTH).toDateAndTimeString()
    val hoursOfDay = this.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()
    val minute = this.get(Calendar.MINUTE).toDateAndTimeString()

    return "$hoursOfDay:$minute $dayOfMonth.$month.$year"
}

fun Calendar.setNextDayOfWeek(dayOfWeek: Int) {
    val initialDayOfWeek = get(Calendar.DAY_OF_WEEK)
    set(Calendar.DAY_OF_WEEK, dayOfWeek)
    if (initialDayOfWeek == dayOfWeek || timeInMillis < System.currentTimeMillis()) {
        val nextWeekOfYear = get(Calendar.WEEK_OF_YEAR) + 1
        if (nextWeekOfYear > weeksInWeekYear) {
            set(Calendar.YEAR, get(Calendar.YEAR) + 1)
            set(Calendar.WEEK_OF_YEAR, 1)
        } else {
            set(Calendar.WEEK_OF_YEAR, get(Calendar.WEEK_OF_YEAR) + 1)
        }
    }
}