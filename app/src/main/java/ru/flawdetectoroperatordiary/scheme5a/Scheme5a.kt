package ru.flawdetectoroperatordiary.scheme5a

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.flawdetectoroperatordiary.R
import ru.flawdetectoroperatordiary.utils.Scheme5a.getAmount
import ru.flawdetectoroperatordiary.utils.Scheme5a.getCoefN
import ru.flawdetectoroperatordiary.utils.Scheme5a.getFocusDistance
import ru.flawdetectoroperatordiary.utils.Scheme5a.getLength
import ru.flawdetectoroperatordiary.utils.Scheme5a.getTransilluminationPerimeter
import ru.flawdetectoroperatordiary.utils.getCoefC
import ru.flawdetectoroperatordiary.utils.getCoefM
import ru.flawdetectoroperatordiary.utils.getInternalDiameter
import java.text.DecimalFormat

const val VALUABLE_AMOUNT = 4

class Scheme5a : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scheme5a, container, false)

        setCompanion(view!!)
        setListeners()

        // Inflate the layout for this fragment
        return view
    }

    private fun setCompanion(view: View) {
        with(view) {
            externalDiameter = findViewById(R.id.et_external_diameter)
            radiationWeight = findViewById(R.id.et_radiation_weight)
            sensitivity = findViewById(R.id.et_sensitivity)
            focus = findViewById(R.id.et_focus)

            internalDiameter = findViewById(R.id.tv_internal_diameter)
            coefC = findViewById(R.id.tv_coef_c)
            coefM = findViewById(R.id.tv_coef_m)
            coefN = findViewById(R.id.tv_coef_n)
            transilluminationPerimeter = findViewById(R.id.tv_transillumination_perimeter)
            focusDistance = findViewById(R.id.tv_focus_distance)
            amount = findViewById(R.id.tv_amount)
            length = findViewById(R.id.tv_length)
        }
    }

    private fun setListeners() {
        externalDiameter.setOnEditorActionListener(DefaultOnEditorActionListener())
        externalDiameter.onFocusChangeListener = DefaultOnFocusChangeListener()

        radiationWeight.setOnEditorActionListener(DefaultOnEditorActionListener())
        radiationWeight.onFocusChangeListener = DefaultOnFocusChangeListener()

        sensitivity.setOnEditorActionListener(DefaultOnEditorActionListener())
        sensitivity.onFocusChangeListener = DefaultOnFocusChangeListener()

        focus.setOnEditorActionListener(DefaultOnEditorActionListener())
        focus.onFocusChangeListener = DefaultOnFocusChangeListener()
    }

    private fun calculateInternalDiameter() {
        if (externalDiameter.isNotEmpty() && radiationWeight.isNotEmpty()) {
            internalDiameter.text = getInternalDiameter(
                externalDiameter.toDouble(),
                radiationWeight.toDouble()
            ).format()
            internalDiameter.isEnabled = true
        } else {
            internalDiameter.isEnabled = false
        }
    }

    private fun calculateCoefC() {
        if (sensitivity.isNotEmpty() && focus.isNotEmpty()) {
            coefC.text = getCoefC(sensitivity.toDouble(), focus.toDouble()).format()
            coefC.isEnabled = true
        } else {
            coefC.isEnabled = false
        }
    }

    private fun calculateCoefM() {
        if (externalDiameter.isNotEmpty() && radiationWeight.isNotEmpty()) {
            coefM.text = getCoefM(externalDiameter.toDouble(), radiationWeight.toDouble()).format()
            coefM.isEnabled = true
        } else {
            coefM.isEnabled = false
        }
    }

    private fun calculateCoefN() {
        if (isAllNotEmpty()) {
            coefN.text = getCoefN(
                externalDiameter.toDouble(),
                radiationWeight.toDouble(),
                sensitivity.toDouble(),
                focus.toDouble()
            ).format()
            coefN.isEnabled = true
        } else {
            coefN.isEnabled = false
        }
    }

    private fun calculateTransilluminationPerimeter() {
        if (externalDiameter.isNotEmpty()) {
            transilluminationPerimeter.text =
                getTransilluminationPerimeter(externalDiameter.toDouble()).format()
            transilluminationPerimeter.isEnabled = true
        } else {
            transilluminationPerimeter.isEnabled = false
        }
    }

    private fun calculateFocusDistance() {
        if (isAllNotEmpty()) {
            focusDistance.text = getFocusDistance(
                externalDiameter.toDouble(),
                radiationWeight.toDouble(),
                sensitivity.toDouble(),
                focus.toDouble()
            ).format()
            focusDistance.isEnabled = true
        } else {
            focusDistance.isEnabled = false
        }
    }

    private fun calculateAmount() {
        if (isAllNotEmpty()) {
            amount.text = getAmount(
                externalDiameter.toDouble(),
                radiationWeight.toDouble(),
                sensitivity.toDouble(),
                focus.toDouble()
            ).formatAsInt()
            amount.isEnabled = true
        } else {
            amount.isEnabled = false
        }
    }

    private fun calculateLength() {
        if (isAllNotEmpty()) {
            length.text = getLength(
                externalDiameter.toDouble(),
                radiationWeight.toDouble(),
                sensitivity.toDouble(),
                focus.toDouble()
            ).format()
            length.isEnabled = true
        } else {
            length.isEnabled = false
        }
    }

    private fun calculate() {
        calculateInternalDiameter()
        calculateCoefC()
        calculateCoefM()
        calculateCoefN()
        calculateTransilluminationPerimeter()
        calculateFocusDistance()
        calculateAmount()
        calculateLength()
    }

    private fun isAllNotEmpty(): Boolean =
        externalDiameter.isNotEmpty() && radiationWeight.isNotEmpty() && sensitivity.isNotEmpty() && focus.isNotEmpty()

    private fun EditText.isNotEmpty(): Boolean = this.text.isNotEmpty()

    private fun EditText.toDouble(): Double = this.text.toString().toDouble()

    private fun Double.format(): String {
        val df = DecimalFormat()
        df.maximumFractionDigits = VALUABLE_AMOUNT
        return df.format(this).replace(",", ".")
    }

    private fun Double.formatAsInt(): String {
        val df = DecimalFormat()
        df.maximumFractionDigits = 0
        return df.format(this)

    }

    inner class DefaultOnEditorActionListener : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                when {
                    externalDiameter.text.isEmpty() && v != externalDiameter -> {
                        externalDiameter.requestFocus()
                    }
                    radiationWeight.text.isEmpty() && v != radiationWeight -> {
                        radiationWeight.requestFocus()
                    }
                    sensitivity.text.isEmpty() && v != sensitivity -> {
                        sensitivity.requestFocus()
                    }
                    focus.text.isEmpty() && v != focus -> {
                        focus.requestFocus()
                    }
                }
                return true
            }
            return false
        }
    }

    inner class DefaultOnFocusChangeListener : View.OnFocusChangeListener {
        override fun onFocusChange(v: View, hasFocus: Boolean) {
            if (v !is EditText)
                throw IllegalArgumentException("default on focus change listener only for EditText")

            if (!hasFocus) {
                calculate()

                if (v.text.isEmpty() && !wasEmpty) {
                    emptyFields++
                } else if (v.text.isNotEmpty() && wasEmpty && emptyFields > 1) {
                    emptyFields--
                }
            } else {
                if (emptyFields == 1)
                    v.imeOptions = EditorInfo.IME_ACTION_DONE
                else
                    v.imeOptions = EditorInfo.IME_ACTION_NEXT

                wasEmpty = v.text.isEmpty()
            }
        }
    }

    companion object {
        private var emptyFields = 4
        private var wasEmpty = true
        private lateinit var externalDiameter: EditText
        private lateinit var radiationWeight: EditText
        private lateinit var sensitivity: EditText
        private lateinit var focus: EditText

        private lateinit var internalDiameter: TextView
        private lateinit var coefC: TextView
        private lateinit var coefM: TextView
        private lateinit var coefN: TextView
        private lateinit var transilluminationPerimeter: TextView
        private lateinit var focusDistance: TextView
        private lateinit var amount: TextView
        private lateinit var length: TextView
    }
}
