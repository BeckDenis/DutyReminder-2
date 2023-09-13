package denis.beck.dutyreminder_2.fragments.newReminder

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.models.Remind
import denis.beck.dutyreminder_2.remindManager.RemindManager
import denis.beck.dutyreminder_2.room.RemindDatabase
import denis.beck.dutyreminder_2.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class NewReminderViewModel(
    private val remindManager: RemindManager,
    private val remindDatabase: RemindDatabase,
) : ViewModel() {

    private val _showDatePicker = SingleLiveEvent<Unit>()
    val showDatePicker: LiveData<Unit> = _showDatePicker

    private val _showTimePicker = SingleLiveEvent<Unit>()
    val showTimePicker: LiveData<Unit> = _showTimePicker

    private val _goBack = SingleLiveEvent<Unit>()
    val goBack: LiveData<Unit> = _goBack

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val allReminds = remindDatabase.reminderDao().getAll()
            Timber.d("reminds: $allReminds prpr")
        }
    }

    fun onDatePickerButtonClick() {
        _showDatePicker.value = Unit
    }

    fun onTimePickerButtonClick() {
        _showTimePicker.value = Unit
    }

    fun onSaveButtonClicked(timestamp: Long, message: String) {
        val remind = Remind(
            timestamp = timestamp,
            message = message,
        )
        viewModelScope.launch(Dispatchers.IO) {
            remindDatabase.reminderDao().insert(remind.toRemindEntity()).also { id ->
                remindManager.setReminder(remind.copy(id = id))
            }
        }
        _goBack.value = Unit
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[APPLICATION_KEY]) as DutyReminderApp
                return NewReminderViewModel(
                    RemindManager(application),
                    application.remindDatabase,
                ) as T
            }
        }
    }
}