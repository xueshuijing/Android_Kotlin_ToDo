package com.commonsware.todo.ui

import com.commonsware.todo.MainDispatcherRule
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.verify

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class SingleModelMotorTest {
    @get:Rule
    // The MainDispatcherRule now uses StandardTestDispatcher by default and doesn't need the 'paused' parameter.
    val mainDispatcherRule = MainDispatcherRule()

    private val testModel = ToDoModel("this is a test")
    private val repo: ToDoRepository = mock {
        on { find(testModel.id) } doReturn flowOf(testModel)
    }
    private lateinit var underTest: SingleModelMotor

    @Before
    fun setUp() {
        underTest = SingleModelMotor(repo, testModel.id)
    }

    @Test
    // Use `runTest` which provides a coroutine scope and replaces `runBlocking`.
    fun `initial state`() = runTest {
        // The coroutine inside SingleModelMotor's init block is queued.
        // `runCurrent()` executes tasks at the current virtual time.
        // This is the modern way to "advance time".
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        // Now that the initial coroutine has run, we can collect the state.
        // `first()` is a suspend function and can be called directly inside `runTest`.
        val item = underTest.states.first().item
        assertEquals(testModel, item)
    }
    /*@Test
    fun `actions pass through to repo`() {
        val replacement = testModel.copy("whatevs")
        underTest.save(replacement)
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()
        runBlocking { verify(repo).save(replacement) }
        underTest.delete(replacement)
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()
        runBlocking { verify(repo).delete(replacement) }
    }*/
    @Test
// 1. Change the test to use the `runTest` builder
    fun `actions pass through to repo`() = runTest {
        val replacement = testModel.copy(description = "whatevs")

        // Call the function that triggers the coroutine
        underTest.save(replacement)

        // 2. Advance the dispatcher to run the queued coroutine
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()

        // 3. Verify the result directly, no runBlocking needed
        verify(repo).save(replacement)

        // Repeat for the delete action
        underTest.delete(replacement)
        mainDispatcherRule.testDispatcher.scheduler.runCurrent()
        verify(repo).delete(replacement)
    }
}
