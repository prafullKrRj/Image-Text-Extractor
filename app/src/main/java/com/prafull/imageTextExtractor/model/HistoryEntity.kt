package com.prafull.imageTextExtractor.model

import android.net.Uri
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "History")
data class HistoryEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val image: String,
    val text: String
) {
    fun toModel(): HistoryModel {
        return HistoryModel(
            Uri.parse(image),
            text
        )
    }
}

data class HistoryModel(
    val image: Uri,
    val text: String
)