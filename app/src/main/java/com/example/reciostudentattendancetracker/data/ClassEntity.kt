package com.example.reciostudentattendancetracker.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(tableName = "class_table")
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val className: String,
    val subjectName: String
)