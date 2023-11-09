package com.example.organizeme.ui

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.organizeme.R
import com.example.organizeme.databinding.ActivityBmiBinding
import com.example.organizeme.databinding.FragmentProfileBinding
import com.example.organizeme.fragment.ProfileFragment
import com.example.organizeme.helper.SharedViewModel
import com.google.android.material.button.MaterialButton
import kotlin.math.pow


class BmiActivity : AppCompatActivity() {
    private lateinit var binding : ActivityBmiBinding
    private var selectedGender: String? = null
    private lateinit var sharedViewModel: SharedViewModel
    val gender = arrayOf("Male", "Female")
    override fun onCreate(savedInstanceState: Bundle?) {
        var choice = 0

        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = Color.TRANSPARENT
        setContentView(binding.root)

        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)


        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, gender)
        binding.spinnerBmi.adapter = arrayAdapter

        binding.spinnerBmi.setOnItemSelectedListener (object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                selectedGender = p0?.getItemAtPosition(p2).toString()
                choice = p2
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })


        binding.backBmiBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.calcButton.setOnClickListener {
            calculateBMI()

        }


    }


    private fun calculateBMI(): Double {
        val weight = binding.weighEdit.text.toString().toFloatOrNull()
        val height = binding.heightEdit.text.toString().toFloatOrNull()


        if (weight != null && height != null && selectedGender != null) {
            val bmi = weight / (height / 100).pow(2)
            val bmiDouble = bmi.toDouble()
            val bmiResult = String.format("%.2f", bmiDouble)

            sharedViewModel.bmiResult.postValue(bmiResult)

            // Cek nilai
            val currentValue = sharedViewModel.bmiResult.value
            val tag = "value"
            if (currentValue != null) {

                Log.d(tag,"Current BMI Result: $currentValue")
            } else {
                Log.d(tag,"BMI Result is null")
            }



            val category = when {
                bmi < 18.5 -> "Underweight"
                bmi < 24.9 -> "Healthy Weight"
                bmi < 29.9 -> "Overweight"
                else -> "Obesity"
            }
            binding.textResult.text = "BMI : $bmiResult\nCategory: $category"


            return bmiDouble

        } else {
            // buat alert dialog
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage("Please enter valid weight and height.")
                .setPositiveButton("Ok") { dialog, _ ->
                    dialog.dismiss()
                }
                .create()

            alertDialog.show()
        }
        return 0.0
    }

}