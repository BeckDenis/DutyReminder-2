package denis.beck.dutyreminder_2

import android.app.Application
import androidx.room.Room
import denis.beck.dutyreminder_2.room.RemindDatabase
import timber.log.Timber

class DutyReminderApp : Application() {

    lateinit var remindDatabase : RemindDatabase

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        remindDatabase = Room.databaseBuilder(
            applicationContext,
            RemindDatabase::class.java, "reminder database"
        ).build()
    }
}