package com.example.innowisemultithreads.circle_race

import java.util.*

abstract class Vehicle(val speed: Int, val puncturePercent: Double) {
    var distanceTraveled: Double = 0.0
    open val name: String = ""

    fun moveOneUnit(): Boolean {
        val random = Random()
        if (random.nextDouble() * 100 <= puncturePercent && random.nextInt(10) == 5) {
            // Колесо прокалывается
            return false
        }
        distanceTraveled += speed / 10
        return true
    }


    abstract fun printInfo()

    fun printStatus(): String {
        println("$name проехал $distanceTraveled единиц")
        return "$name проехал $distanceTraveled единиц"
    }

    fun printBroke(): String {
        println("$name проколол колесо")
        return "$name проколол колесо"
    }
}