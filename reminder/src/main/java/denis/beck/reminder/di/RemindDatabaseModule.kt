package denis.beck.reminder.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import denis.beck.reminder.data.room.RemindDao
import denis.beck.reminder.data.room.RemindDatabase
import javax.inject.Singleton

@Module
class RemindDatabaseModule {

    @Singleton
    @Provides
    fun provideRemindDatabase(applicationContext: Context): RemindDatabase = Room.databaseBuilder(
        applicationContext,
        RemindDatabase::class.java, "reminder database"
    ).build()

    @Provides
    fun provideRemindDao(database: RemindDatabase): RemindDao = database.reminderDao()

}