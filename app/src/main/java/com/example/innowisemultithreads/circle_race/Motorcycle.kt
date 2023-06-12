package com.example.innowisemultithreads.circle_race

class Motorcycle(speed: Int, puncturePercent: Double, val hasSidecar: Boolean) :
    Vehicle(if (hasSidecar) speed - 3 else speed, puncturePercent) {
    override val name = "Мотоцикл"

    override fun printInfo() {
        println("$name:")
        println("Скорость: $speed")
        println("Вероятность прокола колеса: $puncturePercent")
        println("Наличие коляски: ${if (hasSidecar) "есть" else "нет"}.")
    }
}