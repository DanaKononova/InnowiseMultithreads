package com.example.innowisemultithreads.ships_tunnels_goods

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.innowisemultithreads.R

class MonitorActivity : AppCompatActivity() {
    private lateinit var shipGeneratorThread: Thread
    private lateinit var loadingManagerThread: Thread
    private lateinit var handler: Handler
    private var shipsLabels = mutableListOf<String>()
    private var dock1Labels = mutableListOf<String>()
    private var dock2Labels = mutableListOf<String>()
    private var dock3Labels = mutableListOf<String>()
    private var leavedShips = mutableListOf<String>()

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor)

        val tunnel = Tunnel()
        val docks = mutableListOf(
            Dock("Хлеб"),
            Dock("Банан"),
            Dock("Одежда")
        )
        val shipGenerator = ShipGenerator()

        handler = Handler(Looper.getMainLooper())

        shipGeneratorThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                val ship = shipGenerator.generateShip()

                tunnel.enterTunnel(ship)
                val message =
                    "Корабль ${ship.type} с вместимостью ${ship.capacity}"
                if (shipsLabels.size < 5 ) shipsLabels.add(message + "\n")
                else {
                    shipsLabels.removeFirst()
                    shipsLabels.add(message + "\n")
                }
                displayShipsMessage()

                Thread.sleep(1000)

                ship.readyForLoading = true
            }
        }

        loadingManagerThread = Thread {
            while (!Thread.currentThread().isInterrupted) {
                for (dock in docks) {
                    if (!dock.loadingInProgress && dock.loadingShip == null) {
                        val availableShips = tunnel.getReadyShips()

                        if (availableShips.isNotEmpty()) {
                            val ship = availableShips.find { it.type == dock.type }
                            if (ship != null) {
                                dock.loadingShip = ship
                                dock.loadingInProgress = true

                                val message =
                                    "Корабль ${ship.type} с вместимостью ${ship.capacity} начал загрузку на причале ${dock.type}. Процент: ${dock.loadedCargo}"
                                when (dock.type) {
                                    "Хлеб" -> {
                                        if (dock1Labels.size < 3) dock1Labels.add(message + "\n")
                                        else {
                                            dock1Labels.removeFirst()
                                            dock1Labels.add(message + "\n")
                                        }
                                        displayDock1()
                                    }
                                    "Банан" ->{
                                        if (dock2Labels.size < 3) dock2Labels.add(message + "\n")
                                        else {
                                            dock2Labels.removeFirst()
                                            dock2Labels.add(message + "\n")
                                        }
                                        displayDock2()
                                    }
                                    "Одежда" -> {
                                        if (dock3Labels.size < 3) dock3Labels.add(message + "\n")
                                        else {
                                            dock3Labels.removeFirst()
                                            dock3Labels.add(message + "\n")
                                        }
                                        displayDock3()
                                    }
                                }

                                Thread.sleep(1000)

                                val loadedCargo = minOf(10, ship.capacity - ship.cargo)
                                ship.cargo += loadedCargo
                                dock.loadedCargo += loadedCargo

                                if (ship.cargo == ship.capacity) {
                                    ship.readyForLoading = false
                                    tunnel.exitTunnel(ship)
                                    dock.loadedCargo = 0
                                    when (dock.type) {
                                        "Хлеб" -> {
                                            dock1Labels.removeIf{ it.startsWith("Корабль ${ship.type}") }
                                            displayDock1()
                                        }
                                        "Банан" -> {
                                            dock2Labels.removeIf{ it.startsWith("Корабль ${ship.type}") }
                                            displayDock2()
                                        }
                                        "Одежда" -> {
                                            dock3Labels.removeIf{ it.startsWith("Корабль ${ship.type}") }
                                            displayDock3()
                                        }
                                    }
                                    val exitMessage =
                                        "Корабль ${ship.type} с вместимостью ${ship.capacity}"
                                    displayLeavedShips(exitMessage)
                                    displayShipsMessage()
                                }

                                dock.loadingShip = null
                                dock.loadingInProgress = false
                            }
                        }
                    }
                }

                Thread.sleep(1000)
            }
        }

        shipGeneratorThread.start()
        loadingManagerThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        shipGeneratorThread.interrupt()
        loadingManagerThread.interrupt()
    }

    private fun displayShipsMessage() {
        var text = ""
        for (label in shipsLabels) {
            text += label
        }
        handler.post {
            findViewById<TextView>(R.id.ships).text = text
        }
    }

    private fun displayDock1() {
        var text = ""
        for (label in dock1Labels) {
            text += label
        }
        handler.post {
            findViewById<TextView>(R.id.dock1).text = text
        }
    }

    private fun displayDock2() {
        var text = ""
        for (label in dock2Labels) {
            text += label
        }
        handler.post {
            findViewById<TextView>(R.id.dock2).text = text
        }
    }

    private fun displayDock3() {
        var text = ""
        for (label in dock3Labels) {
            text += label
        }
        handler.post {
            findViewById<TextView>(R.id.dock3).text = text
        }
    }

    private fun displayLeavedShips(message: String) {
        if (leavedShips.size < 4) leavedShips.add(message + "\n")
        else {
            leavedShips.removeFirst()
            leavedShips.add(message + "\n")
        }
        var text = ""
        for (label in leavedShips) {
            text += label
        }
        handler.post {
            findViewById<TextView>(R.id.leavesShips).text = text
        }
    }
}
