package denis.beck.dutyreminder_2.epoxy.mappers

import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.models.RemindPresentationModel
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import java.util.Calendar
import java.util.Date

class RemindPresentationMapper {
    private val calendar = Calendar.getInstance()

    fun map(models: List<RemindDomainModel>): List<RemindPresentationModel> {
        return models.map(::map)
    }

    private fun map(model: RemindDomainModel): RemindPresentationModel {
        calendar.time = Date(model.timestamp)
        return RemindPresentationModel(
            id = model.id,
            time = formatTime(),
            message = model.message,
        )
    }

    private fun formatTime() = "${calendar.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()}:${calendar.get(Calendar.MINUTE).toDateAndTimeString()}"
}