package com.skul9x.battu.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY createdAt DESC")
    fun getAllHistory(): Flow<List<HistoryEntity>>
    
    @Insert
    suspend fun insert(entity: HistoryEntity)
    
    @Delete
    suspend fun delete(entity: HistoryEntity)
    
    @Query("DELETE FROM history")
    suspend fun deleteAll()

    @Query("SELECT * FROM history WHERE id = :id")
    suspend fun getById(id: Int): HistoryEntity?
}
