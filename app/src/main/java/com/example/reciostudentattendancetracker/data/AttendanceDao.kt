package com.example.reciostudentattendancetracker.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {
    @Query("SELECT * FROM attendance_table WHERE studentId = :studentId ORDER BY date DESC")
    fun getAttendanceByStudent(studentId: Int): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance_table WHERE date = :date AND studentId IN (SELECT id FROM student_table WHERE classId = :classId)")
    suspend fun getAttendanceByDateAndClass(date: String, classId: Int): List<AttendanceEntity>

    @Query("SELECT * FROM attendance_table WHERE date = :date AND studentId = :studentId")
    suspend fun getAttendanceByDateAndStudent(date: String, studentId: Int): AttendanceEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttendance(attendance: AttendanceEntity): Long

    @Update
    suspend fun updateAttendance(attendance: AttendanceEntity)

    @Delete
    suspend fun deleteAttendance(attendance: AttendanceEntity)

    @Query("SELECT COUNT(*) FROM attendance_table WHERE studentId = :studentId AND status = :status")
    suspend fun getAttendanceCountByStatus(studentId: Int, status: String): Int

    @Query("SELECT COUNT(*) FROM attendance_table WHERE studentId = :studentId")
    suspend fun getTotalAttendanceCount(studentId: Int): Int
}