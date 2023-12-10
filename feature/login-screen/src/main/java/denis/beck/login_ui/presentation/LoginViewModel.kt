package denis.beck.login_ui.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import denis.beck.common_core.liveData.SingleLiveEvent
import denis.beck.login_ui.domain.LoginInteractor
import denis.beck.preferences.SharedPreferencesManager
import javax.inject.Inject

interface LoginViewModel {
    val goToReminderList: LiveData<Unit>

    fun onLogIn()
}

class LoginViewModelImpl @Inject constructor(
    private val sharedPreferencesManager: SharedPreferencesManager,
    interactor: LoginInteractor,
) : ViewModel(), LoginViewModel {

    private val _goToReminderList = SingleLiveEvent<Unit>()
    override val goToReminderList: LiveData<Unit> = _goToReminderList

    init {
        interactor
    }

    override fun onLogIn() {
        sharedPreferencesManager.setAuthorized(true)
        _goToReminderList.value = Unit
    }
}