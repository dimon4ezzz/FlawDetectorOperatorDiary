package ru.flawdetectoroperatordiary.scheme5a

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import ru.flawdetectoroperatordiary.R
import ru.flawdetectoroperatordiary.utils.*

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
            externalDiameter.setOnEditorActionListener(getOnEditorActionListener(
                set = { math.setExternalDiameter(it) },
                unset = { math.unsetExternalDiameter() }
            ))
            radiationThickness = findViewById(R.id.et_radiation_thickness)
            radiationThickness.setOnEditorActionListener(getOnEditorActionListener(
                set = { math.setRadiationThickness(it) },
                unset = { math.unsetRadiationThickness() }
            ))
            sensitivity = findViewById(R.id.et_sensitivity)
            sensitivity.setOnEditorActionListener(getOnEditorActionListener(
                set = { math.setSensitivity(it) },
                unset = { math.unsetSensitivity() }
            ))
            focalSpot = findViewById(R.id.et_focal_spot)
            focalSpot.setOnEditorActionListener(getOnEditorActionListener(
                set = { math.setFocalSpot(it) },
                unset = { math.unsetFocalSpot() }
            ))

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
        math.setListener(Field.INTERNAL_DIAMETER, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                internalDiameter.text = FORMAT.format(value)
                internalDiameter.isEnabled = true
            }

            override fun onErase() {
                internalDiameter.text = FORMAT.format(1.0)
                internalDiameter.isEnabled = false
            }
        })
        math.setListener(Field.COEFC, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                coefC.text = FORMAT.format(value)
                coefC.isEnabled = true
            }

            override fun onErase() {
                coefC.text = FORMAT.format(1.0)
                coefC.isEnabled = false
            }
        })
        math.setListener(Field.COEFM, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                coefM.text = FORMAT.format(value)
                coefM.isEnabled = true
            }

            override fun onErase() {
                coefM.text = FORMAT.format(1.0)
                coefM.isEnabled = false
            }
        })
        math.setListener(Field.COEFN, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                coefN.text = FORMAT.format(value)
                coefN.isEnabled = true
            }

            override fun onErase() {
                coefN.text = FORMAT.format(1.0)
                coefN.isEnabled = false
            }
        })
        math.setListener(Field.TRANSILLUMINATION_PERIMETER, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                transilluminationPerimeter.text = FORMAT.format(value)
                transilluminationPerimeter.isEnabled = true
            }

            override fun onErase() {
                transilluminationPerimeter.text = FORMAT.format(1.0)
                transilluminationPerimeter.isEnabled = false
            }
        })
        math.setListener(Field.DISTANCE, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                distance.text = FORMAT.format(value)
                distance.isEnabled = true
            }

            override fun onErase() {
                distance.text = FORMAT.format(1.0)
                distance.isEnabled = false
            }
        })
        math.setListener(Field.SCANS_AMOUNT, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                scansAmount.text = INT_FORMAT.format(value)
                scansAmount.isEnabled = true
            }

            override fun onErase() {
                scansAmount.text = INT_FORMAT.format(1.0)
                scansAmount.isEnabled = false
            }
        })
        math.setListener(Field.PLOT_LENGTH, object : OnDataChangeListener {
            override fun onChange(value: Double) {
                plotLength.text = FORMAT.format(value)
                plotLength.isEnabled = true
            }

            override fun onErase() {
                plotLength.text = FORMAT.format(1.0)
                plotLength.isEnabled = false
            }
        })
    }
}
