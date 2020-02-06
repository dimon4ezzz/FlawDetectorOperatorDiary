package ru.flawdetectoroperatordiary.scheme4

import android.view.View
import android.widget.TextView
import ru.flawdetectoroperatordiary.R
import ru.flawdetectoroperatordiary.utils.CommonMath
import ru.flawdetectoroperatordiary.utils.DefaultFragment
import ru.flawdetectoroperatordiary.utils.Field
import ru.flawdetectoroperatordiary.utils.Scheme

class Scheme4 : DefaultFragment() {
    private lateinit var coefC: TextView
    private lateinit var distance: TextView
    private lateinit var plotLength: TextView

    override fun setActivityTitle() {
        activity!!.setTitle(R.string.scheme5a_title)
    }

    override fun setMath() {
        math = CommonMath(Scheme.FOUR)
    }

    override fun initCalculatedFields(view: View) {
        with(view) {
            coefC = findViewById(R.id.tv_coef_c)
            distance = findViewById(R.id.tv_distance)
            plotLength = findViewById(R.id.tv_plot_length)
        }
    }

    override fun setCalculatedFieldListeners() {
        setMathListener(Field.COEFC, coefC)
        setMathListener(Field.DISTANCE, distance)
        setMathListener(Field.PLOT_LENGTH, plotLength)
    }
}
