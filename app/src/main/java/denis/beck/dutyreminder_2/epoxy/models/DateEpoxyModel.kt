package denis.beck.dutyreminder_2.epoxy.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.epoxy.KotlinEpoxyHolder

@EpoxyModelClass
abstract class DateEpoxyModel(private val dateDataModel: DateEpoxyDataModel) : EpoxyModelWithHolder<DateEpoxyModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.date_item

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.title.text = dateDataModel.text
    }

    inner class Holder : KotlinEpoxyHolder() {
        val title by bind<TextView>(R.id.root)
    }


}