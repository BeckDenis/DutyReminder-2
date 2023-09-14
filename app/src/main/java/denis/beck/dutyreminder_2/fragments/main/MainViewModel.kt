package denis.beck.dutyreminder_2.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.models.Remind
import denis.beck.dutyreminder_2.room.RemindDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val remindDao: RemindDao) : ViewModel() {

    private val _data = MutableLiveData<List<Remind>>()
    val data: LiveData<List<Remind>> = _data

    fun getReminds() {
        viewModelScope.launch(Dispatchers.IO) {
            val reminds = remindDao.getAll()
            _data.postValue(reminds.map { it.toRemind() })
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as DutyReminderApp
                return MainViewModel(
                    application.remindDatabase.reminderDao(),
                ) as T
            }
        }
    }
}