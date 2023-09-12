package denis.beck.dutyreminder_2.fragments.pickers.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.Calendar

class PickersCommonViewModel : ViewModel() {
    private var dateAndTime: Calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, 18)
        set(Calendar.MINUTE, 0)
    }

    private val _pickedDateAndTimeText = MutableLiveData<String>()
    val pickedDateAndTimeText: LiveData<String> = _pickedDateAndTimeText

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
        val month = dateAndTime.get(Calendar.MONTH)
        val dayOfMonth = dateAndTime.get(Calendar.DAY_OF_MONTH)
        val hoursOfDay = dateAndTime.get(Calendar.HOUR_OF_DAY)
        val minute = dateAndTime.get(Calendar.MINUTE)

        _pickedDateAndTimeText.value = "$hoursOfDay:$minute\n$dayOfMonth.$month.$year"
    }
}