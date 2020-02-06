package ru.flawdetectoroperatordiary.utils

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.flawdetectoroperatordiary.R

const val FORMAT = "%.4f"
const val INT_FORMAT = "%.0f"

abstract class DefaultFragment : Fragment() {
    private lateinit var externalDiameter: EditText
    private lateinit var radiationThickness: EditText
    private lateinit var sensitivity: EditText
    private lateinit var focalSpot: EditText

    protected lateinit var math: CommonMath

    private var counter = 4
    private var wasEmpty = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scheme5a, container, false)

        with(view) {
            externalDiameter = findViewById(R.id.et_external_diameter)
            radiationThickness = findViewById(R.id.et_radiation_thickness)
            sensitivity = findViewById(R.id.et_sensitivity)
            focalSpot = findViewById(R.id.et_focal_spot)
        }

        initCalculatedFields(view)

        setActivityTitle()
        setMath()

        setFieldListeners()
        setCalculatedFieldListeners()

        // Inflate the layout for this fragment
        return view
    }

    protected abstract fun setActivityTitle()
    protected abstract fun setMath()

    protected abstract fun initCalculatedFields(view: View)
    protected abstract fun setCalculatedFieldListeners()

    protected fun setMathListener(field: Field, textView: TextView, intFormat: Boolean = false) {
        math.setListener(field, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                textView.text = value.format(intFormat)
                textView.isEnabled = true
            }

            override fun onErase() {
                textView.text = (1.0).format(intFormat)
                textView.isEnabled = false
            }
        })
    }

    private fun Double.format(intFormat: Boolean): String =
        if (intFormat) INT_FORMAT.format(this) else FORMAT.format(this)

    private fun setFieldListeners() {
        externalDiameter.setOnEditorActionListener(defaultOnEditorActionListener())
        externalDiameter.onFocusChangeListener = defaultOnFocusChangeListener(
            set = { math.setExternalDiameter(it) },
            unset = { math.unsetExternalDiameter() }
        )

        radiationThickness.setOnEditorActionListener(defaultOnEditorActionListener())
        radiationThickness.onFocusChangeListener = defaultOnFocusChangeListener(
            set = { math.setRadiationThickness(it) },
            unset = { math.unsetRadiationThickness() }
        )

        sensitivity.setOnEditorActionListener(defaultOnEditorActionListener())
        sensitivity.onFocusChangeListener = defaultOnFocusChangeListener(
            set = { math.setSensitivity(it) },
            unset = { math.unsetSensitivity() }
        )

        focalSpot.setOnEditorActionListener(defaultOnEditorActionListener())
        focalSpot.onFocusChangeListener = defaultOnFocusChangeListener(
            set = { math.setFocalSpot(it) },
            unset = { math.unsetFocalSpot() }
        )
    }

    private fun defaultOnEditorActionListener() =
        TextView.OnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER ||
                event?.keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER ||
                event?.keyCode == KeyEvent.KEYCODE_DPAD_CENTER
            ) {
                requestFocus(v)

                // attribution: https://stackoverflow.com/a/1109108/3686575
                if (actionId == EditorInfo.IME_ACTION_DONE)
                    view?.let {
                        it.clearFocus()
                        val imm =
                            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                        imm?.hideSoftInputFromWindow(it.windowToken, 0)
                    }
                return@OnEditorActionListener true
            }

            false
        }

    private fun requestFocus(v: TextView) {
        when {
            externalDiameter.text.isEmpty() && v != externalDiameter ->
                externalDiameter.requestFocus()
            radiationThickness.text.isEmpty() && v != radiationThickness ->
                radiationThickness.requestFocus()
            sensitivity.text.isEmpty() && v != sensitivity ->
                sensitivity.requestFocus()
            focalSpot.text.isEmpty() && v != focalSpot ->
                focalSpot.requestFocus()
        }
    }

    private fun defaultOnFocusChangeListener(set: (Double) -> Unit, unset: () -> Unit) =
        View.OnFocusChangeListener { v, hasFocus ->
            require(v is EditText) { "default on-focus change listener only for EditText" }

            if (v.text.isNotEmpty())
                set(v.text.toString().toDouble())
            else
                unset()

            if (!hasFocus) {
                if (v.text.isEmpty() && !wasEmpty) {
                    counter++
                } else if (v.text.isNotEmpty() && wasEmpty && counter > 1) {
                    counter--
                }
            } else {
                if (counter == 1)
                    v.imeOptions = EditorInfo.IME_ACTION_DONE
                else
                    v.imeOptions = EditorInfo.IME_ACTION_NEXT

                wasEmpty = v.text.isEmpty()
            }
        }
}
