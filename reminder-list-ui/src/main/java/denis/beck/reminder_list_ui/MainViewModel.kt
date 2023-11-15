package denis.beck.reminder_list_ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import denis.beck.common.SingleLiveEvent
import denis.beck.common.tryLaunch
import denis.beck.reminder.RemindDatabaseSingleton
import denis.beck.reminder.RemindEpoxyDataModelMapper
import denis.beck.reminder.RemindRepository
import denis.beck.reminder.data.RemindEpoxyDataModel
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val remindRepository: RemindRepository,
    private val mainEpoxyMapper: MainEpoxyMapper,
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
                    .map(mainEpoxyMapper::map)
                    .collect { list ->
                        _data.value = list
                    }
            }
        )
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return MainViewModel(
                    RemindRepository(RemindDatabaseSingleton.instance.reminderDao()),
                    MainEpoxyMapper(),
                ) as T
            }
        }
    }
}