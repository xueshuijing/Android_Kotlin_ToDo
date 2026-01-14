package com.commonsware.todo

import android.app.Application
import com.commonsware.todo.repo.ToDoDatabase
import com.commonsware.todo.repo.ToDoRepository
import com.commonsware.todo.ui.SingleModelMotor
import com.commonsware.todo.ui.roster.RosterMotor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

//sub application for dependency inversion, i.e. using different databases for different purposes
class ToDoApp: Application() {
    private val koinModule =
        module {
            // telling Koin, "Here are the instructions for building the parts of my app:"
            //single { ... }: defines a singleton. It's an instruction to create the object only once
            // and reuse that same instance every time it's needed.
            // This is perfect for your ToDoDatabase and ToDoRepository,
            // as you typically only want one database connection and one data repository for the entire app.
        single { ToDoDatabase.newInstance(androidContext()) }
        single (named("appScope")){ CoroutineScope(SupervisorJob()) }
        single { ToDoRepository(
            get<ToDoDatabase>().todoStore(),
            get(named("appScope"))
        ) }
            //viewModel { ... }: tells Koin how to build a ViewModel.
            // Koin intelligently ties the ViewModel's lifecycle to your Fragments/Activities,
            // so it survives configuration changes (like screen rotation) correctly.
        viewModel{ RosterMotor(get()) }
            // Inside a recipe, get() tells Koin,
            // "Now go find the recipe for this other component I need and give it to me."
            // For example, when creating the ToDoRepository, Koin sees get<ToDoDatabase>() and
            // knows to find the single block for ToDoDatabase,
            // create it (if it hasn't already), and provide it.
        viewModel { (modelId: String) -> SingleModelMotor (get(), modelId) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ToDoApp)
            modules(koinModule)
        }
    }
}