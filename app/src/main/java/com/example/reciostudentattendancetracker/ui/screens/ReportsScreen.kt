package com.example.reciostudentattendancetracker.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reciostudentattendancetracker.data.ClassEntity
import com.example.reciostudentattendancetracker.data.StudentEntity
import com.example.reciostudentattendancetracker.viewmodel.AttendanceViewModel
import com.example.reciostudentattendancetracker.viewmodel.AttendanceSummary
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsScreen(
    viewModel: AttendanceViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    val classes by viewModel.allClasses.collectAsState(initial = emptyList())
    var selectedView by remember { mutableStateOf(ReportView.PER_CLASS) }
    var selectedClass by remember { mutableStateOf<ClassEntity?>(null) }
    var showDateFilter by remember { mutableStateOf(false) }
    var startDate by remember { mutableStateOf<LocalDate?>(null) }
    var endDate by remember { mutableStateOf<LocalDate?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Attendance Summary",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showDateFilter = true }) {
                        Icon(
                            Icons.Default.DateRange,
                            "Date Filter",
                            tint = if (startDate != null || endDate != null)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
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
                            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.05f),
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
                // View Toggle Card Implementation
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "View Mode",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            FilterChip(
                                selected = selectedView == ReportView.PER_CLASS,
                                onClick = {
                                    selectedView = ReportView.PER_CLASS
                                    selectedClass = null
                                },
                                label = { Text("Per Class") },
                                leadingIcon = if (selectedView == ReportView.PER_CLASS) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                                } else null,
                                modifier = Modifier.weight(1f)
                            )
                            FilterChip(
                                selected = selectedView == ReportView.OVERALL,
                                onClick = {
                                    selectedView = ReportView.OVERALL
                                    selectedClass = null
                                },
                                label = { Text("Overall") },
                                leadingIcon = if (selectedView == ReportView.OVERALL) {
                                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                                } else null,
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Date Filter Display Implementation
                        if (startDate != null || endDate != null) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            Icons.Default.List,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column {
                                            Text(
                                                "Date Filter Applied",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Text(
                                                "${startDate?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) ?: "Start"} - ${endDate?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) ?: "End"}",
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                    IconButton(onClick = {
                                        startDate = null
                                        endDate = null
                                    }) {
                                        Icon(Icons.Default.Close, "Clear filter")
                                    }
                                }
                            }
                        }

                        // Class Selector for Per Class view Implementation
                        if (selectedView == ReportView.PER_CLASS && classes.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(12.dp))
                            var expanded by remember { mutableStateOf(false) }

                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = { expanded = it }
                            ) {
                                OutlinedTextField(
                                    value = selectedClass?.let { "${it.className} - ${it.subjectName}" } ?: "Select Class",
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("Class") },
                                    leadingIcon = {
                                        Icon(Icons.Default.Face, contentDescription = null)
                                    },
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                                        focusedLeadingIconColor = MaterialTheme.colorScheme.tertiary
                                    )
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
                                            },
                                            leadingIcon = {
                                                Icon(Icons.Default.Face, contentDescription = null)
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Content based on selected view Implementation
                when {
                    selectedView == ReportView.PER_CLASS && selectedClass != null -> {
                        PerClassReportList(
                            classId = selectedClass!!.id,
                            viewModel = viewModel,
                            startDate = startDate,
                            endDate = endDate
                        )
                    }
                    selectedView == ReportView.OVERALL -> {
                        OverallReportList(
                            viewModel = viewModel,
                            classes = classes,
                            startDate = startDate,
                            endDate = endDate
                        )
                    }
                    selectedView == ReportView.PER_CLASS && classes.isEmpty() -> {
                        EmptyStateCard("No classes available")
                    }
                    selectedView == ReportView.PER_CLASS -> {
                        EmptyStateCard("Select a class to view reports")
                    }
                }
            }
        }
    }

    if (showDateFilter) {
        DateRangeFilterDialog(
            startDate = startDate,
            endDate = endDate,
            onDismiss = { showDateFilter = false },
            onApply = { start, end ->
                startDate = start
                endDate = end
                showDateFilter = false
            }
        )
    }
}

enum class ReportView {
    PER_CLASS, OVERALL
}

