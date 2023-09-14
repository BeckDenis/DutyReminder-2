package denis.beck.dutyreminder_2.fragments.pickers.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import java.util.Calendar

class PickersViewModel : ViewModel() {

    private val _pickedDateAndTimeText = MutableLiveData<String>()
    val pickedDateAndTimeText: LiveData<String> = _pickedDateAndTimeText

    private var dateAndTime: Calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 18)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
    }

    val timestamp: Long
        get() = dateAndTime.timeInMillis

    init {
        invalidateDateAndTimeText()
    }

    fun setTime(hourOfDay: Int, minute: Int) {
        dateAndTime = dateAndTime.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        invalidateDateAndTimeText()
    }

    fun setDate(year: Int, month: Int, day: Int) {
        dateAndTime = dateAndTime.apply {
            set(Calendar.YEAR, year);
            set(Calendar.MONTH, month);
            set(Calendar.DAY_OF_MONTH, day)
        }
        invalidateDateAndTimeText()
    }

    private fun invalidateDateAndTimeText() {
        val year = dateAndTime.get(Calendar.YEAR)
        val month = dateAndTime.get(Calendar.MONTH).toDateAndTimeString()
        val dayOfMonth = dateAndTime.get(Calendar.DAY_OF_MONTH).toDateAndTimeString()
        val hoursOfDay = dateAndTime.get(Calendar.HOUR_OF_DAY).toDateAndTimeString()
        val minute = dateAndTime.get(Calendar.MINUTE).toDateAndTimeString()

        _pickedDateAndTimeText.value = "$hoursOfDay:$minute\n$dayOfMonth.$month.$year"
    }
}