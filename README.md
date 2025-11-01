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

---

## Features

### Core Functionality ✅

#### 1. Class Management
- ✅ Add new classes with subject names
- ✅ Edit existing class information
- ✅ Delete classes (with cascade delete)
- ✅ View all classes in a scrollable list
- ✅ Navigate to student list per class

#### 2. Student Management
- ✅ Add students to specific classes
- ✅ Edit student information (Name, ID Number)
- ✅ Delete students with confirmation
- ✅ View student count per class
- ✅ Initial avatar badges for visual appeal

#### 3. Attendance Tracking
- ✅ Select class from dropdown
- ✅ Choose date (Previous/Today/Next navigation)
- ✅ Mark students as:
  - **Present** (Green)
  - **Absent** (Red)
  - **Late** (Amber)
- ✅ Real-time status updates
- ✅ Persistent attendance records

#### 4. Reports & Analytics
- ✅ Attendance percentage calculation
- ✅ Performance ratings (Excellent/Good/Fair/Needs Improvement)
- ✅ Visual progress bars
- ✅ Detailed statistics:
  - Total Present days
  - Total Absent days
  - Total Late days
  - Total recorded days
- ✅ Color-coded performance indicators

### Technical Features 🛠️

- **CRUD Operations:** Complete Create, Read, Update, Delete functionality
- **Data Persistence:** Room Database with SQLite
- **Real-time Updates:** Kotlin Flow for reactive UI
- **Foreign Key Relationships:** Automatic cascade delete
- **Input Validation:** Required field checks
- **Confirmation Dialogs:** Prevent accidental deletions
- **Date Management:** LocalDate for attendance tracking
- **Navigation:** Type-safe navigation with arguments
- **State Management:** ViewModel with LiveData

---

## Design

### Color Palette 🎨

| Name | Hex Code | Usage |
|------|----------|-------|
| Forest Green | `#2E7D32` | Primary (Headers, Buttons) |
| Leaf Green | `#66BB6A` | Secondary (Student Cards) |
| Mint Green | `#81C784` | Tertiary (Reports) |
| Success Green | `#43A047` | Present Status |
| Error Red | `#E53935` | Absent Status |
| Warning Amber | `#FFB300` | Late Status |
| Light Green | `#F1F8F4` | Background |

### Design Principles ✨

- **Material Design 3:** Latest design guidelines
- **Gradient Backgrounds:** Subtle green gradients
- **Rounded Corners:** 12-20dp radius throughout
- **Card Elevation:** 4-8dp shadows for depth
- **Icon Integration:** Material icons for every action
- **Typography Hierarchy:** Bold titles, medium body text
- **Color Coding:** Visual status indicators
- **Empty States:** Helpful messages when no data
- **Responsive Layout:** Adapts to different screen sizes

---

## Technology Stack

### Development Tools
- **IDE:** Android Studio Hedgehog (2023.1.1) or later
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
```

### Architecture Pattern
- **MVVM (Model-View-ViewModel):** Clean separation of concerns
- **Repository Pattern:** Data layer abstraction
- **Single Source of Truth:** Room Database

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
│   └── AttendanceViewModel.kt        # UI state management
│
├── 📁 ui/                            # UI Layer
│   ├── 📁 screens/
│   │   ├── MainScreen.kt             # Landing page
│   │   ├── ClassListScreen.kt        # Class management
│   │   ├── StudentListScreen.kt      # Student management
│   │   ├── AttendanceScreen.kt       # Attendance marking
│   │   └── ReportsScreen.kt          # Statistics & reports
│   │
│   └── 📁 theme/
│       ├── Color.kt                  # Green color palette
│       ├── Theme.kt                  # Material 3 theme
│       └── Type.kt                   # Typography definitions
│
├── 📁 navigation/
│   └── Navigation.kt                 # Navigation graph
│
└── MainActivity.kt                   # Application entry point
```

---

## Installation Guide

### Prerequisites
1. Android Studio Hedgehog (2023.1.1) or later
2. JDK 11 or higher
3. Android SDK with API 24+
4. Minimum 4GB RAM recommended

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
room = "2.6.1"
navigationCompose = "2.8.4"
ksp = "2.0.21-1.0.28"

[libraries]
androidx-room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
androidx-room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }
androidx-room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
androidx-navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigationCompose" }

