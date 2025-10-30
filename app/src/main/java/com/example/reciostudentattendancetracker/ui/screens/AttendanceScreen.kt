package com.example.reciostudentattendancetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
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
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    viewModel: AttendanceViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val classes by viewModel.allClasses.collectAsState(initial = emptyList())
    var selectedClass by remember { mutableStateOf<ClassEntity?>(null) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    var showDatePicker by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mark Attendance") },
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
            // Class Selector
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

                // Date Selector
                OutlinedButton(
                    onClick = { showDatePicker = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.DateRange, "Select Date")
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(selectedDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy")))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Student List with Attendance
                if (selectedClass != null) {
                    AttendanceList(
                        classId = selectedClass!!.id,
                        date = selectedDate.toString(),
                        viewModel = viewModel
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Please select a class to mark attendance")
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No classes available. Please add classes first.")
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            selectedDate = selectedDate,
            onDateSelected = { selectedDate = it },
            onDismiss = { showDatePicker = false }
        )
    }
}

@Composable
fun AttendanceList(
    classId: Int,
    date: String,
    viewModel: AttendanceViewModel
) {
    val students by viewModel.getStudentsByClass(classId).collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var attendanceMap by remember { mutableStateOf<Map<Int, String>>(emptyMap()) }

    LaunchedEffect(date, classId) {
        scope.launch {
            val records = viewModel.getAttendanceByDateAndClass(date, classId)
            attendanceMap = records.associate { it.studentId to it.status }
        }
    }

    if (students.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("No students in this class")
        }
    } else {
        LazyColumn {
            items(students) { student ->
                AttendanceRow(
                    student = student,
                    currentStatus = attendanceMap[student.id],
                    onStatusChange = { status ->
                        viewModel.markAttendance(date, student.id, status)
                        attendanceMap = attendanceMap + (student.id to status)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceRow(
    student: StudentEntity,
    currentStatus: String?,
    onStatusChange: (String) -> Unit
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

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = currentStatus == "Present",
                    onClick = { onStatusChange("Present") },
                    label = { Text("Present") },
                    leadingIcon = if (currentStatus == "Present") {
                        { Icon(Icons.Default.Check, "Selected") }
                    } else null,
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )

                FilterChip(
                    selected = currentStatus == "Absent",
                    onClick = { onStatusChange("Absent") },
                    label = { Text("Absent") },
                    leadingIcon = if (currentStatus == "Absent") {
                        { Icon(Icons.Default.Close, "Selected") }
                    } else null,
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.error,
                        selectedLabelColor = MaterialTheme.colorScheme.onError
                    )
                )

                FilterChip(
                    selected = currentStatus == "Late",
                    onClick = { onStatusChange("Late") },
                    label = { Text("Late") },
                    leadingIcon = if (currentStatus == "Late") {
                        { Icon(Icons.Default.Check, "Selected") }
                    } else null,
                    modifier = Modifier.weight(1f),
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePickerDialog(
    selectedDate: LocalDate,
    onDateSelected: (LocalDate) -> Unit,
    onDismiss: () -> Unit
) {
    var tempDate by remember { mutableStateOf(selectedDate) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Selected Date") },
        text = {
            Column {
                Text("Selected: ${tempDate.format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))}")
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { tempDate = tempDate.minusDays(1) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Previous Day")
                    }
                    Button(
                        onClick = { tempDate = LocalDate.now() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Today")
                    }
                    Button(
                        onClick = { tempDate = tempDate.plusDays(1) },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Next Day")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(tempDate)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}