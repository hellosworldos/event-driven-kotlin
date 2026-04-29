package com.spadar.friendsofddd.eventdriven

interface EventBus {
    fun dispatch(vararg events: Any)
}
