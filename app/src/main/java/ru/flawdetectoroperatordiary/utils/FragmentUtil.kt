package ru.flawdetectoroperatordiary.utils

import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView

fun getOnEditorActionListener(set: (Double) -> Unit, unset: () -> Unit) =
    object : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId == EditorInfo.IME_ACTION_NEXT ||
                actionId == EditorInfo.IME_ACTION_DONE ||
                event?.keyCode == KeyEvent.KEYCODE_ENTER ||
                event?.keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER ||
                event?.keyCode == KeyEvent.KEYCODE_DPAD_CENTER
            ) {
                if (v.text.isNotEmpty())
                    set(v.text.toString().toDouble())
                else
                    unset()

                return true
            }

            return false
        }
    }