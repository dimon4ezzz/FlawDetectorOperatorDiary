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
                trySet(Field.COEFC, coefC)
                trySet(Field.DISTANCE, distance)
                trySet(Field.PLOT_LENGTH, plotLength)
            }
            Scheme.FIVE_A -> {
                trySet(Field.TRANSILLUMINATION_PERIMETER, transilluminationPerimeter)
                trySet(Field.INTERNAL_DIAMETER, internalDiameter)
                trySet(Field.COEFM, coefM)
                trySet(Field.COEFC, coefC)
                trySet(Field.DISTANCE, distance)
                trySet(Field.SCANS_AMOUNT, scansAmount)
                trySet(Field.PLOT_LENGTH, plotLength)
                trySet(Field.COEFN, coefN)
            }
            Scheme.FIVE_B -> TODO()
            Scheme.FIVE_V -> {
                trySet(Field.COEFC, coefC)
                trySet(Field.DISTANCE, distance)
                trySet(Field.SCANS_AMOUNT, scansAmount)
                trySet(Field.PLOT_LENGTH, plotLength)
            }
            Scheme.FIVE_G -> {
                trySet(Field.INTERNAL_DIAMETER, internalDiameter)
                trySet(Field.COEFC, coefC)
                trySet(Field.COEFM, coefM)
                trySet(Field.COEFN, coefN)
                trySet(Field.P_VAR, pVar)
                trySet(Field.DISTANCE, distance)
                trySet(Field.SCANS_AMOUNT, scansAmount)
                trySet(Field.ROTATION_ANGLE, rotationAngle)
                trySet(Field.PLOT_LENGTH, plotLength)
            }
            Scheme.FIVE_D -> {
                trySet(Field.INTERNAL_DIAMETER, internalDiameter)
                trySet(Field.COEFC, coefC)
                trySet(Field.COEFM, coefM)
                trySet(Field.COEFN, coefN)
                trySet(Field.P_VAR, pVar)
                trySet(Field.DISTANCE, distance)
                trySet(Field.SCANS_AMOUNT, scansAmount)
                trySet(Field.ROTATION_ANGLE, rotationAngle)
                trySet(Field.FILM_LENGTH, filmLength)
            }
            Scheme.FIVE_E -> {
                trySet(Field.INTERNAL_DIAMETER, internalDiameter)
                trySet(Field.COEFC, coefC)
                trySet(Field.COEFM, coefM)
                trySet(Field.DISTANCE, distance)
                trySet(Field.TRANSILLUMINATION_PERIMETER, transilluminationPerimeter)
                trySet(Field.GEOMETRIC_BLUR, geometricBlur)
                trySet(Field.CALCULATED_FOCAL_SPOT, calculatedFocalSpot)
            }
            Scheme.FIVE_Zh -> TODO()
            Scheme.FIVE_Z -> TODO()
            Scheme.SIX -> TODO()
        }
    }

    private fun trySet(field: Field, value: Double) {
        if (!value.isNaN())
            listeners[field]?.onChange(value)
        else
            listeners[field]?.onErase()
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
                Scheme.FIVE_V -> coefC * externalDiameter
                Scheme.FIVE_G ->
                    if (0.5 * (1.5 * coefC * (externalDiameter - internalDiameter) - externalDiameter) < 1)
                        externalDiameter
                    else
                        0.5 * (1.5 * coefC * (externalDiameter - internalDiameter) - externalDiameter)
                Scheme.FIVE_D -> 0.5 * (coefC * (1.4 * externalDiameter - internalDiameter) - externalDiameter)
                Scheme.FIVE_E -> internalDiameter / 2
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
                    PI / (asin(pVar * coefM) + asin(pVar * coefM / (2 * coefN + 1)))
                else ->
                    throw IllegalStateException("scans amount is not applicable")
            }
        }

    private val pVar: Double
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
                Scheme.FIVE_V ->
                    40 + externalDiameter
                Scheme.FIVE_G ->
                    1.2 * externalDiameter
                else ->
                    throw IllegalStateException("plot length is not applicable")
            }
        }

    private val coefN: Double
        get() = distance / externalDiameter

    private val rotationAngle: Double
        get() = 360 / scansAmount

    private val filmLength: Double
        get() = 250.0

    private val geometricBlur: Double
        get() = focalSpot * (3 + radiationThickness) / distance

    private val calculatedFocalSpot: Double
        get() = sensitivity * internalDiameter / (2 * (externalDiameter - internalDiameter))
}

