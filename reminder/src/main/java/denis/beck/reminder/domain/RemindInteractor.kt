package denis.beck.reminder.domain

import denis.beck.reminder.data.RemindRepository
import denis.beck.reminder.data.room.RemindEntity
import denis.beck.reminder.domain.remindManager.RemindDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RemindInteractor @Inject constructor(private val remindRepository: RemindRepository) {
    fun getReminds(): Flow<List<RemindDomainModel>> = remindRepository
        .getRemindsByFlow()
        .map { list ->
            list.map(RemindDomainModel::fromEntity)
        }

    suspend fun getRemind(id: Long): RemindDomainModel? = remindRepository
        .getRemind(id)
        ?.let(RemindDomainModel::fromEntity)

}