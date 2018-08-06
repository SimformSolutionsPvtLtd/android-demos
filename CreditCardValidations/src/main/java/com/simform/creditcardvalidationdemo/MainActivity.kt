package com.simform.creditcardvalidationdemo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.scutt.android.util.card.RegexCardValidator
import com.simform.creditcardvalidationdemo.card.CardTextWatcher
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et_card_number.addTextChangedListener(CardTextWatcher())
        addTextChangeListener()

        tv_save.setOnClickListener { validateCardDetails() }
    }

    private fun validateCardDetails() {
        val result = RegexCardValidator.isValid(et_card_number.text.toString())
        if (allFieldsFilled())
            Toast.makeText(this, "Please fill all fields.", Toast.LENGTH_LONG).show()
        else if (!result.isValid)
            Toast.makeText(this, "Please enter valid card.", Toast.LENGTH_LONG).show()
        else if (!validateCardExpiryMonth(et_month.text.toString()))
            Toast.makeText(this, "Please enter valid month.", Toast.LENGTH_LONG).show()
        else if (et_year.text.toString().toInt() < getCurrentTwoDigitYear())
            Toast.makeText(this, "Please enter valid year.", Toast.LENGTH_LONG).show()
        else if ((getCurrentMonth() > et_month.text.toString().toInt()) && (getCurrentTwoDigitYear() == et_year.text.toString().toInt()))
            Toast.makeText(this, "Please enter valid month and year.", Toast.LENGTH_LONG).show()
        /* If you don't want to allow same month/year then uncomment this code
        else if ((getCurrentMonth() == et_month.text.toString().toInt()) && (getCurrentTwoDigitYear() == et_year.text.toString().toInt()))
            Toast.makeText(this, "Please enter valid month and year.", Toast.LENGTH_LONG).show()*/
        else if (et_cvv.text.length < 3 || getSum(et_cvv.text.toString().toInt()) == 0)
            Toast.makeText(this, "Please enter valid CVV.", Toast.LENGTH_LONG).show()
        else
            Toast.makeText(this, "Looks good.", Toast.LENGTH_LONG).show()
    }

    private fun allFieldsFilled(): Boolean = et_card_number.text.trim().isEmpty() || et_month.text.trim().isEmpty() || et_year.text.trim().isEmpty() || et_cvv.text.trim().isEmpty()

    private fun validateCardExpiryMonth(expiryDate: String): Boolean {
        return expiryDate.matches("(?:0[1-9]|1[0-2])".toRegex())
    }

    private fun getCurrentTwoDigitYear(): Int {
        return Calendar.getInstance().get(Calendar.YEAR) % 100
    }

    private fun getCurrentMonth(): Int {
        return Calendar.getInstance().get(Calendar.MONTH) + 1
    }

    private fun getSum(n: Int): Int {
        var n = n
        var sum = 0
        while (n > 0) {
            sum += n % 10
            n /= 10
        }
        return sum
    }

    private fun addTextChangeListener() {
        et_month.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_month.text.toString().length == 2 && et_year.text.isEmpty())
                    et_year.requestFocus()
            }

        })

        et_year.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (et_year.text.toString().length == 2 && et_cvv.text.isEmpty())
                    et_cvv.requestFocus()
            }

        })
    }
}
