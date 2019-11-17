package com.labappengineering.pomodoro.settings

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session

import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_settings.*
import javax.inject.Inject
import androidx.recyclerview.widget.DividerItemDecoration



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
//        settingsViewModel.sessionItemLiveData.observe(this, Observer {
//            settingsViewModel.sessionItemLiveData.value = ArrayList(it)
//            settingsViewModel.sessionLiveData.value = sessionItemListToSession(ArrayList(it), settingsViewModel.sess!!)
//        })

    }

    private fun createRecyclerView(sess: Session) {
        val settingsValues: LinkedHashMap<String, String> = LinkedHashMap(6)
        createSessionItem(sess)
        settings_rv_container.layoutManager = LinearLayoutManager(this)
        settings_rv_container.adapter = SettingsAdapter(settingsViewModel.sessionItemLiveData, this) { sessionItem : SessionItem -> recyclerViewItemClicked(sessionItem) }
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
        sessionItems.add(SessionItem("length", sess.length.toString(), "Session Length"))
        sessionItems.add(SessionItem("shortBreak", sess.shortBreak.toString(), "Short Break Length"))
        sessionItems.add(SessionItem("longBreak", sess.longBreak.toString(), "Long Break Length"))
        sessionItems.add(SessionItem("sessionColor", sess.sessionColor, "Session Timer Color"))
        sessionItems.add(SessionItem("shortBreakColor", sess.shortBreakColor, "Short Break Timer Color"))
        sessionItems.add(SessionItem("longBreakColor", sess.longBreakColor, "Long Break Timer Color"))
        return sessionItems
    }

    private fun sessionItemListToSession(sessionItemList: ArrayList<SessionItem>, sess: Session) : Session {
        return sess.copy(
            length = sessionItemList[0].value.toInt(),
            shortBreak = sessionItemList[1].value.toInt(),
            longBreak = sessionItemList[2].value.toInt(),
            sessionColor = sessionItemList[3].value,
            shortBreakColor = sessionItemList[4].value,
            longBreakColor = sessionItemList[5].value
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
////        MaterialAlertDialogBuilder(this)
////            .setTitle("${sessionItem.name}")
////            .setMessage("${sessionItem.name}: ${sessionItem.value}")
////            .setView(input)
////            .setPositiveButton("Ok"
////            ) { dialog, id ->
////                Log.i("SettingsActivity", "${input.text}")
////            }
////            .show()
        val fm = supportFragmentManager
        val custom = SettingsDialog()
        custom.show(fm, "")
    }
    private fun updateUI(sess: Session?) {
    }
}
