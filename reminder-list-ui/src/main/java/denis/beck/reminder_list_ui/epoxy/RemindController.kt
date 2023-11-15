package denis.beck.reminder_list_ui.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import denis.beck.reminder.data.RemindEpoxyDataModel

class RemindController : Typed2EpoxyController<List<denis.beck.epoxy.EpoxyDataModel>, Boolean>() {

    override fun buildModels(data: List<denis.beck.epoxy.EpoxyDataModel>?, data2: Boolean?) {
        data?.forEach { model ->
            when (model) {
                is DateEpoxyDataModel -> {
                    DateEpoxyModel_(model)
                        .apply {
                            id(model.id)
                        }
                        .addTo(this)
                }

                is RemindEpoxyDataModel -> {
                    RemindEpoxyModel_(model)
                        .apply {
                            id(model.id + model.timestamp)
                        }
                        .addTo(this)
                }
            }

        }
    }
}