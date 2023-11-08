package com.example.organizeme.helper

import java.time.LocalDate
import java.time.LocalTime
import java.util.UUID

class TaskItem (
    var name: String,
    var dueTime: LocalTime?,
    var dueDate: LocalDate?,
    var id: UUID = UUID.randomUUID()
)