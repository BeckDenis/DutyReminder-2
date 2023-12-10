package denis.beck.reminder_list_ui.epoxy

import android.widget.TextView
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import denis.beck.epoxy.KotlinEpoxyHolder
import denis.beck.features.reminderlistui.R

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