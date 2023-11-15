package denis.beck.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager

interface Navigator {
    fun navigateToReminder(fragmentManager: FragmentManager, remindId: Long?)
    fun navigateBack(activity: AppCompatActivity)
}