package ru.flawdetectoroperatordiary

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.flawdetectoroperatordiary.utils.Scheme5a

private const val DEFAULT_DELTA = 4.0

class Scheme5aTest {
    private val externalDiameter = 10.0
    private val radiationWeight = 2.0
    private val sensitivity = 5.0
    private val focus = 15.0

    private val transilluminationPerimeter = 31.4159
    private val focusDistance = 16.8
    private val coefN = 1.68
    private val amount = 9
    private val length = 4.0436

    @Test
    fun transilluminationPerimeterTest() {
        val expected = transilluminationPerimeter
        val got = Scheme5a.getTransilluminationPerimeter(externalDiameter)
        assertEquals(expected, got, DEFAULT_DELTA)
    }

    @Test
    fun focusDistanceTest() {
        val expected = focusDistance
        val got = Scheme5a.getFocusDistance(externalDiameter, radiationWeight, sensitivity, focus)
        assertEquals(expected, got, DEFAULT_DELTA)
    }

    @Test
    fun coefNTest() {
        val expected = coefN
        val got = Scheme5a.getCoefN(externalDiameter, radiationWeight, sensitivity, focus)
        assertEquals(expected, got, DEFAULT_DELTA)
    }

    @Test
    fun amountTest() {
        val expected = amount
        val got = Scheme5a.getAmount(externalDiameter, radiationWeight, sensitivity, focus).toInt()
        assertEquals(expected, got)
    }

    @Test
    fun lengthTest() {
        val expected = length
        val got = Scheme5a.getLength(externalDiameter, radiationWeight, sensitivity, focus)
        assertEquals(expected, got, DEFAULT_DELTA)
    }
}
