package com.commonsware.todo.repo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
// 1. Import the necessary new APIs
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.empty
import org.hamcrest.Matchers.equalTo
import org.hamcrest.collection.IsIterableContainingInOrder.contains
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// 2. Add the OptIn annotation at the class level
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ToDoRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val db = ToDoDatabase.newTestInstance(context)

    @Test
    // 3. Change runBlockingTest to runTest
    fun canAddItems() = runTest {
        // The 'this' scope is now a TestScope from runTest
        val underTest = ToDoRepository(db.todoStore(), this)
        val results = mutableListOf<List<ToDoModel>>()

        val itemsJob = launch {
            underTest.items().collect { results.add(it) }
        }

        // 4. Manually advance the virtual clock to run the collection
        advanceUntilIdle()

        assertThat(results.size, equalTo(1))
        assertThat(results[0], empty())

        val testModel = ToDoModel(description = "test model")
        underTest.save(testModel)

        // 5. Advance the clock again after the save operation
        advanceUntilIdle()

        assertThat(results.size, equalTo(2))
        assertThat(results[1], contains(testModel))
        assertThat(underTest.find(testModel.id).first(), equalTo(testModel))

        itemsJob.cancel()
    }

    // ... (your other tests can remain unchanged for now)


    @Test
    fun canModifyItems() = runTest {
        val underTest = ToDoRepository(db.todoStore(), this)
        val testModel = ToDoModel(description ="test model")
        val replacement = testModel.copy(notes = "This is the replacement")
        val results = mutableListOf<List<ToDoModel>>()
        val itemsJob = launch {
            underTest.items().collect { results.add(it) }
        }
        advanceUntilIdle()
        assertThat(results[0], empty())
        underTest.save(testModel)
        advanceUntilIdle()
        assertThat(results[1], contains(testModel))
        underTest.save(replacement)
        advanceUntilIdle()
        assertThat(results[2], contains(replacement))
        itemsJob.cancel()
    }

    @Test
    fun canRemoveItems() = runTest {
        val underTest = ToDoRepository(db.todoStore(), this)
        val testModel = ToDoModel("test model")
        val results = mutableListOf<List<ToDoModel>>()
        val itemsJob = launch {
            underTest.items().collect { results.add(it) }
        }
        advanceUntilIdle()
        assertThat(results[0], empty())
        underTest.save(testModel)
        advanceUntilIdle()
        assertThat(results[1], contains(testModel))
        underTest.delete(testModel)
        advanceUntilIdle()
        assertThat(results[2], empty())
        itemsJob.cancel()
    }

    @Test
    fun canModifyItems2() = runTest {
        val underTest = ToDoRepository(db.todoStore(), this)
        // GIVEN: An initial item is saved
        val testModel = ToDoModel(description = "test model") // Use named arguments
        underTest.save(testModel)

        // WHEN: A replacement with the same ID is saved
        val replacement = testModel.copy(notes = "This is the replacement")
        underTest.save(replacement)

        // THEN: The repository contains only the updated replacement model
        val items = underTest.items().first() // Get the *current* state of the flow
        assertThat(items.size, equalTo(1))
        assertThat(items[0], equalTo(replacement))
    }

    @Test
    fun canRemoveItems2() = runTest {
        val underTest = ToDoRepository(db.todoStore(), this)
        // GIVEN: An item is saved
        val testModel = ToDoModel(description = "test model")
        underTest.save(testModel)
        // And we confirm it was added
        assertThat(underTest.items().first().size, equalTo(1))

        // WHEN: The item is deleted
        underTest.delete(testModel)

        // THEN: The repository is empty again
        assertThat(underTest.items().first(), empty())
    }

}


