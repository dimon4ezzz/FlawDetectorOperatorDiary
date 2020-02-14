package ru.flawdetectoroperatordiary.scheme5e

import android.view.View
import android.widget.EditText
import android.widget.TextView
import ru.flawdetectoroperatordiary.R
import ru.flawdetectoroperatordiary.utils.CommonMath
import ru.flawdetectoroperatordiary.utils.DefaultFragment
import ru.flawdetectoroperatordiary.utils.Field
import ru.flawdetectoroperatordiary.utils.Scheme

class Scheme5e : DefaultFragment() {
    private lateinit var realDistance: EditText

    private lateinit var internalDiameter: TextView
    private lateinit var coefC: TextView
    private lateinit var coefM: TextView
    private lateinit var distance: TextView
    private lateinit var transilluminationPerimeter: TextView
    private lateinit var geometricBlur: TextView
    private lateinit var calculatedFocalSpot: TextView

    override fun getLayout(): Int {
        return R.layout.fragment_scheme5e
    }

    override fun setMath() {
        math = CommonMath(Scheme.FIVE_E)
    }

    override fun initCalculatedFields(view: View) {
        with(view) {
            realDistance = findViewById(R.id.et_real_distance)
            internalDiameter = findViewById(R.id.tv_internal_diameter)
            coefC = findViewById(R.id.tv_coef_c)
            coefM = findViewById(R.id.tv_coef_m)
            distance = findViewById(R.id.tv_distance)
            transilluminationPerimeter = findViewById(R.id.tv_transillumination_perimeter)
            geometricBlur = findViewById(R.id.tv_geometric_blur)
            calculatedFocalSpot = findViewById(R.id.tv_calculated_focal_spot)
        }
    }

    override fun setCalculatedFieldListeners() {
        realDistance.setOnFocusChangeListener { v, _ ->
            checkEditText(v as EditText, distance.toDouble())
        }
        setMathListener(Field.INTERNAL_DIAMETER, internalDiameter)
        setMathListener(Field.COEFC, coefC)
        setMathListener(Field.COEFM, coefM)
        setMathListener(Field.DISTANCE, distance)
        setMathListener(Field.TRANSILLUMINATION_PERIMETER, transilluminationPerimeter)
        setMathListener(Field.GEOMETRIC_BLUR, geometricBlur)
        setMathListener(Field.CALCULATED_FOCAL_SPOT, calculatedFocalSpot)
    }
}
