package com.spadar.eventdriven

interface CommandBus {
    fun dispatch(vararg commands: Any)
}
