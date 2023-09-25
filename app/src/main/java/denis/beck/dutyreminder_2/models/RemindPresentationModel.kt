package denis.beck.dutyreminder_2.models

import denis.beck.dutyreminder_2.epoxy.models.DayOfWeek

data class RemindPresentationModel(
    val id: Long = 0,
    val time: String,
    val message: String,
    val selectedDayOfWeeks: Set<DayOfWeek>
) {
    var onClickListener: () -> Unit = {}
}