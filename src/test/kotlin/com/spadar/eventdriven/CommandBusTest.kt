package com.spadar.eventdriven

import kotlin.test.Test
import kotlin.test.assertEquals

class CommandBusTest {
    @Test
    fun `should dispatch commands`() {
        val dispatched = mutableListOf<Any>()
        val commandBus =
            object : CommandBus {
                override fun dispatch(vararg commands: Any) {
                    dispatched.addAll(commands.toList())
                }
            }

        commandBus.dispatch("cmd1", "cmd2")

        assertEquals(listOf<Any>("cmd1", "cmd2"), dispatched)
    }
}
