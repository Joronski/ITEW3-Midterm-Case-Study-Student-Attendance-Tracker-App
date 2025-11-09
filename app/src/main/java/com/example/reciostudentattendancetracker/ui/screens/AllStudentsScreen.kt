package com.example.reciostudentattendancetracker.ui.screens

import android.graphics.drawable.Icon
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
                        if (classes.isNotEmpty()) {
                            var expanded by remember { mutableStateOf(false) }

                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = it }
                            ) {
                                OutlinedTextField(
                                    value = if (selectedClassFilter == null) {
                                        "All Classes"
                                    } else {
                                        classes.find { it.id == selectedClassFilter }?.className ?: "All Classes"
                                    },
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Filter by Class") },
                                    leadingIcon = {
                                        Icon(Icons.Default.List, contentDescription = null)
                                    },
                                    trailingIcon = {
                                        Row {
                                            if (selectedClassFilter != null) {
                                                IconButton(onClick = { selectedClassFilter = null }) {
                                                    Icon(Icons.Default.Clear, "Clear filter")
                                                }
                                            }
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }
                                ) {
                                    DropdownMenuItem(
                                        text = { Text("All Classes") },
                                        onClick = {
                                            selectedClassFilter = null
                                            expanded = false
                                        },
                                        leadingIcon = {
                                            Icon(Icons.Default.Person, contentDescription = null)
                                        }
                                    )
                                    Divider()
                                    classes.forEach { classItem ->
                                        DropdownMenuItem(
                                            text = { Text("${classItem.className} - ${classItem.subjectName}") },
                                            onClick = {
                                                selectedClassFilter = classItem.id
                                                expanded = false
                                            },
                                            leadingIcon = {
                                                Icon(Icons.Default.Face, contentDescription = null)
                                            }
                                        )
                                    }
                                }
                            }
                        }

                        // Student Implementation Count
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Default.AccountBox,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.secondary
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "Total Students: ${filteredStudents.size}",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Student List Implementation
                if (filteredStudents.isEmpty()) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                        )
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(48.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Text(
                                "No Students Found",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                "Try adjusting your search or filter",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(filteredStudents) { studentsWithClass ->
                            ViewOnlyStudentCard(
                                studentWithClass = studentsWithClass
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ViewOnlyStudentCard(
    studentWithClass: StudentWithClass
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.3f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.secondary,
                                MaterialTheme.colorScheme.secondaryContainer
                            )
                        ),
                        shape = RoundedCornerShape(15.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = studentWithClass.student.studentName.last().uppercase(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = studentWithClass.student.studentName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = studentWithClass.student.studentIdNumber,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.secondary
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = "${studentWithClass.className} - ${studentWithClass.subjectName}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}