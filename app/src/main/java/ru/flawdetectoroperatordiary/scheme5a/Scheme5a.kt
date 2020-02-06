package ru.flawdetectoroperatordiary.scheme5a

import android.view.View
import android.widget.TextView
import ru.flawdetectoroperatordiary.R
import ru.flawdetectoroperatordiary.utils.*

private const val FORMAT = "%.4f"
private const val INT_FORMAT = "%.0f"

class Scheme5a : DefaultFragment() {
    private lateinit var internalDiameter: TextView
    private lateinit var coefC: TextView
    private lateinit var coefM: TextView
    private lateinit var coefN: TextView
    private lateinit var transilluminationPerimeter: TextView
    private lateinit var distance: TextView
    private lateinit var scansAmount: TextView
    private lateinit var plotLength: TextView

    override fun setActivityTitle() {
        activity!!.setTitle(R.string.scheme5a_title)
    }

    override fun setMath() {
        math = CommonMath(Scheme.FIVE_A)
    }

    override fun initCalculatedFields(view: View) {
        with(view) {
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

    override fun setCalculatedFieldListeners() {
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
