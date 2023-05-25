package com.example.innowisemultithreads

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var timerTextView: TextView
    private var sessionDuration: Long = 0
    private var toastCounter: Int = 0

    private val mainHandler = Handler(Looper.getMainLooper())

    private val timerRunnable = object : Runnable {
        override fun run() {
            ++sessionDuration
            timerTextView.text = formatDuration(sessionDuration)
            mainHandler.postDelayed(this, 1000)
        }
    }

    private val toastRunnable = object : Runnable {
        override fun run() {
            val message = if (toastCounter % 4 == 0 && toastCounter != 0) {
                "Surprise"
            } else {
                formatDuration(sessionDuration)
            }
            showToast(message)
            toastCounter++
            mainHandler.postDelayed(this, 10000)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerTextView = findViewById(R.id.timerTextView)

        mainHandler.post(timerRunnable)
        mainHandler.post(toastRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainHandler.removeCallbacks(timerRunnable)
        mainHandler.removeCallbacks(toastRunnable)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun formatDuration(duration: Long): String {
        val minutes = (duration / 60).toString().padStart(2, '0')
        val seconds = (duration % 60).toString().padStart(2, '0')
        return "$minutes:$seconds"
    }
}
