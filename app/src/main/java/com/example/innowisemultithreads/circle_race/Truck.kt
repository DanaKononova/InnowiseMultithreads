package com.example.innowisemultithreads.circle_race

class Truck(speed: Int, puncturePercent: Double, val cargoWeight: Int) :
    Vehicle(speed - cargoWeight / 10, puncturePercent) {
    override val name = "Грузовик"

    override fun printInfo() {
        println("$name:")
        println("Скорость: $speed")
        println("Вероятность прокола колеса: $puncturePercent")
        println("Вес груза: $cargoWeight.")
    }
}