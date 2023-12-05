package denis.beck.login_ui.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import denis.beck.common.extensions.tryLaunch
import denis.beck.common.liveData.SingleLiveEvent
import denis.beck.login_ui.domain.LoginInteractor
import denis.beck.preferences.SharedPreferencesManager
import kotlinx.coroutines.Dispatchers
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
        Log.d("INITINITING", "init $this")
        interactor
    }

    override fun onLogIn() {
        sharedPreferencesManager.setAuthorized(true)
        _goToReminderList.value = Unit
    }
}