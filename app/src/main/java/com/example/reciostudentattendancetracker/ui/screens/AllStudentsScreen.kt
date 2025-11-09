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

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "All Students",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.onSecondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondary.copy(alpha = 0.05f),
                            MaterialTheme.colorScheme.background
                        )
                    )
                )
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Implementations of Search and Filter Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Search Bar
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("Search Students") },
                            placeholder = { Text("Name or ID Number...") },
                            leadingIcon = {
                                Icon(Icons.Default.Search, contentDescription = null)
                            },
                            trailingIcon = {
                                if (searchQuery.isNotEmpty()) {
                                    IconButton(onClick = { searchQuery = "" }) {
                                        Icon(Icons.Default.Clear, "Clear")
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                                focusedLabelColor = MaterialTheme.colorScheme.secondary,
                                focusedLeadingIconColor = MaterialTheme.colorScheme.secondary
                            )
                        )

                        // Class Filter Implementations
                        // Initial Revisions of User Experience and Interaction as of Nov 8, 2025 at 11:40 PM
                    }
                }
            }
        }
    }
}