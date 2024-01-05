package com.prafull.imageTextExtractor.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.prafull.imageTextExtractor.model.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Upsert
    suspend fun updateHistory(historyEntity: HistoryEntity)

    @Query("SELECT * FROM history")
    fun getAllHistory(): Flow<List<HistoryEntity>>

    @Delete
    suspend fun deleteHistory(historyEntity: HistoryEntity)
}