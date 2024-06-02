package com.paparimsky.studylast

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "questions")
class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "question")
    var question: String,
    @ColumnInfo(name = "first")
    var first: String,
    @ColumnInfo(name = "second")
    var second: String,
    @ColumnInfo(name = "third")
    var third: String,
    @ColumnInfo(name = "fourth")
    var fourth: String,
    @ColumnInfo(name = "right")
    var right: String
)