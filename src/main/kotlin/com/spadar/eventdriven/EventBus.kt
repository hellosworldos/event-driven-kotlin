package com.spadar.eventdriven

interface EventBus {
    fun dispatch(vararg events: Any)
}
