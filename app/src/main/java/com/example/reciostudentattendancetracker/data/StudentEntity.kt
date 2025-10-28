package com.example.reciostudentattendancetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "student_table",
    foreignKeys = [
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["id"],
            childColumns = ["classId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("classId")]
)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val studentName: String,
    val studentIdNumber: String,
    val classId: Int
)