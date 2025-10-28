package com.example.reciostudentattendancetracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    @Query("SELECT * FROM class_table ORDER BY className ASC")
    fun getAllClasses(): Flow<List<ClassEntity>>

    @Query("SELECT * FROM class_table WHERE id = :classId")
    suspend fun getClassById(classId: Int): ClassEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(classEntity: ClassEntity): Long

    @Update
    suspend fun updateClass(classEntity: ClassEntity)

    @Delete
    suspend fun deleteClass(classEntity: ClassEntity)
}