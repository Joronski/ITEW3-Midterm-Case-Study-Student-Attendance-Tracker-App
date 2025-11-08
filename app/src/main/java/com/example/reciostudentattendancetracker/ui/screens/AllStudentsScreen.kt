package com.example.reciostudentattendancetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reciostudentattendancetracker.data.StudentEntity
import com.example.reciostudentattendancetracker.viewmodel.AttendanceViewModel
import kotlinx.coroutines.launch

data class StudentWithClass(
    val student: StudentEntity,
    val className: String,
    val subjectName: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllStudentsScreen(
    viewModel: AttendanceViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val classes by viewModel.allClasses.collectAsState(initial = emptyList())
    val studentsWithClass = remember { mutableStateOf<List<StudentWithClass>>(emptyList()) }
    val scope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf("") }
    var selectedClassFilter by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(classes) {
        scope.launch {
            val allStudents = mutableListOf<StudentWithClass>()
            classes.forEach { classEntity ->
                viewModel.getStudentsByClass(classEntity.id).collect { students ->
                    students.forEach { student ->
                        allStudents.add(
                            StudentWithClass(
                                student = student,
                                className = classEntity.className,
                                subjectName = classEntity.subjectName
                            )
                        )
                    }
                    studentsWithClass.value = allStudents.sortedBy { it.student.studentName }
                }
            }
        }
    }

    val filteredStudents = studentsWithClass.value.filter { studentWithClass ->
        val matchesSearch = studentWithClass.student.studentName.contains(searchQuery, ignoreCase = true) || studentWithClass.student.studentIdNumber.contains(searchQuery, ignoreCase = true)
        val matchesClass = selectedClassFilter == null || studentWithClass.student.classId == selectedClassFilter
        matchesSearch && matchesClass
    }


}