package denis.beck.reminder

import android.content.Context
import androidx.room.Room
import denis.beck.reminder.data.room.RemindDatabase

object RemindDatabaseSingleton {

    lateinit var instance : RemindDatabase
        private set

    fun init(applicationContext: Context) {
        instance = Room.databaseBuilder(
            applicationContext,
            RemindDatabase::class.java, "reminder database"
        ).build()
    }
}