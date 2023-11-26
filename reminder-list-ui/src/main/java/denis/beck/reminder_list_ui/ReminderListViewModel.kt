package denis.beck.reminder_list_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import denis.beck.common.liveData.SingleLiveEvent
import denis.beck.common.extensions.tryLaunch
import denis.beck.reminder.RemindEpoxyDataModelMapper
import denis.beck.reminder.RemindRepository
import denis.beck.reminder.data.RemindEpoxyDataModel
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderListViewModel @Inject constructor(
    private val remindRepository: RemindRepository,
    private val reminderListEpoxyMapper: ReminderListEpoxyMapper,
) : ViewModel() {

    private val _data = MutableLiveData<List<denis.beck.epoxy.EpoxyDataModel>>()
    val data: LiveData<List<denis.beck.epoxy.EpoxyDataModel>> = _data

    private val _goToRemind = SingleLiveEvent<Long>()
    val goToRemind: LiveData<Long> = _goToRemind

    init {
        listenReminds()
    }

    private fun listenReminds() {
        viewModelScope.tryLaunch(
            doOnLaunch = {
                remindRepository
                    .getRemindsByFlow()
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
                        _data.value = list
                    }
            }
        )
    }
}