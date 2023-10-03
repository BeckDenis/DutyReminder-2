package denis.beck.dutyreminder_2.utils

import java.lang.Math.abs
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

fun Calendar.getPreviousMonthCalendar(monthCount: Int = 1): Calendar {
    val yearsCount = monthCount / 12
    val newCalendar = Calendar.getInstance()
    val currentMonth = this.get(Calendar.MONTH)
    var newMonth = currentMonth - (monthCount % 12)
    if (newMonth < 0) {
        newMonth = 12 - kotlin.math.abs(newMonth)
        val newYear = this.get(Calendar.YEAR) - 1
        newCalendar.set(Calendar.YEAR, newYear)
    }
    newCalendar.set(Calendar.YEAR, newCalendar.get(Calendar.YEAR) - yearsCount)
    return newCalendar.apply { set(Calendar.MONTH, newMonth) }
}

fun Calendar.getNextMonthCalendar(monthCount: Int = 1): Calendar {
    val yearsCount = monthCount / 12
    val newCalendar = Calendar.getInstance()
    val currentMonth = this.get(Calendar.MONTH)
    var newMonth = currentMonth + (monthCount % 12)
    if (newMonth > 11) {
        newMonth -= 12
        val newYear = this.get(Calendar.YEAR) + 1
        newCalendar.set(Calendar.YEAR, newYear)
    }
    newCalendar.set(Calendar.YEAR, newCalendar.get(Calendar.YEAR) + yearsCount)
    return newCalendar.apply { set(Calendar.MONTH, newMonth) }
}

fun Calendar.setFirstDayOfMonth() {
    set(Calendar.DAY_OF_MONTH, 1)
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
}