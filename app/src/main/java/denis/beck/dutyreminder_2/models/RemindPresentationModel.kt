package denis.beck.dutyreminder_2.models

import denis.beck.common.DayOfWeek

data class RemindPresentationModel(
    val id: Long = 0,
    val time: String,
    val message: String,
    val selectedDayOfWeeks: Set<denis.beck.common.DayOfWeek>
) {
    var onClickListener: () -> Unit = {}
}