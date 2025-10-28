package com.example.reciostudentattendancetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "attendance_table",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["id"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("studentId"), Index("date")]
)
data class AttendanceEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val date: String,
    val studentId: Int,
    val status: String
)