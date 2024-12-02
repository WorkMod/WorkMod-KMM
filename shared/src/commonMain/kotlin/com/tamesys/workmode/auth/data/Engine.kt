package com.tamesys.workmode.auth.data

class Engine {

    private var engineState = false
    fun start(): Boolean {
        engineState = true
        return engineState
    }

    fun stop(): Boolean {
        engineState = false
        return engineState
    }
}