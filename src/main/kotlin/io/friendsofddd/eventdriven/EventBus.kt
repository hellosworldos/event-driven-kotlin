package io.friendsofddd.eventdriven

interface EventBus {
    fun dispatch(vararg events: Any)
}
