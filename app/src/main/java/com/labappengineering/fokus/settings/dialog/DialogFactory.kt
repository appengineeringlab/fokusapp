package com.labappengineering.fokus.settings.dialog

import android.app.Activity
import com.labappengineering.fokus.settings.SessionItem

class DialogFactory {
    enum class DialogType{
        EDIT_DIALOG,
        COLOR_PICKER_DIALOG
    }
    companion object{
        @JvmStatic
        fun factory(dialogType: DialogType,
                    activity: Activity,
                    sessionItem: SessionItem) : BaseDialogStrategy{
            return when(dialogType){
                DialogType.EDIT_DIALOG -> EditDialogStrategy(activity, sessionItem)
                DialogType.COLOR_PICKER_DIALOG  -> ColorPickerDialogStrategy(activity, sessionItem)
            }
        }
    }
}