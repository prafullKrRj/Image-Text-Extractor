package com.prafull.imageTextExtractor.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.prafull.imageTextExtractor.model.HistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Insert
    fun insertHistory(history: HistoryEntity)

    @Query("SELECT * FROM History")
    fun getHistory(): Flow<List<HistoryEntity>>

    @Delete
    fun deleteHistory(history: HistoryEntity)
}