package com.paparimsky.studylast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "comments")
data class CommentEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "text")
    var text: String,
    @ColumnInfo(name = "imageData")
    var imageData: String?
)
