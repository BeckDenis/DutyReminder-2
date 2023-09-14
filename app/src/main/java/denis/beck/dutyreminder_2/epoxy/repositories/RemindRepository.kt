package denis.beck.dutyreminder_2.epoxy.repositories

import denis.beck.dutyreminder_2.models.RemindDomainModel
import denis.beck.dutyreminder_2.room.RemindDao

class RemindRepository(private val remindDao: RemindDao) {

    suspend fun getReminds() = remindDao.getAll().map { it.toDomain() }

    suspend fun getRemind(id: Long?): RemindDomainModel? {
        id ?: return null
        return remindDao.get(id)?.toDomain()
    }

    suspend fun setRemind(remindDomainModel: RemindDomainModel) =
        remindDao.insert(remindDomainModel.toEntity())

    suspend fun deleteRemind(remindId: Long) = remindDao.delete(remindId)
}