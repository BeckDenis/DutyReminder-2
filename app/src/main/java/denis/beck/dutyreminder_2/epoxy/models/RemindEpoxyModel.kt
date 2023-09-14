package denis.beck.dutyreminder_2.epoxy.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.epoxy.KotlinEpoxyHolder
import denis.beck.dutyreminder_2.models.Remind

@EpoxyModelClass
abstract class RemindEpoxyModel(val remind: Remind) : EpoxyModelWithHolder<Holder>() {

    override fun getDefaultLayout(): Int = R.layout.remind_item

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder) {
            title.text = remind.id.toString()
            description.text = remind.message
            time.text = remind.time
        }
    }
}

class Holder : KotlinEpoxyHolder() {
    val title by bind<TextView>(R.id.title)
    val description by bind<TextView>(R.id.description)
    val time by bind<TextView>(R.id.time)
}