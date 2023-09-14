package denis.beck.dutyreminder_2.epoxy

import com.airbnb.epoxy.Typed2EpoxyController
import denis.beck.dutyreminder_2.epoxy.models.RemindEpoxyModel_
import denis.beck.dutyreminder_2.models.Remind

class RemindController : Typed2EpoxyController<List<Remind>, Boolean>() {
    override fun buildModels(data: List<Remind>?, data2: Boolean?) {
        data?.forEach {
            RemindEpoxyModel_(it)
                .apply { id(it.id) }
                .addTo(this)
        }
    }
}