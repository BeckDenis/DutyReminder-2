package denis.beck.dutyreminder_2.models

import denis.beck.dutyreminder_2.room.RemindEntity

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
}
