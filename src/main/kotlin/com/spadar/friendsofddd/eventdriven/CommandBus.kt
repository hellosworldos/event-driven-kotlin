package com.spadar.friendsofddd.eventdriven

interface CommandBus {
    fun dispatch(vararg commands: Any)
}
