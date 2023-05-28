package com.example.innowisemultithreads.ships_tunnels_goods

import java.util.*

class ShipGenerator() : Thread() {
    private val shipTypes = listOf("Хлеб", "Банан", "Одежда")
    private val capacities = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
    private val random = Random()

    fun generateShip(): Ship {
        val type = shipTypes[random.nextInt(shipTypes.size)]
        val capacity = capacities[random.nextInt(capacities.size)]
        return Ship(type, capacity)
    }
}