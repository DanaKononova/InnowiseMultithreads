package com.example.innowisemultithreads

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*

class ChronometerActivity : AppCompatActivity() {
    private var sessionDuration: Long = 0
    private var toastCounter: Int = 0

    private lateinit var timerJob: Job
    private lateinit var toastJob: Job
    private lateinit var surpriseJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timerTextView = findViewById<TextView>(R.id.timerTextView)
        var message = formatDuration(sessionDuration)

        timerJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(1000)
                sessionDuration++
                message = formatDuration(sessionDuration)
                timerTextView.text = formatDuration(sessionDuration)
            }
        }

        toastJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(10000)
                showToast(message)
                toastCounter++
            }
        }

        surpriseJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) {
                delay(40000)
                message = "Surprise"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timerJob.cancel()
        toastJob.cancel()
        surpriseJob.cancel()
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
