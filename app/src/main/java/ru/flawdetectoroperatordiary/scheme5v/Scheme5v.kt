package ru.flawdetectoroperatordiary.scheme5v

import android.view.View
import android.widget.TextView
import ru.flawdetectoroperatordiary.R
import ru.flawdetectoroperatordiary.utils.CommonMath
import ru.flawdetectoroperatordiary.utils.DefaultFragment
import ru.flawdetectoroperatordiary.utils.Field
import ru.flawdetectoroperatordiary.utils.Scheme

class Scheme5v : DefaultFragment() {
    private lateinit var coefC: TextView
    private lateinit var distance: TextView
    private lateinit var scansAmount: TextView
    private lateinit var plotLength: TextView

    override fun getLayout(): Int {
        return R.layout.fragment_scheme5v
    }

    override fun setMath() {
        math = CommonMath(Scheme.FIVE_V)
    }

    override fun initCalculatedFields(view: View) {
        with(view) {
            coefC = findViewById(R.id.tv_coef_c)
            distance = findViewById(R.id.tv_distance)
            scansAmount = findViewById(R.id.tv_scans_amount)
            plotLength = findViewById(R.id.tv_plot_length)
        }
    }

    override fun setCalculatedFieldListeners() {
        setMathListener(Field.COEFC, coefC)
        setMathListener(Field.DISTANCE, distance, intFormat = true)
        setMathListener(Field.SCANS_AMOUNT, scansAmount, intFormat = true)
        setMathListener(Field.PLOT_LENGTH, plotLength)
    }


}
