package com.paparimsky.studylast

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface Dao {
    @Insert
    fun insertComment(comment: CommentEntity)
    @Query("SELECT * FROM comments")
    fun getAllComment(): Flow<List<CommentEntity>>
    @Query("SELECT * FROM questions")
    fun getAllQuestion(): Flow<List<QuestionEntity>>
    @Insert
    fun insertQuestion(question: QuestionEntity)
}