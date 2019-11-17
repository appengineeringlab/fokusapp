package com.labappengineering.pomodoro.main

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.main.timer.TimerStateContext
import com.labappengineering.pomodoro.util.Converters
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionsViewModel : SessionsViewModel

    val widgets = ArrayList<View>(3)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        setSupportActionBar(main_bap)
        main_fab.isEnabled = false


        widgets.add(main_progress_bar_circle)
        widgets.add(main_tv_time)
        widgets.add(main_fab)


        sessionsViewModel.sessionsLiveData = sessionsViewModel.getAllSessions()
        sessionsViewModel.sessionsLiveData.observe(this, Observer { sessionsList ->
            if(sessionsList != null && sessionsList.isNotEmpty()){
                sessionsViewModel.sessionLiveData.value = sessionsList[0]
            }
        })
        sessionsViewModel.sessionLiveData.observe(this, Observer { sess ->
            if(sessionsViewModel.sess != null && sessionsViewModel.sess != sess) {
                main_fab.isEnabled = false
                sessionsViewModel.sess = sess.copy()
                val res = sessionsViewModel.update(sessionsViewModel.sess!!)
                if(res == 1) {
                    updateUI(sessionsViewModel.sess!!)
                }
                Log.i("MainActivity", "Rezultat; $res")
            } else if(sessionsViewModel.sess == null) {
                sessionsViewModel.sess = sess.copy()
                updateUI(sess)
                sessionsViewModel.timerStateContext =
                    TimerStateContext(widgets, sessionsViewModel.sessionLiveData)
                main_fab.setOnClickListener {
                    sessionsViewModel.timerStateContext!!.doAction()
                }
            }
        })

    }

    private fun updateUI(session: Session) {
        main_fab.isEnabled = true
        main_tv_time.text = Converters.hmsTimeFormatter(
            Converters.minutesToMilliseconds(session.length)
        )
        main_tv_repetitions.text = "${session.currentRepetition} / ${session.repetitions}"
        main_tv_sessions.text = "${session.currentSessionPerDay} / ${session.perDay}"

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_bap_menu, menu)

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }


        return true
    }

}
