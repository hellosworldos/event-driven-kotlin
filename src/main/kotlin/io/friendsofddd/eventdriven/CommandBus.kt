package io.friendsofddd.eventdriven

interface CommandBus {
    fun dispatch(vararg commands: Any)
}
