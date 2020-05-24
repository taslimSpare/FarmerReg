package com.amazon.app.farmerreg.helper


import android.text.InputType
import android.widget.EditText

class Utils {



    fun errorfy(vararg edittexts: EditText) : Boolean {

        var res = false

        edittexts.forEach {
            if(it.text.toString().trim().equals("")) {
                it.error = Constants.REQUIRED_FIELD
                if((it.inputType == InputType.TYPE_TEXT_VARIATION_PASSWORD) && (it.text.length < 6)) it.error = Constants.INVALID_PASSWORD
                res = true
            }
        }

        return res
    }

}