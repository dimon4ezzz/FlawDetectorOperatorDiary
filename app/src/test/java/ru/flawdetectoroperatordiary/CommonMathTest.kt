package ru.flawdetectoroperatordiary

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.flawdetectoroperatordiary.utils.getCoefC
import ru.flawdetectoroperatordiary.utils.getCoefM
import ru.flawdetectoroperatordiary.utils.getInternalDiameter

private const val DEFAULT_DELTA = 0.0

class CommonMathTest {
    private val externalDiameter = 10.0
    private val radiationWeight = 2.0
    private val sensitivity = 5.0
    private val focus = 15.0

    private val internalDiameter = 6.0
    private val coefC = 6.0
    private val coefM = 0.6

    @Test
    fun getInternalDiameter() {
        val expected = internalDiameter
        val got = getInternalDiameter(externalDiameter, radiationWeight)
        assertEquals(expected, got, DEFAULT_DELTA)
    }

    @Test
    fun getCoefCTest() {
        val expected = coefC
        val got = getCoefC(sensitivity, focus)
        assertEquals(expected, got, DEFAULT_DELTA)
    }

    @Test
    fun getCoefMTest() {
        val expected = coefM
        val got = getCoefM(externalDiameter, radiationWeight)
        assertEquals(expected, got, DEFAULT_DELTA)
    }
}
