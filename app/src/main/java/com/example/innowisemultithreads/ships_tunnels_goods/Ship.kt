package com.example.innowisemultithreads.ships_tunnels_goods

class Ship(
    val type: String,
    val capacity: Int,
) {
    var cargo: Int = 0
    var readyForLoading: Boolean = false
}
