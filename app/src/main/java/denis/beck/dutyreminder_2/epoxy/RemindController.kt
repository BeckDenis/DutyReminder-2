package denis.beck.dutyreminder_2.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import denis.beck.dutyreminder_2.epoxy.models.RemindEpoxyModel_
import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.models.RemindPresentationModel

class RemindController : Typed2EpoxyController<List<RemindPresentationModel>, Boolean>() {
    override fun buildModels(data: List<RemindPresentationModel>?, data2: Boolean?) {
        data?.forEach {
            RemindEpoxyModel_(it)
                .apply { id(it.id) }
                .addTo(this)
        }
    }
}