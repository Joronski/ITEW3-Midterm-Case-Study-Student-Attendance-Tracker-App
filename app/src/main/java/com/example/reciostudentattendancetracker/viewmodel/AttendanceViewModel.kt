package com.example.reciostudentattendancetracker.viewmodel

import android.adservices.adid.AdId
import android.app.Application
import android.health.connect.datatypes.units.Percentage
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.reciostudentattendancetracker.data.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.sql.Date

class AttendanceViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AttendanceRepository

    init {
        val database = AppDatabase.getDatabase(application)
        repository = AttendanceRepository(database)
    }

    // Classes Implemented in ViewModel
    val allClasses: Flow<List<ClassEntity>> = repository.getAllClasses()

    fun insertClass(className: String, subjectName: String) {
        viewModelScope.launch {
            repository.insertClass(ClassEntity(className = className, subjectName = subjectName))
        }
    }

    fun updateClass(classEntity: ClassEntity) {
        viewModelScope.launch {
            repository.updateClass(classEntity)
        }
    }

    fun deleteClass(classEntity: ClassEntity) {
        viewModelScope.launch {
            repository.deleteClass(classEntity)
        }
    }

    // Students Implemented in ViewModel
    fun getStudentsByClass(classId: Int): Flow<List<StudentEntity>> = repository.getStudentByClass(classId)

    fun insertStudent(studentName: String, studentIdNumber: String, classId: Int) {
        viewModelScope.launch {
            repository.insertStudent(
                StudentEntity(
                    studentName = studentName,
                    studentIdNumber = studentIdNumber,
                    classId = classId
                )
            )
        }
    }

    fun updateStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.updateStudent(student)
        }
    }

    fun deleteStudent(student: StudentEntity) {
        viewModelScope.launch {
            repository.deleteStudent(student)
        }
    }

    // Attendance Implemented in ViewModel
    suspend fun getAttendanceByDateAndClass(date: String, classId: Int): List<AttendanceEntity> = repository.getAttendanceByDateAndClass(date, classId)

    suspend fun getAttendanceByDateAndStudent(date: String, studentId: Int): AttendanceEntity? = repository.getAttendanceByDateAndStudent(date, studentId)

    fun markAttendance(date: String, studentId: Int, status: String) {
        viewModelScope.launch {
            val existing = repository.getAttendanceByDateAndStudent(date, studentId)
            if (existing != null) {
                repository.updateAttendance(existing.copy(status = status))
            } else {
                repository.insertAttendance(
                    AttendanceEntity(
                        date = date,
                        studentId = studentId,
                        status = status
                    )
                )
            }
        }
    }

    suspend fun getAttendanceSummary(studentId: Int): AttendanceSummary {
        val present = repository.getAttendanceCountByStatus(studentId, "Present")
        val absent = repository.getAttendanceCountByStatus(studentId, "Absent")
        val late = repository.getAttendanceCountByStatus(studentId, "Late")
        val total = repository.getTotalAttendanceCount(studentId)

        val percentage = if (total > 0) {
            (present.toFloat() / total.toFloat() * 100)
        } else {
            0f
        }

        return AttendanceSummary(present, absent, late, total, percentage)
    }
}

data class AttendanceSummary(
    val present: Int,
    val absent: Int,
    val late: Int,
    val total: Int,
    val percentage: Float
)