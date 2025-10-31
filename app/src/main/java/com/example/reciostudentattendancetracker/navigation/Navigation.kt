package com.example.reciostudentattendancetracker.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.reciostudentattendancetracker.ui.screens.*
import com.example.reciostudentattendancetracker.viewmodel.AttendanceViewModel

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object ClassList : Screen("class_list")
    object StudentList : Screen("student_list/{classId}") {
        fun createRoute(classId: Int) = "student_list/$classId"
    }
    object Attendance : Screen("attendance")
    object Reports : Screen("reports")
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    viewModel: AttendanceViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToClasses = { navController.navigate(Screen.ClassList.route) },
                onNavigateToAttendance = { navController.navigate(Screen.Attendance.route) },
                onNavigateToReports = { navController.navigate(Screen.Reports.route) }
            )
        }

        composable(Screen.ClassList.route) {
            ClassListScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() },
                onNavigateToStudents = { classId ->
                    navController.navigate(Screen.StudentList.createRoute(classId))
                }
            )
        }

        composable(
            route = Screen.StudentList.route,
            arguments = listOf(navArgument("classId") { type = NavType.IntType })
        ) { backStackEntry ->
            val classId = backStackEntry.arguments?.getInt("classId") ?: 0
            StudentListScreen(
                classId = classId,
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Attendance.route) {
            AttendanceScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Reports.route) {
            ReportsScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}