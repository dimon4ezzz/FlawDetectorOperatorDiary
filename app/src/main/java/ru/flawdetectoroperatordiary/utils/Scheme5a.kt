package ru.flawdetectoroperatordiary.utils

import kotlin.math.asin

object Scheme5a {
    fun getTransilluminationPerimeter(externalDiameter: Double): Double =
        externalDiameter * Math.PI

    fun getFocusDistance(
        externalDiameter: Double,
        radiationWeight: Double,
        sensitivity: Double,
        focus: Double
    ): Double =
        0.7 * getCoefC(sensitivity, focus) * (externalDiameter - getInternalDiameter(
            externalDiameter,
            radiationWeight
        ))

    fun getAmount(
        externalDiameter: Double,
        radiationWeight: Double,
        sensitivity: Double,
        focus: Double
    ): Double =
        Math.PI / (asin(0.7 * getCoefM(externalDiameter, radiationWeight)) - asin(
            0.7 * getCoefM(
                externalDiameter,
                radiationWeight
            ) / (2 * getCoefN(externalDiameter, radiationWeight, sensitivity, focus) + 1)
        ))

    fun getLength(
        externalDiameter: Double,
        radiationWeight: Double,
        sensitivity: Double,
        focus: Double
    ): Double {
        val result = getTransilluminationPerimeter(externalDiameter) / getAmount(
            externalDiameter,
            radiationWeight,
            sensitivity,
            focus
        )

        return if (result > 100)
            20 + result
        else
            1.2 * result
    }

    fun getCoefN(
        externalDiameter: Double,
        radiationWeight: Double,
        sensitivity: Double,
        focus: Double
    ): Double =
        getFocusDistance(externalDiameter, radiationWeight, sensitivity, focus) / externalDiameter
}