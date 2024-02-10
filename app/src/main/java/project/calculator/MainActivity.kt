package project.calculator

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import project.calculator.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var operation: String = ""
    private var curInput: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        supportActionBar?.setSubtitle("By Hayoung Jung")

        // Textview color change
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.radioBtn1.id -> binding.textView.setBackgroundColor(Color.parseColor("#232121"))
                binding.radioBtn2.id -> binding.textView.setBackgroundColor(Color.parseColor("#108AEC"))
                binding.radioBtn3.id -> binding.textView.setBackgroundColor(Color.parseColor("#F44336"))
            }
        }

        // dropdown menu
        val op = arrayOf("Operations", "+", "-", "*", "/")
        val arrayAdatper = ArrayAdapter(this, android.R.layout.simple_spinner_item, op)
        val spinner = binding.spinner
        spinner.adapter = arrayAdatper


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (position > 0) {
                    operation = op[position]
                    if (curInput.isNotEmpty()) {
                        curInput += " "
                        curInput += operation
                        curInput += " "
                    }
                    binding.textView.append(op[position])

                }
                spinner.setSelection(0)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        // Button click action

        binding.calBtn.setOnClickListener {
            calculate()

        }
        binding.clearBtn.setOnClickListener {
            clearText()
        }

        numberClick()

    } // onCreate

    // Number click
    private fun numberClick() {
        val numButtons = listOf(
            binding.numBtn0, binding.numBtn1, binding.numBtn2, binding.numBtn3, binding.numBtn4,
            binding.numBtn5, binding.numBtn6, binding.numBtn7, binding.numBtn8, binding.numBtn9
        )
        if (operation.isEmpty()) {
            numButtons.forEach { button ->
                button.setOnClickListener { v ->
                    val number = (v as TextView).text.toString()
                    curInput += number
                    binding.textView.text = curInput
                }
            }
        } else {
            numButtons.forEach { button ->
                button.setOnClickListener { v ->
                    val number = (v as TextView).text.toString()
                    curInput += number
                    binding.textView.text = curInput
                }

            }
        }
    }

    // Calculate
    private fun calculate() {
        println(curInput)
        val parts = curInput.split(" ")

        val num1 = parts[0].toDouble()
        val num2 = parts[2].toDouble()
        val operation1 = parts[1]

        val num3 = if (parts.size > 4)
            parts[4].toDouble()
        else null
        val operation2 = if (parts.size > 3)
            parts[3]
        else ""


        var calResult: Double = when(operation1) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> num1 / num2
            else ->
                0.0
        }

        val calResult2: Double = if (operation2.isNotEmpty() && num3 != null) {
            when(operation2) {
                "+" -> calResult + num3
                "-" -> calResult - num3
                "*" -> calResult * num3
                "/" -> calResult / num3
                else -> 0.0
            }
        } else {
            calResult
        }

        curInput = calResult2.toString()
        binding.textView.text = calResult2.toString()

    } // calculate

    // Text clear
    private fun clearText(){
        binding.textView.text = ""
        curInput = ""
        operation = ""
    }
}

