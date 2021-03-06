package ru.flawdetectoroperatordiary.scheme5d

import android.view.View
import android.widget.TextView
import ru.flawdetectoroperatordiary.R
import ru.flawdetectoroperatordiary.utils.CommonMath
import ru.flawdetectoroperatordiary.utils.DefaultFragment
import ru.flawdetectoroperatordiary.utils.Field
import ru.flawdetectoroperatordiary.utils.Scheme

class Scheme5d : DefaultFragment() {
    private lateinit var internalDiameter: TextView
    private lateinit var coefC: TextView
    private lateinit var coefM: TextView
    private lateinit var coefN: TextView
    private lateinit var pVar: TextView
    private lateinit var distance: TextView
    private lateinit var scansAmount: TextView
    private lateinit var filmLength: TextView
    private lateinit var rotationAngle: TextView

    override fun getLayout(): Int {
        return R.layout.fragment_scheme5d
    }

    override fun setMath() {
        math = CommonMath(Scheme.FIVE_D)
    }

    override fun initCalculatedFields(view: View) {
        with(view) {
            internalDiameter = findViewById(R.id.tv_internal_diameter)
            coefC = findViewById(R.id.tv_coef_c)
            coefM = findViewById(R.id.tv_coef_m)
            coefN = findViewById(R.id.tv_coef_n)
            pVar = findViewById(R.id.tv_p_var)
            distance = findViewById(R.id.tv_distance)
            scansAmount = findViewById(R.id.tv_scans_amount)
            filmLength = findViewById(R.id.tv_plot_length)
            rotationAngle = findViewById(R.id.tv_rotation_angle)
        }
    }

    override fun setCalculatedFieldListeners() {
        setMathListener(Field.INTERNAL_DIAMETER, internalDiameter)
        setMathListener(Field.COEFC, coefC)
        setMathListener(Field.COEFM, coefM)
        setMathListener(Field.COEFN, coefN)
        setMathListener(Field.P_VAR, pVar)
        setMathListener(Field.DISTANCE, distance, intFormat = true)
        setMathListener(Field.SCANS_AMOUNT, scansAmount, intFormat = true)
        setMathListener(Field.ROTATION_ANGLE, rotationAngle)
        setMathListener(Field.FILM_LENGTH, filmLength, intFormat = true)
    }
}
