package com.example.reciostudentattendancetracker.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reciostudentattendancetracker.data.ClassEntity
import com.example.reciostudentattendancetracker.data.StudentEntity
import com.example.reciostudentattendancetracker.viewmodel.AttendanceViewModel
import com.example.reciostudentattendancetracker.viewmodel.AttendanceSummary
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    viewModel: AttendanceViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val classes by viewModel.allClasses.collectAsState(initial = emptyList())
    var selectedClass by remember { mutableStateOf<ClassEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Attendance Reports") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            if (classes.isNotEmpty()) {
                var expanded by remember { mutableStateOf(false) }

                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedClass?.className ?: "Select Class",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Class") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        classes.forEach { classItem ->
                            DropdownMenuItem(
                                text = { Text("${classItem.className} - ${classItem.subjectName}") },
                                onClick = {
                                    selectedClass = classItem
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (selectedClass != null) {
                    AttendanceReportList(
                        classId = selectedClass!!.id,
                        viewModel = viewModel
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Please select a class to view reports")
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No classes available")
                }
            }
        }
    }
}

@Composable
fun AttendanceReportList(
    classId: Int,
    viewModel: AttendanceViewModel
) {
    val students by viewModel.getStudentsByClass(classId).collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var summaryMap by remember { mutableStateOf<Map<Int, AttendanceSummary>>(emptyMap()) }

    LaunchedEffect(students) {
        scope.launch {
            val summaries = mutableMapOf<Int, AttendanceSummary>()
            students.forEach { student ->
                summaries[student.id] = viewModel.getAttendanceSummary(student.id)
            }
            summaryMap = summaries
        }
    }

    if (students.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No Students in this class")
        }
    } else {
        LazyColumn {
            items(students) { student ->
                val summary = summaryMap[student.id]
                if (summary != null) {
                    AttendanceReportCard(
                        student = student,
                        summary = summary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceReportCard(
    student: StudentEntity,
    summary: AttendanceSummary
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = student.studentName,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "ID: ${student.studentIdNumber}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Attendance Percentage Implementations
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Attendance Rate",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = String.format("%.1f%%", summary.percentage),
                    style = MaterialTheme.typography.headlineSmall,
                    color = when {
                        summary.percentage >= 90 -> MaterialTheme.colorScheme.primary
                        summary.percentage >= 75 -> MaterialTheme.colorScheme.tertiary
                        else -> MaterialTheme.colorScheme.error
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar Implementation
            LinearProgressIndicator(
                progress = { summary.percentage / 100f },
                modifier = Modifier.fillMaxWidth(),
                color = when {
                    summary.percentage >= 90 -> MaterialTheme.colorScheme.primary
                    summary.percentage >= 75 -> MaterialTheme.colorScheme.tertiary
                    else -> MaterialTheme.colorScheme.error
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Statistics Implementations
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatisticItem(
                    label = "Present",
                    value = summary.present.toString(),
                    color = MaterialTheme.colorScheme.primary
                )
                StatisticItem(
                    label = "Late",
                    value = summary.late.toString(),
                    color = MaterialTheme.colorScheme.tertiary
                )
                StatisticItem(
                    label = "Absent",
                    value = summary.absent.toString(),
                    color = MaterialTheme.colorScheme.error
                )
                StatisticItem(
                    label = "Total",
                    value = summary.total.toString(),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun StatisticItem(
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            color = color
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}