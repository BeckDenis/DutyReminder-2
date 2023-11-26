package denis.beck.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

interface Navigator {
    fun navigateToLogin(fragmentManager: FragmentManager)
    fun navigateToMain(fragmentManager: FragmentManager)
    fun navigateToReminder(fragmentManager: FragmentManager, remindId: Long?)
    fun navigateBack(activity: AppCompatActivity)
}