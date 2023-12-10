package denis.beck.reminder_list_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import denis.beck.common_core.extensions.tryLaunch
import denis.beck.common_core.liveData.SingleLiveEvent
import denis.beck.epoxy.EpoxyDataModel
import denis.beck.preferences.SharedPreferencesManager
import denis.beck.reminder_list_ui.epoxy.RemindEpoxyDataModelMapper
import denis.beck.reminder.data.RemindEpoxyDataModel
import denis.beck.reminder.domain.RemindInteractor
import denis.beck.reminder_list_ui.epoxy.ReminderListEpoxyMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderListViewModel @Inject constructor(
    private val remindInteractor: RemindInteractor,
    private val reminderListEpoxyMapper: ReminderListEpoxyMapper,
    private val sharedPreferencesManager: SharedPreferencesManager,
) : ViewModel() {

    private val _data = MutableLiveData<List<EpoxyDataModel>>()
    val data: LiveData<List<EpoxyDataModel>> = _data

    private val _goToRemind = SingleLiveEvent<Long>()
    val goToRemind: LiveData<Long> = _goToRemind

    private val _goToLogin = SingleLiveEvent<Unit>()
    val goToLogin: LiveData<Unit> = _goToLogin

    init {
        listenReminds()
    }

    fun onLogout() {
        sharedPreferencesManager.setAuthorized(false)
        _goToLogin.value = Unit
    }

    private fun listenReminds() {
        viewModelScope.tryLaunch(Dispatchers.IO) {
            remindInteractor
                .getReminds()
                .map { reminds ->
                    RemindEpoxyDataModelMapper()
                        .map(
                            models = reminds,
                            onClickListener = { _goToRemind.postValue(it) }
                        )
                }
                .map { it.sortedBy(RemindEpoxyDataModel::timestamp) }
                .map(reminderListEpoxyMapper::map)
                .collect { list ->
                    _data.postValue(list)
                }
        }
    }
}