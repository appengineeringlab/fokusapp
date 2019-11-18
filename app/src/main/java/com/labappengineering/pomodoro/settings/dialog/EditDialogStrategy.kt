package com.labappengineering.pomodoro.settings.dialog

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.settings.SessionItem
import java.lang.NumberFormatException

class EditDialogStrategy(
    activity: Activity,
    sessionItem: SessionItem
)   : BaseDialogStrategy(activity, sessionItem) {
    override fun show(items: MutableLiveData<ArrayList<SessionItem>>) {
        val settingsDialogView = activity.layoutInflater.inflate(R.layout.fragment_settings_dialog, null)
        val settingsDialog = MaterialAlertDialogBuilder(activity)
            .setView(settingsDialogView)
            .create()
        programaticallyPopulateViewBehaviour(settingsDialog, settingsDialogView, items)
        settingsDialog.show()
    }

    private fun programaticallyPopulateViewBehaviour(
        settingsDialog: AlertDialog,
        settingsDialogView: View,
        items: MutableLiveData<ArrayList<SessionItem>>) {
        // Find all items
        val btnCancel = settingsDialogView!!.findViewById<MaterialButton>(R.id.settings_dialog_btn_cancel)
        val btnConfirm = settingsDialogView!!.findViewById<MaterialButton>(R.id.settings_dialog_btn_confirm)
        val etValue =  settingsDialogView!!.findViewById<EditText>(R.id.settings_dialog_et_value)
        val tvFieldName = settingsDialogView!!.findViewById<TextView>(R.id.settings_dialog_tv_field_name)
        val til = settingsDialogView!!.findViewById<TextInputLayout>(R.id.settings_dialog_til)

        // prepare fields and funtctions for editing
        til.error = ""
        til.isErrorEnabled = false
        tvFieldName.text = sessionItem.name
        etValue.setText(sessionItem.value)
        btnConfirm.isEnabled = false

        fun checkValueIfNumber(newValue: String): Int?{
            return try{
                val number = newValue.toInt()
                if(number != sessionItem.value.toInt()){
                    number
                } else {
                    -1 // the same
                }
            }catch (ex: NumberFormatException){
                null
            }
        }

        etValue.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }
            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                val retVal = checkValueIfNumber(s.toString().trim())
                if(retVal == null || (retVal != null && retVal == -1)){
                    btnConfirm.isEnabled = false
                }
                else if(retVal != null && retVal != -1){
                    btnConfirm.isEnabled = true
                }
            }
        })

        // add button actions
        btnConfirm.setOnClickListener {
            val enteredText = etValue.text.toString().trim()
            if(sessionItem.valueType == SessionItem.ValueType.Int){
                val retVal = checkValueIfNumber(enteredText)
                if(retVal == null || (retVal != null && retVal == -1)){
                    til.error = "You must enter integer number."
                    til.isErrorEnabled = true
                }
                else if(retVal != null && retVal != -1){
                    sessionItem.value = retVal.toString()
                    updateLiveData(sessionItem, items)
                    settingsDialog.dismiss()
                }
            } else if(enteredText != sessionItem.value){
                sessionItem.value = enteredText
                settingsDialog.dismiss()
            }
        }
        btnCancel.setOnClickListener {
            settingsDialog!!.dismiss()
        }
    }


}