package com.spadar.eventdriven.application

interface EventBus {
    fun dispatch(vararg events: Any)
}
