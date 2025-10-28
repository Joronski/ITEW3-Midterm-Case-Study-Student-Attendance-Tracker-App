package com.example.reciostudentattendancetracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_table WHERE classId = :classId ORDER BY studentName ASC")
    fun getStudentsByClass(classId: Int): Flow<List<StudentEntity>>

    @Query("SELECT * FROM student_table WHERE id = :studentId")
    suspend fun getStudentById(studentId: Int): StudentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity): Long

    @Update
    suspend fun updateStudent(student: StudentEntity)

    @Delete
    suspend fun deleteStudent(student: StudentEntity)
}