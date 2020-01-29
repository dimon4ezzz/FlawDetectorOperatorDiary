package ru.flawdetectoroperatordiary.utils

fun getCoefC(sensitivity: Double, focus: Double): Double {
    val result = focus / sensitivity

    return if (result < 2) 4.0
    else 2 * result
}

fun getInternalDiameter(externalDiameter: Double, radiationWeight: Double): Double =
    externalDiameter - 2 * radiationWeight

fun getCoefM(externalDiameter: Double, radiationWeight: Double): Double =
    getInternalDiameter(externalDiameter, radiationWeight) / externalDiameter

