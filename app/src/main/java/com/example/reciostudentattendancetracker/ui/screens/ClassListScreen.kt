package com.example.reciostudentattendancetracker.ui.screens

import androidx.compose.foundation.clickable
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
import androidx.room.Delete
import com.example.reciostudentattendancetracker.data.ClassEntity
import com.example.reciostudentattendancetracker.viewmodel.AttendanceViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassListScreen(
    viewModel: AttendanceViewModel = viewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToStudents: (Int) -> Unit
) {
    val classes by viewModel.allClasses.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }
    var editingClass by remember { mutableStateOf<ClassEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Classes") },
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
                editingClass = null
                showDialog = true
            }) {
                Icon(Icons.Default.Add, "Add Class")
            }
        }
    ) { paddingValues ->
        if (classes.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No classes yet. Add one to get started!")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                items(classes) { classItem ->
                    ClassCard(
                        classEntity = classItem,
                        onClick = { onNavigateToStudents(classItem.id) },
                        onEdit = {
                            editingClass = classItem
                            showDialog = true
                        },
                        onDelete = { viewModel.deleteClass(classItem) }
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }

    if (showDialog) {
        AddEditClassDialog(
            classEntity = editingClass,
            onDismiss = { showDialog = false },
            onSave = { className, subjectName ->
                if (editingClass != null) {
                    viewModel.updateClass(
                        editingClass!!.copy(
                            className = className,
                            subjectName = subjectName
                        )
                    )
                } else {
                    viewModel.insertClass(className, subjectName)
                }
                showDialog = false
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClassCard(
    classEntity: ClassEntity,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = classEntity.className,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = classEntity.subjectName,
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
            title = { Text("Delete Class") },
            text = { Text("Are you sure you want to delete this class? All students and attendance records will be removed.") },
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
fun AddEditClassDialog(
    classEntity: ClassEntity?,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit
) {
    var className by remember { mutableStateOf(classEntity?.className ?: "") }
    var subjectName by remember { mutableStateOf(classEntity?.subjectName ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (classEntity == null) "Add Class" else "Edit Class") },
        text = {
            Column {
                OutlinedTextField(
                    value = className,
                    onValueChange = { className = it },
                    label = { Text("Class Name") },
                    placeholder = { Text("e.g., BSIT 2A") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = subjectName,
                    onValueChange = { subjectName = it },
                    label = { Text("Subject Name") },
                    placeholder = { Text("e.g., Mobile Programming") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (className.isNotBlank() && subjectName.isNotBlank()) {
                        onSave(className, subjectName)
                    }
                },
                enabled = className.isNotBlank() && subjectName.isNotBlank()
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