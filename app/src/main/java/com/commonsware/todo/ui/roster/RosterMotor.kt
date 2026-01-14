package com.commonsware.todo.ui.roster

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * Represents the state of the roster screen, primarily holding the list of to-do items.
 * @param items The current list of ToDoModel objects to be displayed.
 */
data class RosterViewState(val items: List<ToDoModel> = listOf())
/**
 * The ViewModel for the roster screen.
 *
 * Its main purpose is to provide a stable stream of the to-do list state (`RosterViewState`)
 * to the UI and to handle user actions, such as saving a new to-do item. It survives
 * configuration changes (like screen rotation), ensuring the UI state is not lost.
 *
 * @property repo The ToDoRepository instance, which is the single source of truth for data.
 */
class RosterMotor (private val repo: ToDoRepository) : ViewModel() {
    /**
     * A flow of the roster's UI state. It observes changes from the repository's `items()` flow,
     * maps the raw data into a `RosterViewState`, and exposes it as a `StateFlow`.
     * The UI (e.g., a Fragment or Activity) collects this flow to update what the user sees.
     */
    val states = repo.items()
        .map { RosterViewState(it) }
        .stateIn(viewModelScope, SharingStarted.Eagerly, RosterViewState())
    /**
     * Saves a to-do item by delegating the operation to the repository.
     * This is executed within the `viewModelScope`, ensuring the operation is lifecycle-aware
     * and doesn't block the main thread.
     *
     * @param model The ToDoModel to be saved.
     */

    fun save(model: ToDoModel){
        viewModelScope.launch {
            repo.save(model)
        }
    }
    /**
     * Saves a to-do item by delegating the operation to the repository.
     * This is executed within the `viewModelScope`, ensuring the operation is lifecycle-aware
     * and doesn't block the main thread.
     *
     * @param model The ToDoModel to be saved.
     */
}