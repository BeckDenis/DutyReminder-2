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
import denis.beck.dutyreminder_2.utils.SingleLiveEvent
import denis.beck.dutyreminder_2.utils.toDateAndTimeString
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Calendar

enum class RemindViewState {
    NEW,
    CHANGE,
}

class ReminderViewModel(
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

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val dateAndTime: Calendar = Calendar.getInstance().apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }

    private var initialRemind: RemindDomainModel? = null

    val timestamp: Long
        get() = dateAndTime.timeInMillis

    var state: RemindViewState = RemindViewState.NEW

    fun init(remindId: Long?) {
        remindId?.let { state = RemindViewState.CHANGE }
        viewModelScope.launch(Dispatchers.IO) {
            remindRepository.getRemind(id = remindId)?.let { remind ->
                this@ReminderViewModel.initialRemind = remind
                dateAndTime.timeInMillis = remind.timestamp
                _message.postValue(remind.message)
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

    fun onSaveButtonClick(timestamp: Long, message: String) {
        val newRemind = RemindDomainModel(
            timestamp = timestamp,
            message = message,
        )
        viewModelScope.launch(Dispatchers.IO) {
            initialRemind?.let { oldRemind ->
                remindManager.updateReminder(
                    newRemind = newRemind.copy(id = oldRemind.id),
                    oldRemind = oldRemind,
                )
            } ?: remindManager.setReminder(newRemind)
        }
        _goBack.value = Unit
    }

    fun onDeleteButtonClick() {
        viewModelScope.launch(Dispatchers.IO) {
            initialRemind?.let { remind ->
                remindManager.deleteReminder(remind)
            }
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
        _pickedDateAndTimeText.postValue(dateAndTime.toDateAndTimeString())
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
                return ReminderViewModel(
                    RemindManager(application, remindRepository),
                    remindRepository,
                ) as T
            }
        }
    }
}