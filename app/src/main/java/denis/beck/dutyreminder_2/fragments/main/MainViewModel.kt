package denis.beck.dutyreminder_2.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.epoxy.mappers.MainEpoxyMapper
import denis.beck.dutyreminder_2.epoxy.mappers.RemindEpoxyDataModelMapper
import denis.beck.dutyreminder_2.epoxy.models.EpoxyDataModel
import denis.beck.dutyreminder_2.epoxy.models.RemindEpoxyDataModel
import denis.beck.dutyreminder_2.epoxy.repositories.RemindRepository
import denis.beck.dutyreminder_2.utils.SingleLiveEvent
import denis.beck.dutyreminder_2.utils.tryLaunch
import kotlinx.coroutines.flow.map

class MainViewModel(
    private val remindRepository: RemindRepository,
    private val mainEpoxyMapper: MainEpoxyMapper,
) : ViewModel() {

    private val _data = MutableLiveData<List<EpoxyDataModel>>()
    val data: LiveData<List<EpoxyDataModel>> = _data

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
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as DutyReminderApp
                return MainViewModel(
                    RemindRepository(application.remindDatabase.reminderDao()),
                    MainEpoxyMapper(),
                ) as T
            }
        }
    }
}