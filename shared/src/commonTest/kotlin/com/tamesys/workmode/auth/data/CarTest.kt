package com.tamesys.workmode.auth.data

import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlin.test.Test

class CarTest {

    val engine = mockk<Engine>()

    @Test
    fun testCar() {
        val car = Car(engine)

        every { engine.start() } returns true

        car.drive()

        verify { engine.start() }

        confirmVerified(engine)
    }
}