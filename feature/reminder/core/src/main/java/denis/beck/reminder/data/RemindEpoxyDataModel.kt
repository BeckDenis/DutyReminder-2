package denis.beck.reminder.data

import denis.beck.common_core.models.ExtendedTimeStamp
import denis.beck.epoxy.EpoxyDataModel

data class RemindEpoxyDataModel(
    val id: Long,
    val message: String,
    val timestamp: Long,
    val extendedTimeStamp: ExtendedTimeStamp,
    val timeText: String,
) : EpoxyDataModel {
    var onClickListener: () -> Unit = {}
}
