package com.commonsware.todo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
/**
 * Represents the state for a screen that shows a single to-do item.
 * It's a simple data-holding class.
 *
 * @param item The ToDoModel to be displayed. It is nullable because the item
 * might not exist in the database or could still be loading.
 */
data class SingleModelViewState(
    val item: ToDoModel? = null
)
/**
 * The ViewModel for the detail/editor screen.
 *
 * Its main purpose is to load a specific ToDoModel by its ID and provide it
 * to the UI. It also handles user actions for that specific item, such as
 * saving edits or deleting it.
 *
 * @param repo The ToDoRepository, used to fetch, save, or delete the item.
 * @param modelId The unique ID of the ToDoModel to be loaded and displayed.
 */
class SingleModelMotor (
    private val repo: ToDoRepository,
    private val modelId: String?
        ): ViewModel() {
    /**
     * A flow of the detail screen's UI state. It calls the repository to find
     * the specific item by its ID, maps the result into a [SingleModelViewState],
     * and exposes it as a StateFlow for the UI to collect.
     */
    val states = repo.find(modelId)
        .map { SingleModelViewState(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly,
            SingleModelViewState())

    /**
     * Saves the provided ToDoModel by delegating the call to the repository.
     * This is used for both creating a new item and updating an existing one.
     *
     * @param model The ToDoModel with the latest data to be saved.
     */
    fun save(model: ToDoModel){
        viewModelScope.launch {
            repo.save(model)
        }
    }

    /**
     * Deletes the provided ToDoModel by delegating the call to the repository.
     *
     * @param model The ToDoModel to be deleted.
     */
    fun delete(model: ToDoModel){
        viewModelScope.launch {
            repo.delete(model)
        }
    }
}