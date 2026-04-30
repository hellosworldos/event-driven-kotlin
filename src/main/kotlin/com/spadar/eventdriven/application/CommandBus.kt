package com.spadar.eventdriven.application

interface CommandBus {
    fun dispatch(vararg commands: Any)
}
