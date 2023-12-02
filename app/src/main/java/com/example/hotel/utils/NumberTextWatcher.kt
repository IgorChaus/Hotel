package com.example.hotel.utils

import android.text.Editable
import android.text.TextWatcher

class NumberTextWatcher(mask: String) : TextWatcher {

    private var oldPhone = mask
    private var indexChar = -1
    private var increment = 0
    private var decrement = 0

    override fun afterTextChanged(s: Editable?) {

        if (increment == 1){
            val phone: String
            val digitChar = s.toString()[indexChar].toString()

            if (digitChar.matches(Regex("[0-9]*"))){
                phone = s.toString().removeRange(indexChar, indexChar + 1)
                    .replaceFirst("*",digitChar)
            } else {
                phone = s.toString().removeRange(indexChar, indexChar + 1)
            }

            oldPhone = phone
            s?.clear()
            s?.append(phone)
        }

        if (decrement == 1){
            val lastDigitIndex = oldPhone.indexOfLast { it.isDigit() }
            if (lastDigitIndex != 1){
                val phone = oldPhone.substring(0, lastDigitIndex) + "*" +
                        oldPhone.substring(lastDigitIndex + 1)
                oldPhone = phone
                s?.clear()
                s?.append(phone)
            } else {
                s?.clear()
                s?.append(oldPhone)
            }
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        increment = count
        decrement = before
        indexChar = start
    }

}
