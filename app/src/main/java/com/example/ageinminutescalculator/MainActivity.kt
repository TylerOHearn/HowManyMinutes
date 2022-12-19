package com.example.ageinminutescalculator

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var textViewSelectedDate: TextView? = null
    private var textViewAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSelectDate: Button = findViewById(R.id.buttonSelectDate)

        textViewSelectedDate = findViewById(R.id.textViewSelectedDate)
        textViewAgeInMinutes = findViewById(R.id.textViewAgeInMinutes)

        buttonSelectDate.setOnClickListener {
            clickButtonSelectDate()
        }


    }

    private fun clickButtonSelectDate() {

        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialogCode = DatePickerDialog(this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->

                /*Toast.makeText(this,
                    "Year was $selectedYear, Month was ${selectedMonth+1}, day was $selectedDayOfMonth",
                    Toast.LENGTH_LONG).show()*/

                val selectedDate = "${selectedMonth + 1}/$selectedDayOfMonth/$selectedYear"

                textViewSelectedDate?.text = (selectedDate)

                val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)

                val theDate = simpleDateFormat.parse(selectedDate)
                theDate?.let {
                    val selectedDateInMinutes = theDate.time / 60000

                    val currentDate =
                        simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
                    currentDate?.let {
                        val currentDateInMinutes = currentDate.time / 60000

                        val differenceInMinutes = currentDateInMinutes - selectedDateInMinutes

                        textViewAgeInMinutes?.text = differenceInMinutes.toString()

                        Timber.i("User date in minutes: $differenceInMinutes")
                    }

                }

            },
            year,
            month,
            day
        )
        datePickerDialogCode.datePicker.maxDate = System.currentTimeMillis() - 86400000
        datePickerDialogCode.show()

    }

}