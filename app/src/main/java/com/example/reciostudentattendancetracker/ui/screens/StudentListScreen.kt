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
import com.example.reciostudentattendancetracker.data.StudentEntity
import com.example.reciostudentattendancetracker.viewmodel.AttendanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(
    classId: Int,
    viewModel: AttendanceViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val students by viewModel.getStudentsByClass(classId).collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var editingStudent by remember { mutableStateOf<StudentEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Students") },
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
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingStudent = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, "Add Student")
            }
        }
    ) { paddingValues ->
        if (students.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No students yet. Add one to get started!")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(students) { student ->
                    StudentCard(
                        student = student,
                        onEdit = {
                            editingStudent = student
                            showDialog = true
                        },
                        onDelete = { viewModel.deleteStudent(student) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (showDialog) {
        AddEditStudentDialog(
            student = editingStudent,
            onDismiss = { showDialog = false },
            onSave = { name, idNumber ->
                if (editingStudent != null) {
                    viewModel.updateStudent(
                        editingStudent!!.copy(
                            studentName = name,
                            studentIdNumber = idNumber
                        )
                    )
                } else {
                    viewModel.insertStudent(name, idNumber, classId)
                }
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentCard(
    student: StudentEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = student.studentName,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "ID: ${student.studentIdNumber}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Row {
                IconButton(onClick = onEdit) {
                    Icon(Icons.Default.Edit, "Edit", tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(Icons.Default.Delete, "Delete", tint = MaterialTheme.colorScheme.error)
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Delete Student") },
            text = { Text("Are you sure you want to delete this student? All attendance records will be removed.") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDeleteDialog = false
                }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun AddEditStudentDialog(
    student: StudentEntity?,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var studentName by remember { mutableStateOf(student?.studentName ?: "") }
    var studentIdNumber by remember { mutableStateOf(student?.studentIdNumber ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (student == null) "Add Student" else "Edit Student") },
        text = {
            Column {
                OutlinedTextField(
                    value = studentName,
                    onValueChange = { studentName = it },
                    label = { Text("Student Name") },
                    placeholder = { Text("e.g., Juan Dela Cruz") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = studentIdNumber,
                    onValueChange = { studentIdNumber = it },
                    label = { Text("Student ID Number") },
                    placeholder = { Text("e.g., 2025-12345") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (studentName.isNotBlank() && studentIdNumber.isNotBlank()) {
                        onSave(studentName, studentIdNumber)
                    }
                },
                enabled = studentName.isNotBlank() && studentIdNumber.isNotBlank()
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}