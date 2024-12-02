package com.tamesys.workmode.auth.data

class Car(val engine: Engine) {

    fun drive() {
        println("driving")
        val engineState = engine.start()
        println("engineState: $engineState")
    }
}