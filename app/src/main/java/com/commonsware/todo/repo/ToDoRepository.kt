package com.commonsware.todo.repo

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ToDoRepository(
    private val store: ToDoEntity.Store,
    private val appScope: CoroutineScope
){

    fun items(): Flow<List<ToDoModel>> =
        store.all().map { all -> all.map { it.toModel() } }

    fun find(id: String?): Flow<ToDoModel?> = store.find(id).map {
        it?.toModel()
    }

    suspend fun save(model: ToDoModel){
        withContext(appScope.coroutineContext){
            store.save(ToDoEntity(model))
        }
    }

    suspend fun delete(model: ToDoModel) {
        withContext(appScope.coroutineContext) {
            store.delete(ToDoEntity(model))
        }
    }


}


/*
listOf(
        ToDoModel(
            description = "Create an application",
            notes = "See https://wares.commonsware.com"
        ),
        ToDoModel(
            description = "Find a job"
        ),
        ToDoModel(
            description = "Plant a watermelon plant",
            isCompleted = true,
            notes = "Don't forget to water them!"
        )
    )
 */