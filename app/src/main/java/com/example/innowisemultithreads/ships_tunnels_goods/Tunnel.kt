package com.example.innowisemultithreads.ships_tunnels_goods

import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

class Tunnel {
    private val ships: MutableList<Ship> = mutableListOf()
    private val lock = ReentrantLock()
    private val condition: Condition = lock.newCondition()

    fun enterTunnel(ship: Ship) {
        lock.lock()
        try {
            while (ships.size >= 5) {
                condition.await()
            }
            ships.add(ship)
        } finally {
            lock.unlock()
        }
    }

    fun exitTunnel(ship: Ship) {
        lock.lock()
        try {
            ships.remove(ship)
            condition.signalAll()
        } finally {
            lock.unlock()
        }
    }

    fun getReadyShips(): List<Ship> {
        lock.lock()
        try {
            return ships.filter { it.readyForLoading }
        } finally {
            lock.unlock()
        }
    }
}

