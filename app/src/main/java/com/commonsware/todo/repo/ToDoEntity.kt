package com.commonsware.todo.repo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.Instant
import java.util.UUID


//model of a table and its methods
@Entity(tableName = "todos", indices = [Index(value = ["id"])])
data class ToDoEntity (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val description : String,
    val notes: String = "",
    val createdOn: Instant = Instant.now(),
    val isCompleted: Boolean = false
        ){

    constructor(model: ToDoModel): this(
        id = model.id,
        description = model.description,
        notes = model.notes,
        createdOn = model.createdOn,
        isCompleted = model.isCompleted
    )

    fun toModel(): ToDoModel {
        return ToDoModel(
            id = id,
            description = description,
            notes = notes,
            createdOn = createdOn,
            isCompleted = isCompleted

        )
    }

    @Dao
    interface Store {
        @Query("SELECT * FROM todos ORDER BY description")
        fun all(): Flow<List<ToDoEntity>>

        //finding a record
        @Query("SELECT * FROM todos WHERE id = :modelId")
        fun find(modelId: String?): Flow<ToDoEntity?>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun save(vararg entities: ToDoEntity)

        @Delete
        suspend fun delete(vararg entities: ToDoEntity)
    }


}