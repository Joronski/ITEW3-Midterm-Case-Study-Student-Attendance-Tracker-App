package com.example.reciostudentattendancetracker.data

import kotlinx.coroutines.flow.Flow

class AttendanceRepository(private val database: AppDatabase) {
    // Class Operations Implemented in Repo
    fun getAllClasses(): Flow<List<ClassEntity>> = database.classDao().getAllClasses()

    suspend fun getClassById(classId: Int): ClassEntity? = database.classDao().getClassById(classId)

    suspend fun insertClass(classEntity: ClassEntity): Long = database.classDao().insertClass(classEntity)

    suspend fun updateClass(classEntity: ClassEntity) = database.classDao().updateClass(classEntity)

    suspend fun deleteClass(classEntity: ClassEntity) = database.classDao().deleteClass(classEntity)

    // Student Operations Implemented in Repo
    fun getStudentByClass(classId: Int): Flow<List<StudentEntity>> = database.studentDao().getStudentsByClass(classId)

    suspend fun getStudentById(studentId: Int): StudentEntity? = database.studentDao().getStudentById(studentId)

    suspend fun insertStudent(student: StudentEntity): Long = database.studentDao().insertStudent(student)

    suspend fun updateStudent(student: StudentEntity) = database.studentDao().updateStudent(student)

    suspend fun deleteStudent(student: StudentEntity) = database.studentDao().deleteStudent(student)

    // Attendance Operations Implemented in Repo
    fun getAttendanceByStudent(studentId: Int): Flow<List<AttendanceEntity>> = database.attendanceDao().getAttendanceByStudent(studentId)

    suspend fun getAttendanceByDateAndClass(date: String, classId: Int): List<AttendanceEntity> = database.attendanceDao().getAttendanceByDateAndClass(date, classId)

    suspend fun getAttendanceByDateAndStudent(date: String, studentId: Int): AttendanceEntity? = database.attendanceDao().getAttendanceByDateAndStudent(date, studentId)

    suspend fun insertAttendance(attendance: AttendanceEntity): Long = database.attendanceDao().insertAttendance(attendance)

    suspend fun updateAttendance(attendance: AttendanceEntity) = database.attendanceDao().updateAttendance(attendance)

    suspend fun deleteAttendance(attendance: AttendanceEntity) = database.attendanceDao().deleteAttendance(attendance)

    suspend fun getAttendanceCountByStatus(studentId: Int, status: String): Int = database.attendanceDao().getAttendanceCountByStatus(studentId, status)

    suspend fun getTotalAttendanceCount(studentId: Int): Int = database.attendanceDao().getTotalAttendanceCount(studentId)
}