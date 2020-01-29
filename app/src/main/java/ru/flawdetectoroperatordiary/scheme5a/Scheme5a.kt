package ru.flawdetectoroperatordiary.scheme5a


import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import ru.flawdetectoroperatordiary.R

/**
 * A simple [Fragment] subclass.
 */
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

    inner class DefaultOnEditorActionListener : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                when {
                    externalDiameter.text.isEmpty() && v != externalDiameter -> {
                        externalDiameter.requestFocus()
                        return true
                    }
                    radiationWeight.text.isEmpty() && v != radiationWeight -> {
                        radiationWeight.requestFocus()
                        return true
                    }
                    sensitivity.text.isEmpty() && v != sensitivity -> {
                        sensitivity.requestFocus()
                        return true
                    }
                    focus.text.isEmpty() && v != focus -> {
                        focus.requestFocus()
                        return true
                    }
                }
            }
            return false
        }
    }

    inner class DefaultOnFocusChangeListener : View.OnFocusChangeListener {
        override fun onFocusChange(v: View, hasFocus: Boolean) {
            if (v !is EditText)
                throw IllegalArgumentException("default on focus change listener only for EditText")

            if (!hasFocus) {
                if (v.text.isEmpty() && !wasEmpty) {
                    emptyFields++
                } else if (v.text.isNotEmpty() && emptyFields > 1)
                    emptyFields--
            } else {
                if (emptyFields == 1)
                    v.imeOptions = EditorInfo.IME_ACTION_DONE
                else
                    v.imeOptions = EditorInfo.IME_ACTION_NEXT

                wasEmpty = v.text.isEmpty()
            }

            Toast.makeText(view!!.context, emptyFields.toString(), Toast.LENGTH_SHORT).show()
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
