package com.example.innowisemultithreads.circle_race

import kotlinx.coroutines.delay
import java.util.*

abstract class Vehicle(val speed: Int, val puncturePercent: Double) {
    var distanceTraveled: Int = 0
    open val name: String = ""

    suspend fun moveOneUnit(): Boolean {
        delay(100)

        val random = Random().nextDouble()
        if (random < puncturePercent) {
            println("$name проколол колесо!")
            return false
        }

        distanceTraveled++
        return true
    }

    abstract fun printInfo()

    fun printStatus() {
        println("$name проехал $distanceTraveled единиц")
    }
}