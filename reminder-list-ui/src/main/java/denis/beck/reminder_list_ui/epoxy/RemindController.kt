package denis.beck.reminder_list_ui.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import denis.beck.common.di.FragmentScope
import denis.beck.epoxy.EpoxyDataModel
import denis.beck.reminder.data.RemindEpoxyDataModel
import javax.inject.Inject

@FragmentScope
internal class RemindController @Inject constructor() : Typed2EpoxyController<List<EpoxyDataModel>, Boolean>() {

    override fun buildModels(data: List<EpoxyDataModel>?, data2: Boolean?) {
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