package com.example.reciostudentattendancetracker.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MainScreen(
    onNavigateToClasses: () -> Unit,
    onNavigateToStudents: () -> Unit,
    onNavigateToAttendance: () -> Unit,
    onNavigateToReports: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Welcome Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        modifier = Modifier.size(80.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Dangal Attendance Tracker",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Track and manage attendance easily",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                    )
                }
            }

            // Menu Cards
            ModernMenuCard(
                title = "Class List",
                description = "Manage and view all classes",
                icon = Icons.Default.Face,
                onClick = onNavigateToClasses,
                gradient = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primaryContainer
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernMenuCard(
                title = "Student List",
                description = "View all students in the system",
                icon = Icons.Default.Info,
                onClick = onNavigateToStudents,
                gradient = listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.secondaryContainer
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernMenuCard(
                title = "Mark Attendance",
                description = "Record daily student attendance",
                icon = Icons.Default.CheckCircle,
                onClick = onNavigateToAttendance,
                gradient = listOf(
                    MaterialTheme.colorScheme.secondary,
                    MaterialTheme.colorScheme.secondaryContainer
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            ModernMenuCard(
                title = "Attendance Summary",
                description = "Check attendance statistics & summaries",
                icon = Icons.Default.List,
                onClick = onNavigateToReports,
                gradient = listOf(
                    Color(0xFF9CCC65),
                    Color(0xFFDCEDC8)
                )
            )
        }
    }
}

@Composable
fun ModernMenuCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit,
    gradient: List<Color>
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = gradient[0].copy(alpha = 0.3f)
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
                        colors = gradient.map { it.copy(alpha = 0.1f) }
                    )
                )
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(
                        brush = Brush.linearGradient(gradient),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = null,
                tint = gradient[0]
            )
        }
    }
}