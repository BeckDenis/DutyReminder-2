package denis.beck.login_ui.data

import denis.beck.login_ui.data.external.LoginExternalDataSource
import denis.beck.login_ui.data.local.LoginLocalDataSource
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val localDataSource: LoginLocalDataSource,
    private val externalDataSource: LoginExternalDataSource,
) : LoginRepository