package com.commonsware.todo

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * A JUnit rule that replaces the main coroutines dispatcher with a test dispatcher.
 *
 * @param testDispatcher The test dispatcher to use. Defaults to a new StandardTestDispatcher.
 */
class MainDispatcherRule(
    val testDispatcher: TestDispatcher = StandardTestDispatcher()
    //StandardTestDispatcher is paused by default until it is told to run the test
) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        // Replaces Dispatchers.Main with the test dispatcher
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        // Resets Dispatchers.Main to the original dispatcher
        Dispatchers.resetMain()
    }
}


/*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlinx.coroutines.test.StandardTestDispatcher

/**
 * A JUnit rule that sets the main coroutines dispatcher to a test dispatcher
 * for the duration of a test. This is useful for testing code that uses
 * `Dispatchers.Main`.
 *
 * Example usage:
 *
 * ```kotlin
 * @get:Rule
 * val mainDispatcherRule = MainDispatcherRule()
 * ```
 *
 * @param testDispatcher The test dispatcher to use. Defaults to an
 * [kotlinx.coroutines.test.UnconfinedTestDispatcher].
 */
/*
@OptIn(ExperimentalCoroutinesApi::class)

class MainDispatcherRule(
  val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
  override fun starting(description: Description) {
    Dispatchers.setMain(testDispatcher)
  }

  override fun finished(description: Description) {
    Dispatchers.resetMain()
  }
}
*/
// inspired by https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471

class MainDispatcherRule(paused: Boolean) : TestWatcher() {
    val dispatcher = TestCoroutineDispatcher().apply {
        if (paused) pauseDispatcher() }

    val dispatcher02 = StandardTestDispatcher().apply {
        if (paused) pauseDispatcher() }

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }
    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }
}*/