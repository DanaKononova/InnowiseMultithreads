package com.example.innowisemultithreads.circle_race

import java.util.*

abstract class Vehicle(val speed: Int, val puncturePercent: Double) {
    var distanceTraveled: Int = 0
    open val name: String = ""

    fun moveOneUnit(): Boolean {
        val random = Random()
        if (random.nextDouble() * 100 <= puncturePercent) {
            // Колесо прокалывается
            return false
        }
        distanceTraveled += speed
        return true
    }


    abstract fun printInfo()

    fun printStatus() {
        println("$name проехал $distanceTraveled единиц")
    }
}