package com.comunidadedevspace.taskbeats

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val underTest = MyCountRepositoryIMpl()

    @Test
    fun addition_isCorrect() {
        val result = underTest.sum(2,2)
        assertEquals(4, result)
    }
}