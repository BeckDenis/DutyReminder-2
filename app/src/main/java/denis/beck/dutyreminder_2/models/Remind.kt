package denis.beck.dutyreminder_2.models

import denis.beck.dutyreminder_2.room.RemindEntity
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import java.util.Calendar
import java.util.Date

data class Remind(
    val id: Long = 0,
    val timestamp: Long,
    val message: String,
) {
    fun toRemindEntity() = RemindEntity(
        id = id,
        timestamp = timestamp,
        message = message
    )

    val time: String
        get() {
            val calendar = Calendar.getInstance().apply {
                time = Date(timestamp)
            }
            return "${calendar.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()}:${calendar.get(Calendar.MINUTE).toDateAndTimeString()}"
        }
}
