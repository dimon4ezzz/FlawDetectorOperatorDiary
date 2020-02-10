package ru.flawdetectoroperatordiary.scheme5a

import android.view.View
import android.widget.TextView
import ru.flawdetectoroperatordiary.R
import ru.flawdetectoroperatordiary.utils.CommonMath
import ru.flawdetectoroperatordiary.utils.DefaultFragment
import ru.flawdetectoroperatordiary.utils.Field
import ru.flawdetectoroperatordiary.utils.Scheme

class Scheme5a : DefaultFragment() {
    private lateinit var internalDiameter: TextView
    private lateinit var coefC: TextView
    private lateinit var coefM: TextView
    private lateinit var coefN: TextView
    private lateinit var transilluminationPerimeter: TextView
    private lateinit var distance: TextView
    private lateinit var scansAmount: TextView
    private lateinit var plotLength: TextView

    override fun getLayout(): Int = R.layout.fragment_scheme5a

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
        setMathListener(Field.INTERNAL_DIAMETER, internalDiameter)
        setMathListener(Field.COEFC, coefC)
        setMathListener(Field.COEFM, coefM)
        setMathListener(Field.COEFN, coefN)
        setMathListener(Field.TRANSILLUMINATION_PERIMETER, transilluminationPerimeter)
        setMathListener(Field.DISTANCE, distance)
        setMathListener(Field.SCANS_AMOUNT, scansAmount, intFormat = true)
        setMathListener(Field.PLOT_LENGTH, plotLength)
    }
}
