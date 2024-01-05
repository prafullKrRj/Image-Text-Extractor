package com.prafull.imageTextExtractor.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.prafull.imageTextExtractor.model.HistoryEntity


@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class HistoryDB: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDB? = null
        fun getDatabase(context: Context) : HistoryDB {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(context, HistoryDB::class.java, "history_db")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }

}