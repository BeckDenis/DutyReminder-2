package denis.beck.dutyreminder_2.epoxy.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import denis.beck.dutyreminder_2.R
import denis.beck.dutyreminder_2.databinding.WeekLayoutBinding

class WeekView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: WeekLayoutBinding = WeekLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private val weekModels =
        setOf(MONDAY(), TUESDAY(), WEDNESDAY(), THURSDAY(), FRIDAY(), SATURDAY(), SUNDAY())

    private var anySelectedStateListener: ((Boolean) -> Unit) = {}

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        binding.apply {
            weekModels.forEach { weekDay ->
                val view = LayoutInflater
                    .from(context)
                    .inflate(R.layout.week_item, this.root, false) as TextView
                view.bindWeekItemView(weekDay)
                root.addView(view)
            }
        }
    }

    private fun TextView.bindWeekItemView(weekDay: WeekDay) {
        tag = weekDay.abbreviation
        text = weekDay.abbreviation
        setOnClickListener {
            it.isSelected = !it.isSelected
            updateSelectedState()
        }
        weekDay.view = this
    }

    private fun updateSelectedState() {
        weekModels
            .map { it.view }
            .any { it.isSelected }
            .also(anySelectedStateListener::invoke)
    }

    fun setOnAnySelectedStateListener(listener: (Boolean) -> Unit) {
        anySelectedStateListener = listener
    }

    abstract class WeekDay(val abbreviation: String) {
        lateinit var view: TextView
    }

    class MONDAY : WeekDay("Mon")
    class TUESDAY : WeekDay("Tue")
    class WEDNESDAY : WeekDay("Wed")
    class THURSDAY : WeekDay("Thu")
    class FRIDAY : WeekDay("Fri")
    class SATURDAY : WeekDay("Sat")
    class SUNDAY : WeekDay("Sun")
}