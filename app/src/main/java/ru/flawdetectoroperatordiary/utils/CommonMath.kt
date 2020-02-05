package ru.flawdetectoroperatordiary.utils

import kotlin.math.PI
import kotlin.math.asin
import kotlin.math.pow
import kotlin.math.sqrt

class CommonMath(private val scheme: Scheme) {
    private var externalDiameter: Double = Double.NaN
    private var radiationThickness: Double = Double.NaN
    private var sensitivity: Double = Double.NaN
    private var focalSpot: Double = Double.NaN

    private val listeners = HashMap<Field, OnDataChangeListener>()

    fun setExternalDiameter(value: Double) {
        externalDiameter = value
        calculate()
    }

    fun setRadiationThickness(value: Double) {
        radiationThickness = value
        calculate()
    }

    fun setSensitivity(value: Double) {
        sensitivity = value
        calculate()
    }

    fun setFocalSpot(value: Double) {
        focalSpot = value
        calculate()
    }

    fun setListener(field: Field, listener: OnDataChangeListener) {
        listeners[field] = listener
    }

    fun unsetExternalDiameter() {
        externalDiameter = Double.NaN
        calculate()
    }

    fun unsetRadiationThickness() {
        radiationThickness = Double.NaN
        calculate()
    }

    fun unsetSensitivity() {
        sensitivity = Double.NaN
        calculate()
    }

    fun unsetFocalSpot() {
        focalSpot = Double.NaN
        calculate()
    }

    private fun calculate() {
        when (scheme) {
            Scheme.FOUR -> {
                trySetCoefC()
                trySetDistance()
                trySetPlotLength()
            }
            Scheme.FIVE_A -> {
                trySetTransilluminationPerimeter()
                trySetInternalDiameter()
                trySetCoefM()
                trySetCoefC()
                trySetDistance()
                trySetScansAmount()
                trySetPlotLength()
                trySetCoefN()
            }
            Scheme.FIVE_B -> TODO()
            Scheme.FIVE_V -> TODO()
            Scheme.FIVE_G -> TODO()
            Scheme.FIVE_D -> TODO()
            Scheme.FIVE_E -> TODO()
            Scheme.FIVE_Zh -> TODO()
            Scheme.FIVE_Z -> TODO()
            Scheme.SIX -> TODO()
        }
    }

    private fun trySetCoefC() {
        if (!coefC.isNaN())
            listeners[Field.COEFC]?.onChange(coefC)
        else
            listeners[Field.COEFC]?.onErase()
    }

    private fun trySetInternalDiameter() {
        if (!internalDiameter.isNaN())
            listeners[Field.INTERNAL_DIAMETER]?.onChange(internalDiameter)
        else
            listeners[Field.INTERNAL_DIAMETER]?.onErase()
    }

    private fun trySetCoefM() {
        if (!coefM.isNaN())
            listeners[Field.COEFM]?.onChange(coefM)
        else
            listeners[Field.COEFM]?.onErase()
    }

    private fun trySetTransilluminationPerimeter() {
        if (!transilluminationPerimeter.isNaN())
            listeners[Field.TRANSILLUMINATION_PERIMETER]?.onChange(transilluminationPerimeter)
        else
            listeners[Field.TRANSILLUMINATION_PERIMETER]?.onErase()
    }

    private fun trySetDistance() {
        if (!distance.isNaN())
            listeners[Field.DISTANCE]?.onChange(distance)
        else
            listeners[Field.DISTANCE]?.onErase()
    }

    private fun trySetScansAmount() {
        if (!scansAmount.isNaN())
            listeners[Field.SCANS_AMOUNT]?.onChange(scansAmount)
        else
            listeners[Field.SCANS_AMOUNT]?.onErase()
    }

    private fun trySetPlotLength() {
        if (!plotLength.isNaN())
            listeners[Field.PLOT_LENGTH]?.onChange(plotLength)
        else
            listeners[Field.PLOT_LENGTH]?.onErase()
    }

    private fun trySetCoefN() {
        if (!coefN.isNaN())
            listeners[Field.COEFN]?.onChange(coefN)
        else
            listeners[Field.COEFN]?.onErase()
    }

    private val coefC: Double
        get() {
            return if (focalSpot / sensitivity < 2) 4.0
            else 2 * focalSpot / sensitivity
        }

    private val internalDiameter: Double
        get() = externalDiameter - 2 * radiationThickness

    private val coefM: Double
        get() = internalDiameter / externalDiameter

    private val transilluminationPerimeter: Double
        get() = PI * externalDiameter

    private val distance: Double
        get() {
            return when (scheme) {
                Scheme.FOUR -> coefC * radiationThickness
                else -> 0.7 * coefC * (externalDiameter - internalDiameter)
            }
        }

    private val scansAmount: Double
        get() {
            return when (scheme) {
                Scheme.FIVE_A ->
                    PI / (asin(0.7 * coefM) - asin(0.7 * coefM / (2 * coefN + 1)))
                Scheme.FIVE_V ->
                    2.0
                Scheme.FIVE_G, Scheme.FIVE_D ->
                    PI / (asin(coefP * coefM) + asin(coefP * coefM / (2 * coefN + 1)))
                else ->
                    throw IllegalStateException("scans amount is not applicable")
            }
        }

    private val coefP: Double
        get() = sqrt(1 - 0.2 * (2.6 - 1 / coefM).pow(2))

    private val plotLength: Double
        get() {
            return when (scheme) {
                Scheme.FOUR ->
                    0.8 * distance
                Scheme.FIVE_A ->
                    if (transilluminationPerimeter / scansAmount > 100)
                        20 + transilluminationPerimeter / scansAmount
                    else
                        1.2 * transilluminationPerimeter / scansAmount
                else ->
                    throw IllegalStateException("plot length is not applicable")
            }
        }

    private val coefN: Double
        get() = distance / externalDiameter
}

