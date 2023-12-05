package denis.beck.login_ui.domain

import android.util.Log
import denis.beck.common.di.FragmentScope
import denis.beck.login_ui.data.LoginRepository
import javax.inject.Inject

class LoginInteractorImpl @Inject constructor(loginRepository: LoginRepository): LoginInteractor {
    init {
        Log.d("INITINITING", "init $this")
    }
}