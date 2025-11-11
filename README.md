# Student Attendance Tracker App

### ITEW3 - Mobile Programming 1
### Midterm Case Study Project
**Course Requirement | Academic Year 2025-2026, 1st Semester**

---

## Table of Contents
- [Project Overview](#project-overview)
- [Course Information](#course-information)
- [Features](#features)
- [Design](#design)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Installation Guide](#installation-guide)
- [User Guide](#user-guide)
- [Database Schema](#database-schema)
- [System Requirements](#system-requirements)
- [Screenshots](#screenshots)
- [Learning Outcomes](#learning-outcomes)
- [Credits](#credits)

---

## Project Overview

A modern, feature-rich Android application designed for teachers to efficiently manage and track student attendance. Built using **Jetpack Compose** and **Room Database**, this app provides a beautiful green-themed interface that makes attendance tracking simple and intuitive.

### Purpose
This project serves as the **Midterm Case Study** for **ITEW3 - Mobile Programming 1**, demonstrating comprehensive knowledge of:
- Modern Android development with Jetpack Compose
- Local database management with Room
- MVVM architecture implementation
- Material Design 3 principles
- CRUD operations and data persistence
- Advanced date filtering and reporting

---

## Course Information

- **Course Code:** ITEW3
- **Course Title:** Mobile Programming 1
- **Project Type:** Midterm Case Study
- **Package Name:** `com.example.reciostudentattendancetracker`
- **Development Environment:** Android Studio
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose
- **Database:** Room (SQLite)
- **Architecture:** MVVM (Model-View-ViewModel)

---

## Features

### Core Functionality ✅

#### 1. Home Screen - 4 Selection Buttons
- ✅ **Class List** - Manage and view all classes
- ✅ **Student List** - View all students across the system
- ✅ **Attendance Marking** - Record daily student attendance
- ✅ **Attendance Summary** - View comprehensive reports and statistics
- ✅ Modern card-based navigation with gradient backgrounds

#### 2. Class Management
- ✅ Add new classes with subject names
- ✅ Edit existing class information
- ✅ Delete classes (with cascade delete)
- ✅ View all classes in a scrollable list
- ✅ Navigate to student list per class

#### 3. Student Management (Updated - View Only)
- ✅ **Add students** to specific classes
- ✅ **Delete students** with confirmation
- ✅ **View-only student list** - No edit functionality (as per requirements)
- ✅ **All Students Screen** - View students across all classes with:
    - Search by name or ID number
    - Filter by class dropdown
    - Student count display
    - Class information for each student
- ✅ Initial avatar badges for visual appeal

#### 4. Attendance Tracking
- ✅ Select class from dropdown
- ✅ Choose date (Previous/Today/Next navigation)
- ✅ Mark students as:
    - **Present** (Green - #43A047)
    - **Absent** (Red - #E53935)
    - **Late** (Amber - #FFB300)
- ✅ Real-time status updates
- ✅ Persistent attendance records
- ✅ Color-coded status chips for easy marking

#### 5. Attendance Summary - Enhanced Reporting ⭐ NEW
- ✅ **Dual View Modes:**
    - **Per Class View** - Select specific class to view student reports
    - **Overall View** - View ALL students across ALL classes
- ✅ **Date Range Filtering:**
    - Custom date range selector (start/end dates)
    - Quick filters: "Last 7 Days" and "Last Month"
    - Clear filter option
    - Active filter indicator
- ✅ **Attendance Analytics:**
    - Attendance percentage with color coding
    - Performance ratings (Excellent/Good/Fair/Needs Improvement)
    - Visual progress bars
    - Detailed statistics (Present/Late/Absent/Total days)
- ✅ **Color-Coded Performance Indicators:**
    - 90%+ = Excellent (Green - #43A047)
    - 75-89% = Good (Mint - #4DB6AC)
    - 60-74% = Fair (Amber - #FFB300)
    - <60% = Needs Improvement (Red - #E53935)

### Technical Features 🛠️

- **CRUD Operations:** Complete Create, Read, Update, Delete functionality
- **Data Persistence:** Room Database with SQLite
- **Real-time Updates:** Kotlin Flow with `combine()` for reactive UI
- **Foreign Key Relationships:** Automatic cascade delete
- **Input Validation:** Required field checks
- **Confirmation Dialogs:** Prevent accidental deletions
- **Date Management:** LocalDate for attendance tracking
- **Navigation:** Type-safe navigation with arguments (6 screens)
- **State Management:** ViewModel with Flow
- **Search & Filter:** Advanced student search and class filtering

---

## Design

### Color Palette 🎨

| Color | Name | Hex Code | Usage |
|-------|------|----------|-------|
| ![#2E7D32](https://www.colorhexa.com/2e7d32.png) | Forest Green | `#2E7D32` | Primary (Headers, Buttons) |
| ![#66BB6A](https://www.colorhexa.com/66bb6a.png) | Leaf Green | `#66BB6A` | Secondary (Student Cards) |
| ![#81C784](https://www.colorhexa.com/81c784.png) | Mint Green | `#81C784` | Tertiary (Reports) |
| ![#43A047](https://www.colorhexa.com/43a047.png) | Success Green | `#43A047` | Present Status (90%+) |
| ![#4DB6AC](https://www.colorhexa.com/4db6ac.png) | Teal Green | `#4DB6AC` | Good Performance (75-89%) |
| ![#E53935](https://www.colorhexa.com/e53935.png) | Error Red | `#E53935` | Absent Status (<60%) |
| ![#FFB300](https://www.colorhexa.com/ffb300.png) | Warning Amber | `#FFB300` | Late Status (60-74%) |
| ![#F1F8F4](https://www.colorhexa.com/f1f8f4.png) | Light Green | `#F1F8F4` | Background |

### Design Principles ✨

- **Material Design 3:** Latest design guidelines
- **Gradient Backgrounds:** Subtle green gradients on all screens
- **Rounded Corners:** 12-24dp radius throughout
- **Card Elevation:** 4-8dp shadows for depth
- **Icon Integration:** Material icons for every action
- **Typography Hierarchy:** Bold titles, medium body text
- **Color Coding:** Visual status indicators
- **Empty States:** Helpful messages with CTAs
- **Responsive Layout:** Adapts to different screen sizes
- **Modern UI:** Extended FABs, filter chips, progress indicators

---

## Technology Stack

### Development Tools
- **IDE:** Android Studio Narwhal (2025.1.1) or later
- **Language:** Kotlin 2.0.21
- **Min SDK:** 24 (Android 7.0)
- **Target SDK:** 36
- **Compile SDK:** 36

### Libraries & Dependencies

```gradle
// Jetpack Compose
implementation "androidx.activity:activity-compose:1.11.0"
implementation "androidx.compose.ui:ui"
implementation "androidx.compose.material3:material3"

// Room Database
implementation "androidx.room:room-runtime:2.6.1"
implementation "androidx.room:room-ktx:2.6.1"
ksp "androidx.room:room-compiler:2.6.1"

// Navigation
implementation "androidx.navigation:navigation-compose:2.8.4"

// ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-compose:2.9.4"

// Coroutines
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core"
implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android"
```

### Architecture Pattern
- **MVVM (Model-View-ViewModel):** Clean separation of concerns
- **Repository Pattern:** Data layer abstraction
- **Single Source of Truth:** Room Database
- **Reactive Programming:** Kotlin Flow with `combine()` operator

---

## Project Structure

```
app/src/main/java/com/example/reciostudentattendancetracker/
│
├── 📁 data/                           # Data Layer
│   ├── ClassEntity.kt                # Class table entity
│   ├── StudentEntity.kt              # Student table entity
│   ├── AttendanceEntity.kt           # Attendance table entity
│   ├── ClassDao.kt                   # Class data access object
│   ├── StudentDao.kt                 # Student data access object
│   ├── AttendanceDao.kt              # Attendance data access object
│   ├── AppDatabase.kt                # Room database configuration
│   └── AttendanceRepository.kt       # Repository implementation
│
├── 📁 viewmodel/                      # ViewModel Layer
│   └── AttendanceViewModel.kt        # UI state management with date filtering
│
├── 📁 ui/                            # UI Layer
│   ├── 📁 screens/
│   │   ├── MainScreen.kt             # Landing page (4 buttons)
│   │   ├── ClassListScreen.kt        # Class management
│   │   ├── StudentListScreen.kt      # Student management (per class)
│   │   ├── AllStudentsScreen.kt      # View all students (NEW)
│   │   ├── AttendanceScreen.kt       # Attendance marking
│   │   └── ReportsScreen.kt          # Enhanced statistics & reports (NEW)
│   │
│   └── 📁 theme/
│       ├── Color.kt                  # Green color palette
│       ├── Theme.kt                  # Material 3 theme
│       └── Type.kt                   # Typography definitions
│
├── 📁 navigation/
│   └── Navigation.kt                 # Navigation graph (6 routes)
│
└── MainActivity.kt                   # Application entry point
```

---

## Installation Guide

### Prerequisites
1. Android Studio Narwhal (2025.1.1) or later
2. JDK 11 or higher
3. Android SDK with API 24+
4. Minimum 4GB RAM recommended
5. Stable internet connection for gradle sync

### Step-by-Step Installation

#### 1️⃣ Clone or Download Project
```bash
# If using Git
git clone <repository-url>

# Or download and extract ZIP file
```

#### 2️⃣ Open in Android Studio
1. Launch Android Studio
2. Click "Open"
3. Navigate to project folder
4. Click "OK"

#### 3️⃣ Update Configuration Files

**📄 libs.versions.toml**
```toml
[versions]
agp = "8.13.0"
kotlin = "2.0.21"
coreKtx = "1.17.0"
lifecycleRuntimeKtx = "2.9.4"
activityCompose = "1.11.0"
composeBom = "2024.09.00"
room = "2.6.1"
navigationCompose = "2.8.4"
lifecycleViewmodelCompose = "2.9.4"
ksp = "2.0.21-1.0.28"

[libraries]
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }
androidx-lifecycle-viewmodel-compose = { group = "androidx.lifecycle", name = "lifecycle-viewmodel-compose", version.ref = "lifecycleViewmodelCompose" }

[plugins]
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
```

**📄 build.gradle.kts (app module)**
```kotlin
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

dependencies {
    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    
    // Navigation
    implementation(libs.androidx.navigation.compose)
    
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)
}
```

#### 4️⃣ Sync Gradle
1. Click "Sync Now" notification
2. Wait for sync to complete
3. Resolve any dependency issues

#### 5️⃣ Add All Project Files
Copy all provided files to their respective packages:
- Data layer files → `data/`
- ViewModel files → `viewmodel/`
- UI files → `ui/screens/` and `ui/theme/`
- Navigation files → `navigation/`

#### 6️⃣ Build and Run
1. Connect Android device or start emulator
2. Click "Run" (▶️) button
3. Select target device
4. Wait for installation

---

## User Guide

### Getting Started 🚀

#### First Time Setup

1. **Launch the App**
    - You'll see the modern welcome screen with 4 options

2. **Add Your First Class**
    - Tap **"Class List"**
    - Tap the green "+ Add Class" button
    - Enter:
        - Class Name (e.g., "BSIT 2A")
        - Subject Name (e.g., "Mobile Programming 1")
    - Tap "Save"

3. **Add Students to Class**
    - Tap on the class card you just created
    - Tap the "+ Add Student" button
    - Enter:
        - Student Name (e.g., "Juan Dela Cruz")
        - Student ID Number (e.g., "2021-12345")
    - Tap "Save"
    - Repeat for all students

### Daily Usage 📅

#### Viewing All Students
1. From Main Menu → Tap **"Student List"**
2. View all students across all classes
3. Use **Search bar** to find by name or ID
4. Use **Filter by Class** dropdown to narrow results
5. See student count and class information

#### Marking Attendance
1. From Main Menu → Tap **"Attendance Marking"**
2. Select class from dropdown
3. Select date (defaults to today)
4. For each student, tap:
    - **Green chip** = Present
    - **Red chip** = Absent
    - **Amber chip** = Late
5. Changes are saved automatically

#### Viewing Reports with Filters
1. From Main Menu → Tap **"Attendance Summary"**
2. Choose **View Mode:**
    - **Per Class** - Select specific class
    - **Overall** - View all students across all classes
3. Optional: Apply **Date Filter**
    - Tap calendar icon in top bar
    - Select start and end dates
    - Or use quick filters (Last 7 Days, Last Month)
    - Tap "Apply Filter"
4. View detailed statistics:
    - Attendance percentage
    - Performance rating
    - Present/Absent/Late counts
    - Visual progress bars

### Managing Data 🗂️

#### Edit Class
- Tap the **Edit icon** (✏️) on class card
- Modify class name or subject
- Tap "Save"

#### Delete Class
- Tap the **Delete icon** (🗑️) on class card
- Confirm deletion in dialog
- All students and attendance records are removed

#### Add Student
- Navigate to specific class
- Tap **"+ Add Student"** FAB
- Enter student information
- Tap "Save"

#### Delete Student (No Edit Option)
- Tap the **Delete icon** (🗑️) on student card
- Confirm deletion in dialog
- All attendance records are removed

---

## Database Schema

### Entity Relationship Diagram

```
ClassTable (1) ─────< (N) StudentTable
                           │
                           │ (1)
                           │
                           ↓
                      (N) AttendanceTable
```

### Table Structures

#### 📊 ClassTable
| Column | Type | Constraints |
|--------|------|-------------|
| id | Integer | PRIMARY KEY, AUTOINCREMENT |
| className | String | NOT NULL |
| subjectName | String | NOT NULL |

#### 📊 StudentTable
| Column | Type | Constraints |
|--------|------|-------------|
| id | Integer | PRIMARY KEY, AUTOINCREMENT |
| studentName | String | NOT NULL |
| studentIdNumber | String | NOT NULL |
| classId | Integer | FOREIGN KEY → ClassTable.id, ON DELETE CASCADE |

#### 📊 AttendanceTable
| Column | Type | Constraints |
|--------|------|-------------|
| id | Integer | PRIMARY KEY, AUTOINCREMENT |
| date | String | NOT NULL (Format: yyyy-MM-dd) |
| studentId | Integer | FOREIGN KEY → StudentTable.id, ON DELETE CASCADE |
| status | String | NOT NULL ("Present", "Absent", "Late") |

### Relationships
- **One-to-Many:** One Class has many Students
- **One-to-Many:** One Student has many Attendance records
- **Cascade Delete:** Deleting a Class removes all Students and Attendance
- **Cascade Delete:** Deleting a Student removes all Attendance records

---

## System Requirements

### Updated Features Implementation

#### 1. Home Screen Layout ✅
- **4 Selection Buttons** implemented:
    1. Class List
    2. Student List (All Students)
    3. Attendance Marking
    4. Attendance Summary

#### 2. Student Functions ✅
- **Edit functionality removed** as per requirements
- **View-only access** in All Students screen
- **Add and Delete** operations still available per class

#### 3. Attendance Summary Section ✅
- **Overall attendance summary** across all students
- **Per-class statistics** view
- **Date range filtering:**
    - Custom date range selection
    - Quick filters (Last 7 Days, Last Month)
    - Clear filter option
- **Enhanced UI/UX:**
    - Color-coded performance indicators
    - Visual progress bars
    - Detailed statistics cards

---

## Screenshots

### Main Screen (Updated)
> Modern landing page with 4 navigation buttons

**Features:**
- Welcome card with school icon
- Class List button
- Student List button
- Attendance Marking button
- Attendance Summary button

### All Students Screen (NEW)
> View all students across all classes

**Features:**
- Search bar for name/ID
- Filter by class dropdown
- Student count display
- Class information per student
- View-only cards (no edit)

### Attendance Summary (Enhanced)
> Advanced reporting with dual views and date filtering

**Features:**
- Per Class / Overall toggle
- Date range filter button
- Active filter indicator
- Class selector (for per-class view)
- Student statistics cards
- Color-coded performance
- Progress bars
- Detailed statistics grid

---

## Learning Outcomes

### Course Requirements Met ✅

#### 1. Mobile Layout Design
✅ 4-button home screen with modern card design
✅ Consistent green theme across all screens
✅ Material Design 3 implementation
✅ Responsive layouts with gradient backgrounds

#### 2. CRUD Operations
✅ Create: Classes, Students, Attendance
✅ Read: All data with search and filter
✅ Update: Classes only (Students view-only)
✅ Delete: Classes and Students with cascade

#### 3. Local Storage (Room)
✅ 3 Entity tables with relationships
✅ DAOs with Flow and suspend functions
✅ Repository pattern implementation
✅ Foreign key constraints with cascade delete

#### 4. List Management
✅ LazyColumn for all list screens
✅ Flow with `combine()` for reactive updates
✅ Empty states with helpful messages
✅ Item composables with actions

#### 5. User Input & Validation
✅ Input dialogs for add operations
✅ Field validation (required fields)
✅ Confirmation dialogs for delete
✅ Date picker with quick filters

#### 6. Date-Based Filtering ⭐
✅ Custom date range selector
✅ Quick filters (7 days, 1 month)
✅ Filter attendance records by date
✅ Active filter indicators

#### 7. Modular Coding
✅ MVVM architecture
✅ Repository pattern
✅ Reusable composables
✅ Separation of concerns
✅ Clean code principles

---

## Credits

### Development Team
**RRRSS Group**
- All group members contributed to this project

### Course Information
- **Course:** ITEW3 - Mobile Programming 1
- **Instructor:** Asst. Prof. Joseph D. Cartagenas
- **Institution:** University of Cabuyao
- **Academic Year:** 2025-2026
- **Semester:** 1st Semester
- **Project Type:** Midterm Case Study

### Technologies Used
- **Android Studio:** Official IDE for Android development
- **Kotlin:** Primary programming language
- **Jetpack Compose:** Modern UI toolkit by Google
- **Room:** Persistence library by Google
- **Material Design 3:** Design system by Google
- **Coroutines & Flow:** Asynchronous programming

### Resources & References
- [Android Developers Documentation](https://developer.android.com)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [Material Design 3](https://m3.material.io)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)
- [Kotlin Flow Documentation](https://kotlinlang.org/docs/flow.html)

---

## License

This project is created for **educational purposes** as part of the ITEW3 - Mobile Programming 1 course requirements.

© 2025 RRRSS Group. All Rights Reserved.

**Academic Use Only**

---

## Change Log

### Version 1.1 (Latest)
- ✅ Implemented 4-button home screen layout
- ✅ Removed student edit functionality (view-only)
- ✅ Added All Students screen with search and filter
- ✅ Enhanced Attendance Summary with dual views
- ✅ Implemented date range filtering
- ✅ Fixed Flow collection with `combine()` operator
- ✅ Added color-coded performance indicators
- ✅ Improved UI/UX with modern design patterns

### Version 1.0 (Initial)
- ✅ Basic CRUD operations
- ✅ Class and student management
- ✅ Attendance marking
- ✅ Basic reports

---

**Made by RRRSS Group for ITEW3 Midterm Case Study**

**Package:** `com.example.reciostudentattendancetracker`

---

*This README was last updated on November 11, 2025*