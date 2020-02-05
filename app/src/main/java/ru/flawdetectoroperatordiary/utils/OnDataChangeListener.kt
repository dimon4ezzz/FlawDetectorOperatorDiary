package ru.flawdetectoroperatordiary.utils

/**
 * Usual OnChange listener.
 */
interface OnDataChangeListener {
    /**
     * When the data changes, the listener receives it.
     */
    fun onChange(value: Double)
}