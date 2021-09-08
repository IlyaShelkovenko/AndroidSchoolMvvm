/**
 * Created by Ilia Shelkovenko on 05.09.2021.
 */

package com.gmail.hostov47.androidschoolmvvm.extensions

import android.text.Editable
import android.widget.EditText
import androidx.core.widget.addTextChangedListener

fun EditText.afterTextChanged(action: (s: Editable?) -> Unit) =
    addTextChangedListener(afterTextChanged = action)
