package denis.beck.dutyreminder_2.fragments.newReminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.epoxy.repositories.RemindRepository
import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.remindManager.RemindManager
import denis.beck.dutyreminder_2.room.RemindDatabase
import denis.beck.dutyreminder_2.utils.SingleLiveEvent
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

class NewReminderViewModel(
    private val remindManager: RemindManager,
    private val remindRepository: RemindRepository,
) : ViewModel() {

    private val _showDatePicker = SingleLiveEvent<Unit>()
    val showDatePicker: LiveData<Unit> = _showDatePicker

    private val _showTimePicker = SingleLiveEvent<Unit>()
    val showTimePicker: LiveData<Unit> = _showTimePicker

    private val _goBack = SingleLiveEvent<Unit>()
    val goBack: LiveData<Unit> = _goBack

    private val _pickedDateAndTimeText = MutableLiveData<String>()
    val pickedDateAndTimeText: LiveData<String> = _pickedDateAndTimeText

    private val dateAndTime: Calendar = Calendar.getInstance()

    val timestamp: Long
        get() = dateAndTime.timeInMillis

    fun init(remindId: Long?) {
        viewModelScope.launch(Dispatchers.IO) {
            remindRepository.getRemind(id = remindId)?.let { remind ->
                dateAndTime.timeInMillis = remind.timestamp
            }
            invalidateDateAndTimeText()
        }
    }

    fun onDatePickerButtonClick() {
        _showDatePicker.value = Unit
    }

    fun onTimePickerButtonClick() {
        _showTimePicker.value = Unit
    }

    fun onSaveButtonClicked(timestamp: Long, message: String) {
        val remindDomainModel = RemindDomainModel(
            timestamp = timestamp,
            message = message,
        )
        viewModelScope.launch(Dispatchers.IO) {
            remindManager.setReminder(remindDomainModel)
        }
        _goBack.value = Unit
    }

    fun setTime(hourOfDay: Int, minute: Int) {
        dateAndTime.apply {
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        invalidateDateAndTimeText()
    }

    fun setDate(year: Int, month: Int, day: Int) {
        dateAndTime.apply {
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

        _pickedDateAndTimeText.postValue("$hoursOfDay:$minute\n$dayOfMonth.$month.$year")
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as DutyReminderApp
                val remindRepository = RemindRepository(application.remindDatabase.reminderDao())
                return NewReminderViewModel(
                    RemindManager(application, remindRepository),
                    remindRepository,
                ) as T
            }
        }
    }
}