[plugins]
ksp = { id = "com.google.devtools.ksp", version.ref = "ksp" }
```

**📄 build.gradle.kts (app module)**
```kotlin
plugins {
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
   - You'll see the modern welcome screen

2. **Add Your First Class**
   - Tap "Manage Classes & Students"
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

#### Marking Attendance

1. From Main Menu → Tap "Mark Attendance"
2. Select class from dropdown
3. Select date (defaults to today)
4. For each student, tap:
   - **Green chip** = Present
   - **Red chip** = Absent
   - **Amber chip** = Late
5. Changes are saved automatically

#### Viewing Reports

1. From Main Menu → Tap "View Reports"
2. Select class from dropdown
3. View statistics for each student:
   - Attendance percentage
   - Performance rating
   - Present/Absent/Late counts
   - Visual progress bars

### Managing Data 🗂️

#### Edit Class/Student
- Tap the **Edit icon** (✏️) on any card
- Modify information
- Tap "Save"

#### Delete Class/Student
- Tap the **Delete icon** (🗑️) on any card
- Confirm deletion in dialog
- Data is permanently removed

---

## Database Schema

### Entity Relationship Diagram

```
ClassTable (1) ─────< (N) StudentTable
                           │
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

**Example Data:**
```
id | className | subjectName
---|-----------|------------------
1  | BSIT 2A   | Mobile Programming 1
2  | BSBA 1B   | Introduction to Business
```

#### 📊 StudentTable
| Column | Type | Constraints |
|--------|------|-------------|
| id | Integer | PRIMARY KEY, AUTOINCREMENT |
| studentName | String | NOT NULL |
| studentIdNumber | String | NOT NULL |
| classId | Integer | FOREIGN KEY → ClassTable.id, ON DELETE CASCADE |

**Example Data:**
```
id | studentName      | studentIdNumber | classId
---|-----------------|-----------------|--------
1  | Juan Dela Cruz  | 2021-12345      | 1
2  | Maria Santos    | 2021-12346      | 1
```

#### 📊 AttendanceTable
| Column | Type | Constraints |
|--------|------|-------------|
| id | Integer | PRIMARY KEY, AUTOINCREMENT |
| date | String | NOT NULL (Format: yyyy-MM-dd) |
| studentId | Integer | FOREIGN KEY → StudentTable.id, ON DELETE CASCADE |
| status | String | NOT NULL ("Present", "Absent", "Late") |

**Example Data:**
```
id | date       | studentId | status
---|------------|-----------|--------
1  | 2025-11-01 | 1         | Present
2  | 2025-11-01 | 2         | Late
```

### Relationships
- **One-to-Many:** One Class has many Students
- **One-to-Many:** One Student has many Attendance records
- **Cascade Delete:** Deleting a Class removes all Students and Attendance
- **Cascade Delete:** Deleting a Student removes all Attendance records

---

## Screenshots

### Main Screen
> Modern landing page with gradient background and three main navigation cards

**Features:**
- Welcome card with school icon
- Manage Classes & Students card (Green)
- Mark Attendance card (Light Green)
- View Reports card (Mint)

### Class List Screen
> Display all classes with add/edit/delete functionality

**Features:**
- Extended FAB with "+ Add Class"
- Class cards with gradient icons
- Edit and Delete buttons
- "View Students" navigation

### Student List Screen
> Show students per class with management options

**Features:**
- Student count card at top
- Avatar badges with initials
- Student name and ID display
- Extended FAB with "+ Add Student"
- Edit and Delete actions

### Attendance Screen
> Mark daily attendance for selected class

**Features:**
- Class dropdown selector
- Date picker with navigation
- Student list with status chips
- Color-coded selection (Green/Red/Amber)
- Real-time status updates

### Reports Screen
> View attendance statistics and performance

**Features:**
- Class selector dropdown
- Individual student report cards
- Attendance percentage display
- Performance ratings
- Visual progress bars
- Statistics grid (Present/Late/Absent/Total)

---

## Learning Outcomes

### Course Requirements Met ✅

This project successfully demonstrates all required competencies for the ITEW3 Midterm Case Study:

#### 1. Mobile Layout Design
- ✅ Designed modern layouts using Jetpack Compose
- ✅ Implemented Material Design 3 principles
- ✅ Created responsive UI components
- ✅ Applied consistent design language
- ✅ Used proper spacing and typography

#### 2. CRUD Operations
- ✅ **Create:** Add classes, students, and attendance records
- ✅ **Read:** Display all data in lists and reports
- ✅ **Update:** Edit classes and students
- ✅ **Delete:** Remove data with cascade delete

#### 3. Local Storage (Room)
- ✅ Implemented Room Database
- ✅ Created Entity classes with relationships
- ✅ Defined DAO interfaces with queries
- ✅ Used Repository pattern
- ✅ Applied foreign key constraints

#### 4. List Management
- ✅ Used LazyColumn for scrollable lists
- ✅ Implemented item composables
- ✅ Added empty state handling
- ✅ Created custom list items with actions

#### 5. User Input & Validation
- ✅ Created input dialogs with text fields
- ✅ Implemented field validation
- ✅ Disabled save buttons for invalid input
- ✅ Added confirmation dialogs
- ✅ Provided user feedback

#### 6. Date-Based Filtering
- ✅ Integrated LocalDate for date management
- ✅ Created date picker dialog
- ✅ Filtered attendance by selected date
- ✅ Implemented date navigation (Previous/Today/Next)

#### 7. Modular Coding
- ✅ Separated Data, ViewModel, and UI layers
- ✅ Created reusable composable functions
- ✅ Implemented Repository pattern
- ✅ Used MVVM architecture
- ✅ Applied Single Responsibility Principle

### Technical Skills Demonstrated 

- **Kotlin Programming:** Advanced language features
- **Jetpack Compose:** Modern declarative UI
- **Room Database:** SQL and ORM concepts
- **Coroutines & Flow:** Asynchronous programming
- **Navigation Component:** Multi-screen apps
- **State Management:** ViewModel and LiveData
- **Material Design:** UI/UX best practices
- **Clean Architecture:** Separation of concerns
- **Git Version Control:** Code management (if applicable)

---

## Credits

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

### Resources & References
- [Android Developers Documentation](https://developer.android.com)
- [Jetpack Compose Documentation](https://developer.android.com/jetpack/compose)
- [Room Persistence Library](https://developer.android.com/training/data-storage/room)
- [Material Design 3](https://m3.material.io)
- [Kotlin Documentation](https://kotlinlang.org/docs/home.html)

---

## License

This project is created for **educational purposes** as part of the ITEW3 - Mobile Programming 1 course requirements.

© 2025 RRRSS Group. All Rights Reserved.

**Academic Use Only**

---

**Made with RRRSS GROUP for ITEW3 Midterm Case Study**

**Package:** `com.example.reciostudentattendancetracker`

---

*This README was last updated on November 1, 2025*
