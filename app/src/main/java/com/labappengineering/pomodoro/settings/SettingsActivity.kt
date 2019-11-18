package com.labappengineering.pomodoro.settings

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session

import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.fragment_settings_dialog.*
import java.lang.NumberFormatException
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.text.Editable
import android.text.TextWatcher




class SettingsActivity : AppCompatActivity() {
    @Inject
    lateinit var settingsViewModel: SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        AndroidInjection.inject(this)

        settingsViewModel.sessionsLiveData = settingsViewModel.getAllSessions()
        settingsViewModel.sessionsLiveData.observe(this, Observer { sessionsList ->
            if(sessionsList != null && sessionsList.isNotEmpty()){
                settingsViewModel.sessionLiveData.value = sessionsList[0]
            }
        })
        settingsViewModel.sessionLiveData.observe(this, Observer { sess ->
            if(settingsViewModel.sess != null && settingsViewModel.sess != sess) {
                main_fab.isEnabled = false

            } else if(settingsViewModel.sess == null) {
                settingsViewModel.sess = sess.copy()
                createRecyclerView(settingsViewModel.sess!!)
            }
        })
        settings_btn_back.setOnClickListener {
           finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        settingsViewModel.sessionItemLiveData.observe(this, Observer {
//            settingsViewModel.sessionItemLiveData.value = ArrayList(it)
//            settingsViewModel.sessionLiveData.value = sessionItemListToSession(ArrayList(it), settingsViewModel.sess!!)
            if(settingsViewModel.sessionItemLiveData.value != null && settingsViewModel.sess != null) {
                val session = sessionItemListToSession(
                    ArrayList(settingsViewModel.sessionItemLiveData.value!!),
                    settingsViewModel.sess!!
                )
                settingsViewModel.update(session)
            }
        })

    }

    private fun createRecyclerView(sess: Session) {
        val settingsValues: LinkedHashMap<String, String> = LinkedHashMap(6)
        createSessionItem(sess)
        settings_rv_container.layoutManager = LinearLayoutManager(this)
        settings_rv_container.adapter = SettingsAdapter(
            settingsViewModel.sessionItemLiveData,
            this,
            { sessionItem : SessionItem -> recyclerViewItemClicked(sessionItem) } ,
            {sessionItem: SessionItem -> colorChanged(sessionItem)})
        settings_rv_container.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }


    private fun createSessionItem(sess: Session) {
        val sessionItems = sessionToSessionItemList(sess)
        settingsViewModel.sessionItemLiveData.value = sessionItems
    }
    private fun sessionToSessionItemList(sess: Session) : ArrayList<SessionItem> {
        val sessionItems: ArrayList<SessionItem> = ArrayList(6)
        sessionItems.add(SessionItem("length", sess.length.toString(), "Session Length", SessionItem.ValueType.Int))
        sessionItems.add(SessionItem("shortBreak", sess.shortBreak.toString(), "Short Break Length", SessionItem.ValueType.Int))
        sessionItems.add(SessionItem("longBreak", sess.longBreak.toString(), "Long Break Length", SessionItem.ValueType.Int))
        sessionItems.add(SessionItem("sessionColor", sess.sessionColor, "Session Timer Color", SessionItem.ValueType.String))
        sessionItems.add(SessionItem("shortBreakColor", sess.shortBreakColor, "Short Break Timer Color", SessionItem.ValueType.String))
        sessionItems.add(SessionItem("longBreakColor", sess.longBreakColor, "Long Break Timer Color", SessionItem.ValueType.String))
        sessionItems.add(SessionItem("repetitions", sess.repetitions.toString(), "Number of sessions per round", SessionItem.ValueType.Int))
        sessionItems.add(SessionItem("perDay", sess.perDay.toString(), "Number of rounds per day", SessionItem.ValueType.Int))
        return sessionItems
    }

    private fun sessionItemListToSession(sessionItemList: ArrayList<SessionItem>, sess: Session) : Session {
        return sess.copy(
            length = sessionItemList[0].value.toInt(),
            shortBreak = sessionItemList[1].value.toInt(),
            longBreak = sessionItemList[2].value.toInt(),
            sessionColor = sessionItemList[3].value,
            shortBreakColor = sessionItemList[4].value,
            longBreakColor = sessionItemList[5].value,
            repetitions = sessionItemList[6].value.toInt(),
            perDay = sessionItemList[7].value.toInt()
        )
    }
    private fun recyclerViewItemClicked(sessionItem : SessionItem ) {
//        val input = EditText(this)
//        val lp = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.MATCH_PARENT
//        )
//        input.layoutParams = lp
//        input.setText(sessionItem.value)
//        input.inputType = InputType.TYPE_CLASS_NUMBER
        val settingsDialogView = layoutInflater.inflate(R.layout.fragment_settings_dialog, null);

        val settingsDialog = MaterialAlertDialogBuilder(this)
            .setView(settingsDialogView)
            .create()
        settingsDialog!!.show()
        settingsDialogView!!.findViewById<MaterialButton>(R.id.settings_dialog_btn_cancel).setOnClickListener {
            settingsDialog!!.dismiss()
        }

        val btnConfirm = settingsDialogView!!.findViewById<MaterialButton>(R.id.settings_dialog_btn_confirm)
        val etValue =  settingsDialogView!!.findViewById<EditText>(R.id.settings_dialog_et_value)
        val tvFieldName = settingsDialogView!!.findViewById<TextView>(R.id.settings_dialog_tv_field_name)
        val til = settingsDialogView!!.findViewById<TextInputLayout>(R.id.settings_dialog_til)
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
        btnConfirm.setOnClickListener {
            val enteredText = etValue.text.toString().trim()
            if(sessionItem.valueType == SessionItem.ValueType.Int){
                val retVal = checkValueIfNumber(enteredText)
                if(retVal == null || (retVal != null && retVal == -1)){
                    til.error = "You must enter integer number."
                    til.isErrorEnabled = true
                }
                else if(retVal != null && retVal != -1){
                    val item = findItemByKey(sessionItem.key)
                    item!!.value = retVal.toString()
                    settingsDialog.dismiss()
                }
            } else if(enteredText != sessionItem.value){
                sessionItem.value = enteredText
                settingsDialog.dismiss()
            }
        }

    }
    private fun colorChanged(sessionItem: SessionItem) {
        val item = findItemByKey(sessionItem.key)
        item!!.value = sessionItem.value
    }
    private fun findItemByKey(key: String) : SessionItem? {
        for(item in settingsViewModel.sessionItemLiveData.value!!){
            if (item.key == key){
                return item
            }
        }
        return null
    }

    private fun updateUI(sess: Session?) {
    }
}
