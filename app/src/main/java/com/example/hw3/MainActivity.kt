package com.example.hw3

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
fun String.removeLast(){
    this.substring(0,this.length-1)
}
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //we take by their IDs
        val editText = findViewById<EditText>(R.id.editTextNumber)//for input
        val button = findViewById<Button>(R.id.buttonConvert)//for converting
        val resultTextView = findViewById<TextView>(R.id.textViewResult)//for the result
        val toggleButton = findViewById<ToggleButton>(R.id.toggleLanguage)//for changing language to convert

        button.setOnClickListener {
            val inputText = editText.text.toString()
            if (inputText.isNotEmpty()) {
                val number = inputText.toIntOrNull()
                if (number != null && number in 1..1000) {
                    val isEnglish = toggleButton.isChecked//its returns boolean value
                    if (isEnglish) {
                        resultTextView.text = numberToEnglish(number)//function for converting into english
                    } else {
                        resultTextView.text = numberToGeorgian(number)//function for converting into georgian
                    }
                } else {
                    Toast.makeText(this, "Please enter a number between 1 and 1000", Toast.LENGTH_SHORT).show()//in case of too big value we show toast
                }
            } else {
                Toast.makeText(this, "Input field cannot be empty", Toast.LENGTH_SHORT).show()//for null value
            }
        }
    }

    // Convert number to Georgian text
    private fun numberToGeorgian(number: Int): String {
        val toTwenty = arrayOf("", "ერთი", "ორი", "სამი", "ოთხი", "ხუთი", "ექვსი", "შვიდი", "რვა", "ცხრა","ათი","თერთმეტი","თორმეტი","ცამეტი","თოთხმეტი","თხუთმეტი","თექვსმეტი","ჩვიდმეტი","თვრამეტი","ცხრამეტი")
        val twenties = arrayOf("", "ოცი", "ორმოცი", "სამოცი", "ოთხმოცი")
        val hundreds = arrayOf("", "ასი", "ორასი", "სამასი", "ოთხასი", "ხუთასი", "ექვსასი", "შვიდასი", "რვაასი", "ცხრაასი")

        val hundred = number / 100
        val twenty = (number % 100) / 20
        val endNumber = number % 20//taking different parts of the number for converting

        return buildString {
            if (hundred>0&&(twenty>0||endNumber>0)) append(hundreds[hundred].removeLastMine()," ")
            else append(hundreds[hundred])

            if (twenty>0 && endNumber > 0) append(twenties[twenty].removeLastMine(),"და")
            else append(twenties[twenty])

            append(toTwenty[endNumber])//logic for converting numbers also we used buildString here because this is more optimal and takes less space also its faster

        }
    }

    // Convert number to English text
    private fun numberToEnglish(number: Int): String {//same for the english numbers
        val units = arrayOf("", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        val fromTenTotTwenty = arrayOf("ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen")
        val tens = arrayOf("", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety")
        val hundreds = arrayOf("", "one hundred", "two hundred", "three hundred", "four hundred", "five hundred", "six hundred", "seven hundred", "eight hundred", "nine hundred")

        val hundred = number / 100
        val ten = (number % 100) / 10
        val unit = number % 10

        return buildString {
            append(hundreds[hundred])
            if (hundred > 0 && (ten > 0 || unit > 0)) append(" ")
            if (ten == 1) {
                append(fromTenTotTwenty[unit])
            } else {
                append(tens[ten])
                if (ten > 0 && unit > 0) append(" ")
                append(units[unit])
            }
        }
    }
}