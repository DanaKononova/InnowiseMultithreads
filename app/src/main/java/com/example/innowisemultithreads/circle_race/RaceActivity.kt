package com.example.innowisemultithreads.circle_race

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog
import com.example.innowisemultithreads.R
import com.example.innowisemultithreads.databinding.ActivityRaceBinding

class RaceActivity : AppCompatActivity() {

    val vehicles = mutableListOf<Vehicle>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityRaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.nextButton.setOnClickListener {
            if (binding.trucksEditText.text.isNotEmpty() && binding.carsEditText.text.isNotEmpty() && binding.motoEditText.text.isNotEmpty() && binding.circleLengthEditText.text.isNotEmpty()) {
                val trucks = binding.trucksEditText.text.toString().toInt()
                val cars = binding.carsEditText.text.toString().toInt()
                val moto = binding.motoEditText.text.toString().toInt()
                val circleLength = binding.circleLengthEditText.text.toString().toInt()

                for (i in 0 until trucks) {
                    showTruckDialog()
                }
                for (i in 0 until cars) {
                    showCarsDialog()
                }
                for (i in 0 until moto) {
                    showMotoDialog()
                }

                binding.nextButton.text = getString(R.string.start_race)
                binding.nextButton.setOnClickListener {
                    printStartOfTheRace()
                }
            }
        }
    }

    private fun printStartOfTheRace() {
        for (i in 0 until vehicles.size) {
            vehicles[i].printInfo()
        }
    }

    private fun showTruckDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.truck_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Грузовик")
        dialogBuilder.setPositiveButton("OK") { _, _ ->
            val speed =
                dialogView.findViewById<EditText>(R.id.speedEditText).text.toString().toIntOrNull()
            val puncturePercent =
                dialogView.findViewById<EditText>(R.id.puncturePercentEditText).text.toString()
                    .toDoubleOrNull()
            val cargoWeight =
                dialogView.findViewById<EditText>(R.id.cargoWeightEditText).text.toString()
                    .toIntOrNull()
            if (speed != null && puncturePercent != null && cargoWeight != null) {
                vehicles.add(Truck(speed, puncturePercent, cargoWeight))
            }
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun showCarsDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.car_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Легковушка")
        dialogBuilder.setPositiveButton("OK") { _, _ ->
            val speed =
                dialogView.findViewById<EditText>(R.id.speedEditText).text.toString().toIntOrNull()
            val puncturePercent =
                dialogView.findViewById<EditText>(R.id.puncturePercentEditText).text.toString()
                    .toDoubleOrNull()
            val passengerCount =
                dialogView.findViewById<EditText>(R.id.passengerCountEditText).text.toString()
                    .toIntOrNull()
            if (speed != null && puncturePercent != null && passengerCount != null) {
                vehicles.add(Car(speed, puncturePercent, passengerCount))
            }
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun showMotoDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        val dialogView = LayoutInflater.from(this).inflate(R.layout.moto_dialog, null)
        dialogBuilder.setView(dialogView)
        dialogBuilder.setTitle("Мотоцикл")
        dialogBuilder.setPositiveButton("OK") { _, _ ->
            val speed =
                dialogView.findViewById<EditText>(R.id.speedEditText).text.toString().toIntOrNull()
            val puncturePercent =
                dialogView.findViewById<EditText>(R.id.puncturePercentEditText).text.toString()
                    .toDoubleOrNull()

            val selectedRadioButtonId =
                dialogView.findViewById<RadioGroup>(R.id.choiceRadioGroup).checkedRadioButtonId
            if (selectedRadioButtonId != -1) {
                val selectedRadioButton =
                    dialogView.findViewById<RadioButton>(selectedRadioButtonId)
                val choice = selectedRadioButton.text.toString()
                var hasSidecar = false
                if (choice == getString(R.string.yes)) {
                    hasSidecar = true
                }
                if (speed != null && puncturePercent != null) {
                    vehicles.add(Motorcycle(speed, puncturePercent, hasSidecar))
                }
            }
        }
        dialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

}