package com.example.innowisemultithreads.ships_tunnels_goods

class Dock(
    val type: String
){
    var loadingShip: Ship? = null
    var loadedCargo: Int = 0
    var loadingInProgress: Boolean = false
}
