package com.labappengineering.pomodoro.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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


    }

    private fun createRecyclerView(sess: Session) {
        val settingsValues: LinkedHashMap<String, String> = LinkedHashMap(6)
        settingsValues["length"] = sess.length.toString()
        settingsValues["shortBreak"] = sess.shortBreak.toString()
        settingsValues["longBreak"] = sess.longBreak.toString()
        settingsValues["sessionColor"] = sess.sessionColor
        settingsValues["shortBreakColor"] = sess.shortBreakColor
        settingsValues["longBreakColor"] = sess.longBreakColor
        createSessionItem(sess)
        settings_rv_container.layoutManager = LinearLayoutManager(this)
        settings_rv_container.adapter = SettingsAdapter(settingsValues, this) { retMap : HashMap<String, String> -> recyclerViewItemClicked(retMap) }
        settings_rv_container.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun createSessionItem(sess: Session) {
        settingsViewModel.sessionItem = SessionItem(
            sess.length,
            sess.shortBreak,
            sess.longBreak,
            sess.sessionColor,
            sess.shortBreakColor,
            sess.longBreakColor)
        settingsViewModel.sessionItemLiveData.value = settingsViewModel.sessionItem
    }

    private fun recyclerViewItemClicked(retMap : HashMap<String, String> ) {

    }
    private fun updateUI(sess: Session?) {
    }
}
