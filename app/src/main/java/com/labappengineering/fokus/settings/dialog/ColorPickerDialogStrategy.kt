package com.labappengineering.fokus.settings.dialog

import android.app.Activity
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.labappengineering.fokus.settings.SessionItem
import java.lang.Exception

class ColorPickerDialogStrategy (
    activity: Activity,
    sessionItem: SessionItem
)   : BaseDialogStrategy(activity, sessionItem){
    override fun show(items: MutableLiveData<ArrayList<SessionItem>>) {
        var initialColor: Int = Color.BLACK
        try{
            initialColor = Color.parseColor(sessionItem.value)
        } catch(ex: Exception){

        }
        ColorPickerDialogBuilder
            .with(activity)
            .setTitle("Choose color")
            .initialColor(initialColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setOnColorSelectedListener { selectedColor ->
                val newValue = String.format("#%06X", 0xFFFFFF and selectedColor)
                if(newValue !=  sessionItem.value) {
                    sessionItem.value = newValue
                    updateLiveData(sessionItem, items)
                    Log.i("ColorPicker", "Picked new color: $newValue")
                }
            }
            .setPositiveButton(
                "ok"
            ) { dialog, selectedColor, allColors ->
                Log.i("ColorPicker", "Usao u positive")
                // holder.fl.setBackgroundColor(selectedColor)
                val newValue = String.format("#%06X", 0xFFFFFF and selectedColor)
                if(newValue !=  sessionItem.value) {
                    sessionItem.value = newValue
                    updateLiveData(sessionItem, items)
                    Log.i("ColorPicker", "Picked new color: $newValue")
                }
                dialog.dismiss()
            }
            .setNegativeButton(
                "cancel"
            ) { dialog, which -> }
            .build()
            .show()

    }

}