package com.commonsware.todo.repo

import java.time.Instant
import java.util.*

data class ToDoModel (
    val id: String = UUID.randomUUID().toString(),
    val description : String = "",
    val notes: String = "",
    val createdOn: Instant = Instant.now(),
    val isCompleted: Boolean = false
) {
}