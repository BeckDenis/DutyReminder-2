package denis.beck.dutyreminder_2.fragments.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import denis.beck.dutyreminder_2.DutyReminderApp
import denis.beck.dutyreminder_2.epoxy.mappers.RemindPresentationMapper
import denis.beck.dutyreminder_2.epoxy.repositories.RemindRepository
import denis.beck.dutyreminder_2.models.RemindPresentationModel
import denis.beck.dutyreminder_2.utils.SingleLiveEvent
import denis.beck.dutyreminder_2.utils.tryLaunch
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class MainViewModel(
    private val remindRepository: RemindRepository,
    private val remindPresentationMapper: RemindPresentationMapper,
) : ViewModel() {

    private val _data = MutableLiveData<List<RemindPresentationModel>>()
    val data: LiveData<List<RemindPresentationModel>> = _data

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
                        remindPresentationMapper
                            .map(reminds)
                            .map { remind ->
                                remind.apply {
                                    onClickListener = {
                                        _goToRemind.postValue(id)
                                    }
                                }
                            }
                    }
                    .collect { list ->
                        _data.value = list
                    }
            }
        )
        viewModelScope.launch(Dispatchers.IO) {
            val reminds = remindRepository.getReminds()
            _data.postValue(
                remindPresentationMapper.map(reminds).map { remind ->
                    remind.apply {
                        onClickListener = {
                            _goToRemind.postValue(id)
                        }
                    }
                }
            )
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
                    RemindRepository(application.remindDatabase.reminderDao()),
                    RemindPresentationMapper(),
                ) as T
            }
        }
    }
}