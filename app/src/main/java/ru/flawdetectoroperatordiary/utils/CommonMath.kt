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

    private val listeners = HashMap<String, OnDataChangeListener>()

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

    fun setListener(name: String, listener: OnDataChangeListener) {
        listeners[name] = listener
    }

    private fun calculate() {
        when (scheme) {
            Scheme.FOUR -> {
                if (!sensitivity.isNaN() && !focalSpot.isNaN()) {
                    listeners["coefC"]?.onChange(coefC)

                    if (!radiationThickness.isNaN()) {
                        listeners["distance"]?.onChange(distance)
                        listeners["plotLength"]?.onChange(plotLength)
                    }
                }
            }
            Scheme.FIVE_A -> {
                if (!externalDiameter.isNaN())
                    listeners["transilluminationPerimeter"]?.onChange(transilluminationPerimeter)

                if (!externalDiameter.isNaN() && !radiationThickness.isNaN()) {
                    listeners["internalDiameter"]?.onChange(internalDiameter)
                    listeners["coefM"]?.onChange(coefM)
                }

                if (!sensitivity.isNaN() && !focalSpot.isNaN())
                    listeners["coefC"]?.onChange(coefC)

                if (!externalDiameter.isNaN() &&
                    !radiationThickness.isNaN() &&
                    !sensitivity.isNaN() &&
                    !focalSpot.isNaN()
                ) {
                    listeners["distance"]?.onChange(distance)
                    listeners["coefN"]?.onChange(coefN)
                    listeners["scansAmount"]?.onChange(scansAmount)
                    listeners["plotLength"]?.onChange(plotLength)
                }
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
            when (scheme) {
                Scheme.FOUR -> return coefC * radiationThickness
                else -> return 0.7 * coefC * (externalDiameter - internalDiameter)
            }
        }

    private val scansAmount: Double
        get() {
            when (scheme) {
                Scheme.FIVE_A ->
                    return PI / (asin(0.7 * coefM) - asin(0.7 * coefM / (2 * coefN + 1)))
                Scheme.FIVE_V ->
                    return 2.0
                Scheme.FIVE_G, Scheme.FIVE_D ->
                    return PI / (asin(coefP * coefM) + asin(coefP * coefM / (2 * coefN + 1)))
                else ->
                    throw IllegalStateException("scans amount is not applicable")
            }
        }

    private val coefP: Double
        get() = sqrt(1 - 0.2 * (2.6 - 1 / coefM).pow(2))

    private val plotLength: Double
        get() {
            when (scheme) {
                Scheme.FOUR ->
                    return 0.8 * distance
                Scheme.FIVE_A ->
                    return if (transilluminationPerimeter / scansAmount > 100)
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

