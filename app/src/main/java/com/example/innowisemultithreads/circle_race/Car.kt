package com.example.innowisemultithreads.circle_race

class Car(speed: Int, puncturePercent: Double, val passengerCount: Int) :
    Vehicle(speed, puncturePercent) {
    override val name = "Легковушка"

    override fun printInfo() {
        println("$name:")
        println("Скорость: $speed")
        println("Вероятность прокола колеса: $puncturePercent")
        println("Количество пассажиров: $passengerCount.")
    }
}