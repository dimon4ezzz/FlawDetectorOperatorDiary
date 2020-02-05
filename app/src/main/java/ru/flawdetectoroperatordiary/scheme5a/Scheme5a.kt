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
import ru.flawdetectoroperatordiary.utils.CommonMath
import ru.flawdetectoroperatordiary.utils.OnDataChangeListener
import ru.flawdetectoroperatordiary.utils.Scheme

private const val FORMAT = "%.4f"
private const val INT_FORMAT = "%.0f"

class Scheme5a : Fragment() {
    private lateinit var externalDiameter: EditText
    private lateinit var radiationThickness: EditText
    private lateinit var sensitivity: EditText
    private lateinit var focalSpot: EditText

    private lateinit var internalDiameter: TextView
    private lateinit var coefC: TextView
    private lateinit var coefM: TextView
    private lateinit var coefN: TextView
    private lateinit var transilluminationPerimeter: TextView
    private lateinit var distance: TextView
    private lateinit var scansAmount: TextView
    private lateinit var plotLength: TextView

    private val math = CommonMath(Scheme.FIVE_A)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_scheme5a, container, false)

        setFields(view)
        setListeners()

        // Inflate the layout for this fragment
        return view
    }

    private fun setFields(view: View) {
        with(view) {
            externalDiameter = findViewById(R.id.et_external_diameter)
            externalDiameter.setOnEditorActionListener(getOnEditorActionListener {
                math.setExternalDiameter(
                    it
                )
            })
            radiationThickness = findViewById(R.id.et_radiation_thickness)
            radiationThickness.setOnEditorActionListener(getOnEditorActionListener {
                math.setRadiationThickness(
                    it
                )
            })
            sensitivity = findViewById(R.id.et_sensitivity)
            sensitivity.setOnEditorActionListener(getOnEditorActionListener { math.setSensitivity(it) })
            focalSpot = findViewById(R.id.et_focal_spot)
            focalSpot.setOnEditorActionListener(getOnEditorActionListener { math.setFocalSpot(it) })

            internalDiameter = findViewById(R.id.tv_internal_diameter)
            coefC = findViewById(R.id.tv_coef_c)
            coefM = findViewById(R.id.tv_coef_m)
            coefN = findViewById(R.id.tv_coef_n)
            transilluminationPerimeter = findViewById(R.id.tv_transillumination_perimeter)
            distance = findViewById(R.id.tv_distance)
            scansAmount = findViewById(R.id.tv_scans_amount)
            plotLength = findViewById(R.id.tv_plot_length)
        }
    }

    private fun setListeners() {
        math.setListener("internalDiameter", object : OnDataChangeListener {
            override fun onChange(value: Double) {
                internalDiameter.text = FORMAT.format(value)
            }
        })
        math.setListener("coefC", object : OnDataChangeListener {
            override fun onChange(value: Double) {
                coefC.text = FORMAT.format(value)
            }
        })
        math.setListener("coefM", object : OnDataChangeListener {
            override fun onChange(value: Double) {
                coefM.text = FORMAT.format(value)
            }
        })
        math.setListener("coefN", object : OnDataChangeListener {
            override fun onChange(value: Double) {
                coefN.text = FORMAT.format(value)
            }
        })
        math.setListener("transilluminationPerimeter", object : OnDataChangeListener {
            override fun onChange(value: Double) {
                transilluminationPerimeter.text = FORMAT.format(value)
            }
        })
        math.setListener("distance", object : OnDataChangeListener {
            override fun onChange(value: Double) {
                distance.text = FORMAT.format(value)
            }
        })
        math.setListener("scansAmount", object : OnDataChangeListener {
            override fun onChange(value: Double) {
                scansAmount.text = INT_FORMAT.format(value)
            }
        })
        math.setListener("plotLength", object : OnDataChangeListener {
            override fun onChange(value: Double) {
                plotLength.text = FORMAT.format(value)
            }
        })
    }

    private fun getOnEditorActionListener(f: (Double) -> Unit) =
        object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_NEXT ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event?.keyCode == KeyEvent.KEYCODE_ENTER ||
                    event?.keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER ||
                    event?.keyCode == KeyEvent.KEYCODE_DPAD_CENTER
                ) {
                    if (v.text.isNotEmpty())
                        f(v.text.toString().toDouble())
                    else
                        f(Double.NaN)

                    return true
                }

                return false
            }
        }
}