@Composable
fun PerClassReportList(
    classId: Int,
    viewModel: AttendanceViewModel,
    startDate: LocalDate?,
    endDate: LocalDate?
) {
    val students by viewModel.getStudentsByClass(classId).collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    var summaryMap by remember { mutableStateOf<Map<Int, AttendanceSummary>>(emptyMap()) }

    LaunchedEffect(students, startDate, endDate) {
        scope.launch {
            val summaries = mutableMapOf<Int, AttendanceSummary>()
            students.forEach { student ->
                summaries[student.id] = viewModel.getAttendanceSummary(student.id, startDate, endDate)
            }
            summaryMap = summaries
        }
    }

    if (students.isEmpty()) {
        EmptyStateCard("No students in this class")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(students) { student ->
                val summary = summaryMap[student.id]
                if (summary != null) {
                    ModernAttendanceReportCard(
                        student = student,
                        summary = summary
                    )
                }
            }
        }
    }
}

@Composable
fun OverallReportList(
    viewModel: AttendanceViewModel,
    classes: List<ClassEntity>,
    startDate: LocalDate?,
    endDate: LocalDate?
) {
    val scope = rememberCoroutineScope()
    var allStudentsWithSummary by remember { mutableStateOf<List<Pair<StudentWithClassInfo, AttendanceSummary>>>(emptyList()) }

    LaunchedEffect(classes, startDate, endDate) {
        scope.launch {
            val results = mutableListOf<Pair<StudentWithClassInfo, AttendanceSummary>>()
            classes.forEach { classEntity ->
                viewModel.getStudentsByClass(classEntity.id).collect { students ->
                    students.forEach { student ->
                        val summary = viewModel.getAttendanceSummary(student.id, startDate, endDate)
                        results.add(
                            Pair(
                                StudentWithClassInfo(student, classEntity.className, classEntity.subjectName),
                                summary
                            )
                        )
                    }
                    allStudentsWithSummary = results.sortedByDescending { it.second.percentage }
                }
            }
        }
    }

    if (allStudentsWithSummary.isEmpty()) {
        EmptyStateCard("No students found")
    } else {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.3f)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccountBox, contentDescription = null, tint = MaterialTheme.colorScheme.tertiary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Total Students: ${allStudentsWithSummary.size}",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            items(allStudentsWithSummary) { (studentInfo, summary) ->
                OverallAttendanceReportCard(
                    studentInfo = studentInfo,
                    summary = summary
                )
            }
        }
    }
}

data class StudentWithClassInfo(
    val student: StudentEntity,
    val className: String,
    val subjectName: String
)

@Composable
fun OverallAttendanceReportCard(
    studentInfo: StudentWithClassInfo,
    summary: AttendanceSummary
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            when {
                                summary.percentage >= 90 -> Color(0xFF43A047).copy(alpha = 0.15f)
                                summary.percentage >= 75 -> Color(0xFFFFB300).copy(alpha = 0.15f)
                                else -> Color(0xFFE53935).copy(alpha = 0.15f)
                            },
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(20.dp)
        ) {
            // Student Info Implementation
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.tertiary,
                                    MaterialTheme.colorScheme.tertiaryContainer
                                )
                            ),
                            shape = RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = studentInfo.student.studentName.first().uppercase(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = studentInfo.student.studentName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "ID: ${studentInfo.student.studentIdNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Face,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${studentInfo.className} - ${studentInfo.subjectName}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Summary Content Implementation
            AttendanceSummaryContent(summary)
        }
    }
}

@Composable
fun ModernAttendanceReportCard(
    student: StudentEntity,
    summary: AttendanceSummary
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
                shape = RoundedCornerShape(20.dp),
                spotColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f)
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            when {
                                summary.percentage >= 90 -> Color(0xFF43A047).copy(alpha = 0.15f)
                                summary.percentage >= 75 -> Color(0xFFFFB300).copy(alpha = 0.15f)
                                else -> Color(0xFFE53935).copy(alpha = 0.15f)
                            },
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
                .padding(20.dp)
        ) {
            // Student Info Implementation
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .background(
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.tertiary,
                                    MaterialTheme.colorScheme.tertiaryContainer
                                )
                            ),
                            shape = RoundedCornerShape(15.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = student.studentName.first().uppercase(),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = student.studentName,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "ID: ${student.studentIdNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            AttendanceSummaryContent(summary)
        }
    }
}

