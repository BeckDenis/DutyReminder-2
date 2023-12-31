package denis.beck.dutyreminder_2.epoxy.models

enum class DayOfWeek(val number: Int, val fullName: String) {
    MONDAY(2, "monday"),
    TUESDAY(3, "tuesday"),
    WEDNESDAY(4, "wednesday"),
    THURSDAY(5, "thursday"),
    FRIDAY(6, "friday"),
    SATURDAY(7, "saturday"),
    SUNDAY(1, "sunday");

    companion object {
        fun byCalendarDayOfWeek(number: Int) = DayOfWeek.entries.first { it.number == number }
    }
}