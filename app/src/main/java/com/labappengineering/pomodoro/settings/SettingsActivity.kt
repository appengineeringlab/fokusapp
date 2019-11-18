package com.labappengineering.pomodoro.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
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
import java.lang.NumberFormatException
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.labappengineering.pomodoro.settings.dialog.BaseDialogStrategy
import com.labappengineering.pomodoro.settings.dialog.ColorPickerDialogStrategy
import com.labappengineering.pomodoro.settings.dialog.DialogFactory
import com.labappengineering.pomodoro.settings.dialog.EditDialogStrategy
import com.labappengineering.pomodoro.util.BaseViewModel


class SettingsActivity : AppCompatActivity() {
    @Inject
    lateinit var viewModel: BaseViewModel<Session>
    lateinit var settingsViewModel: SettingsViewModel

    private var dialogStrategy : BaseDialogStrategy? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        AndroidInjection.inject(this)
        settingsViewModel = viewModel as SettingsViewModel

        settingsViewModel.sessionsLiveData = settingsViewModel.getAllEntities()
        settingsViewModel.sessionsLiveData.observe(this, Observer { sessionsList ->
            if(sessionsList != null && sessionsList.isNotEmpty()){
                settingsViewModel.sessionLiveData.value = sessionsList[0]
            }
        })
        settingsViewModel.sessionLiveData.observe(this, Observer { sess ->
            if(settingsViewModel.session != null && settingsViewModel.session != sess) {
                settingsViewModel.session = sess.copy()
                val itemList = sessionToSessionItemList(sess)
                settingsViewModel.sessionItemLiveData.value = itemList

            } else if(settingsViewModel.session == null) {
                settingsViewModel.session = sess.copy()
                createRecyclerView(settingsViewModel.session!!)
            }
        })
        settings_btn_back.setOnClickListener {
           finish()
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }
        settingsViewModel.sessionItemLiveData.observe(this, Observer {
            settingsViewModel.update(sessionItemListToSession(it, settingsViewModel.session!!))
            Log.i("SettingsActivity", "Notifying ...")
        })

    }
    infix fun <T> Collection<T>.sameContentWith(collection: Collection<T>?)
            = collection?.let { this.size == it.size && this.containsAll(it) }
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
        dialogStrategy = DialogFactory.factory(
            DialogFactory.DialogType.EDIT_DIALOG,
            this,
            sessionItem)
        (dialogStrategy as EditDialogStrategy)
            .show(settingsViewModel.sessionItemLiveData)
    }
    private fun colorChanged(sessionItem: SessionItem) {
        dialogStrategy = DialogFactory.factory(
            DialogFactory.DialogType.COLOR_PICKER_DIALOG,
            this,
            sessionItem)
        (dialogStrategy as ColorPickerDialogStrategy)
            .show(settingsViewModel.sessionItemLiveData)
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