@Composable
fun AttendanceSummaryContent(summary: AttendanceSummary) {
    // Attendance Percentage Implementation
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = when {
                summary.percentage >= 90 -> Color(0xFF43A047).copy(alpha = 0.2f)
                summary.percentage >= 75 -> Color(0xFF4DB6AC).copy(alpha = 0.2f)
                summary.percentage >= 60 -> Color(0xFFFFB300).copy(alpha = 0.2f)
                else -> Color(0xFFE53935).copy(alpha = 0.2f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Attendance Rate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = when {
                        summary.percentage >= 90 -> "Excellent"
                        summary.percentage >= 75 -> "Good"
                        summary.percentage >= 60 -> "Fair"
                        else -> "Needs Improvement"
                    },
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Text(
                text = String.format("%.1f%%", summary.percentage),
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = when {
                    summary.percentage >= 90 -> Color(0xFF43A047)
                    summary.percentage >= 75 -> Color(0xFF4DB6AC)
                    summary.percentage >= 60 -> Color(0xFFFFB300)
                    else -> Color(0xFFE53935)
                }
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Progress Bar Implementation
    LinearProgressIndicator(
        progress = { summary.percentage / 100f },
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp),
        color = when {
            summary.percentage >= 90 -> Color(0xFF43A047)
            summary.percentage >= 75 -> Color(0xFF4DB6AC)
            summary.percentage >= 60 -> Color(0xFFFFB300)
            else -> Color(0xFFE53935)
        },
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
        strokeCap = androidx.compose.ui.graphics.StrokeCap.Round
    )

    Spacer(modifier = Modifier.height(20.dp))

    // Statistics Grid Implementation
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            label = "Present",
            value = summary.present.toString(),
            icon = Icons.Default.CheckCircle,
            color = Color(0xFF43A047),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Late",
            value = summary.late.toString(),
            icon = Icons.Default.Warning,
            color = Color(0xFFFFB300),
            modifier = Modifier.weight(1f)
        )
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            label = "Absent",
            value = summary.absent.toString(),
            icon = Icons.Default.Clear,
            color = Color(0xFFE53935),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            label = "Total Days",
            value = summary.total.toString(),
            icon = Icons.Default.DateRange,
            color = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun EmptyStateCard(message: String) {
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
                Icons.Default.Menu,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateRangeFilterDialog(
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDismiss: () -> Unit,
    onApply: (LocalDate?, LocalDate?) -> Unit
) {
    var tempStartDate by remember { mutableStateOf(startDate) }
    var tempEndDate by remember { mutableStateOf(endDate) }
    var selectingStart by remember { mutableStateOf(true) }

    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icon(Icons.Default.DateRange, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        },
        title = { Text("Date Range Filter", fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Select date range for filtering attendance records:")

                // Start Date Implementation
                OutlinedCard(
                    onClick = { selectingStart = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("Start Date", style = MaterialTheme.typography.labelMedium)
                            Text(
                                tempStartDate?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) ?: "Not set",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (tempStartDate != null) {
                            IconButton(onClick = { tempStartDate = null }) {
                                Icon(Icons.Default.Clear, "Clear")
                            }
                        }
                    }
                }

                // End Date Implementation
                OutlinedCard(
                    onClick = { selectingStart = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text("End Date", style = MaterialTheme.typography.labelMedium)
                            Text(
                                tempEndDate?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) ?: "Not set",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        if (tempEndDate != null) {
                            IconButton(onClick = { tempEndDate = null }) {
                                Icon(Icons.Default.Clear, "Clear")
                            }
                        }
                    }
                }

                // Quick Actions Implementation
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick = {
                            tempStartDate = LocalDate.now().minusDays(7)
                            tempEndDate = LocalDate.now()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Last 7 Days", style = MaterialTheme.typography.labelSmall)
                    }
                    OutlinedButton(
                        onClick = {
                            tempStartDate = LocalDate.now().minusMonths(1)
                            tempEndDate = LocalDate.now()
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Last Month", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onApply(tempStartDate, tempEndDate) },
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Apply Filter")
            }
        },
        dismissButton = {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                TextButton(
                    onClick = {
                        tempStartDate = null
                        tempEndDate = null
                    }
                ) {
                    Text("Clear All")
                }
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        }
    )
}