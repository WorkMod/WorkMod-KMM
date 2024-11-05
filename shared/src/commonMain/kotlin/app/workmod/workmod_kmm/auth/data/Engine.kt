package app.workmod.workmod_kmm.auth.data

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