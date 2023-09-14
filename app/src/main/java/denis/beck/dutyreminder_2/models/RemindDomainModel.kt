package denis.beck.dutyreminder_2.models

import android.os.Parcelable
import denis.beck.dutyreminder_2.room.RemindEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class RemindDomainModel(
    val id: Long = 0,
    val timestamp: Long,
    val message: String,
) : Parcelable {
    fun toEntity() = RemindEntity(
        id = id,
        timestamp = timestamp,
        message = message
    )
}
