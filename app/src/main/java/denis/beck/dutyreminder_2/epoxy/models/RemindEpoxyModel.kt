package denis.beck.dutyreminder_2.epoxy.models

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.epoxy.KotlinEpoxyHolder

@EpoxyModelClass
abstract class RemindEpoxyModel(private val remindModel: RemindEpoxyDataModel) : EpoxyModelWithHolder<RemindEpoxyModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.remind_item

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder) {
            title.text = remindModel.id.toString()
            description.text = remindModel.message
            time.text = remindModel.timeText
        }
    }

    inner class Holder : KotlinEpoxyHolder() {
        val title by bind<TextView>(R.id.title)
        val description by bind<TextView>(R.id.description)
        val time by bind<TextView>(R.id.time)

        override fun bindView(itemView: View) {
            super.bindView(itemView)
            itemView.setOnClickListener {
                remindModel.onClickListener.invoke()
            }
        }
    }
}

