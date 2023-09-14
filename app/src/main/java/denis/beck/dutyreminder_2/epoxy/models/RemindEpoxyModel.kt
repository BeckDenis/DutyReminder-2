package denis.beck.dutyreminder_2.epoxy.models

import android.view.View
import android.widget.TextView
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.epoxy.KotlinEpoxyHolder
import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.models.RemindPresentationModel

@EpoxyModelClass
abstract class RemindEpoxyModel(private val remindPresentationModel: RemindPresentationModel) : EpoxyModelWithHolder<RemindEpoxyModel.Holder>() {

    override fun getDefaultLayout(): Int = R.layout.remind_item

    override fun bind(holder: Holder) {
        super.bind(holder)
        with(holder) {
            title.text = remindPresentationModel.id.toString()
            description.text = remindPresentationModel.message
            time.text = remindPresentationModel.time
        }
    }

    inner class Holder : KotlinEpoxyHolder() {
        val title by bind<TextView>(R.id.title)
        val description by bind<TextView>(R.id.description)
        val time by bind<TextView>(R.id.time)

        override fun bindView(itemView: View) {
            super.bindView(itemView)
            itemView.setOnClickListener {
                remindPresentationModel.onClickListener.invoke()
            }
        }
    }
}

