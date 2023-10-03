package denis.beck.dutyreminder_2.epoxy.mappers

import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.models.RemindPresentationModel
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import java.util.Calendar
import java.util.Date

// class RemindPresentationMapper {
//     private val calendar = Calendar.getInstance()
//
//     fun map(models: List<RemindDomainModel>, onClickListener: (remindId: Long) -> Unit): List<RemindPresentationModel> {
//         return models.map { model ->
//             model.map(onClickListener)
//         }
//     }
//
//     private fun RemindDomainModel.map(onClickListener: (remindId: Long) -> Unit): RemindPresentationModel {
//         calendar.time = Date(this.timestamp)
//         return RemindPresentationModel(
//             id = this.id,
//             time = formatTime(),
//             message = this.message,
//             selectedDayOfWeeks = selectedDaysOfWeek,
//         ).apply {
//             this.onClickListener = {
//                 onClickListener.invoke(this.id)
//             }
//         }
//     }
//
//
// }