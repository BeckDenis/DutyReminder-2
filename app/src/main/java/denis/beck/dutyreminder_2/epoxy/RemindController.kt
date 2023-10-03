package denis.beck.dutyreminder_2.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import denis.beck.dutyreminder_2.epoxy.models.DateEpoxyDataModel
import denis.beck.dutyreminder_2.epoxy.models.DateEpoxyModel_
import denis.beck.dutyreminder_2.epoxy.models.EpoxyDataModel
import denis.beck.dutyreminder_2.epoxy.models.RemindEpoxyDataModel
import denis.beck.dutyreminder_2.epoxy.models.RemindEpoxyModel_
import denis.beck.dutyreminder_2.models.RemindPresentationModel

class RemindController : Typed2EpoxyController<List<EpoxyDataModel>, Boolean>() {

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