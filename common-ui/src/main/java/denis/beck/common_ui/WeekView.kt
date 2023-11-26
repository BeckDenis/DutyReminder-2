package denis.beck.common_ui

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import denis.beck.common.models.DayOfWeek
import denis.beck.common_ui.databinding.WeekLayoutBinding

class WeekView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val binding: WeekLayoutBinding = WeekLayoutBinding.inflate(LayoutInflater.from(context), this, true)
    private val weekModels = setOf(
        WeekDayView.MONDAY(),
        WeekDayView.TUESDAY(),
        WeekDayView.WEDNESDAY(),
        WeekDayView.THURSDAY(),
        WeekDayView.FRIDAY(),
        WeekDayView.SATURDAY(),
        WeekDayView.SUNDAY()
    )

    private var weekDaySelectedListener: ((Set<DayOfWeek>) -> Unit) = {}

    var selectedDayOfWeeks: Set<DayOfWeek>
        set(value) {
            weekModels.forEach { model ->
                if (value.contains(model.dayOfWeek)) {
                    model.view.isSelected = true
                }
            }
        }
        get() = weekModels
            .filter { it.view.isSelected }
            .map { it.dayOfWeek }
            .toSet()

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

    fun setOnWeekDaySelectedListener(listener: (Set<DayOfWeek>) -> Unit) {
        weekDaySelectedListener = listener
    }

    private fun TextView.bindWeekItemView(weekDay: WeekDayView) {
        tag = weekDay.abbreviation
        text = weekDay.abbreviation
        setOnClickListener {
            isSelected = !isSelected
            updateSelectedState()
        }
        weekDay.view = this
    }

    private fun updateSelectedState() {
        weekDaySelectedListener.invoke(selectedDayOfWeeks)
    }

    private sealed class WeekDayView(val abbreviation: String, val dayOfWeek: DayOfWeek) {
        lateinit var view: TextView

        class MONDAY : WeekDayView("Mon", DayOfWeek.MONDAY)
        class TUESDAY : WeekDayView("Tue", DayOfWeek.TUESDAY)
        class WEDNESDAY : WeekDayView("Wed", DayOfWeek.WEDNESDAY)
        class THURSDAY : WeekDayView("Thu", DayOfWeek.THURSDAY)
        class FRIDAY : WeekDayView("Fri", DayOfWeek.FRIDAY)
        class SATURDAY : WeekDayView("Sat", DayOfWeek.SATURDAY)
        class SUNDAY : WeekDayView("Sun", DayOfWeek.SUNDAY)
    }
}