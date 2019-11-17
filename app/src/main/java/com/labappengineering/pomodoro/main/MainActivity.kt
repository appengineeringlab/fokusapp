package com.labappengineering.pomodoro.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.view.menu.MenuBuilder
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.main.timer.StartedState
import com.labappengineering.pomodoro.main.timer.StoppedState
import com.labappengineering.pomodoro.main.timer.TimerStateContext
import com.labappengineering.pomodoro.util.Converters
import dagger.android.AndroidInjection
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sessionsViewModel : SessionsViewModel

    private lateinit var sessions: LiveData<List<Session>>
    private var sessionLiveData: MutableLiveData<Session> = MutableLiveData<Session>()
    private lateinit var session: Session
    val widgets = ArrayList<View>(3)
    var timerStateContext: TimerStateContext? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        setSupportActionBar(main_bap)
        main_fab.isEnabled = false


        widgets.add(main_progress_bar_circle)
        widgets.add(main_tv_time)
        widgets.add(main_fab)


        sessions = sessionsViewModel.getAllSessions()
        sessions.observe(this, Observer { sessionsList ->
            if(sessionsList != null && sessionsList.isNotEmpty()){
                sessionLiveData.value = sessionsList[0]
            }
        })
        sessionLiveData.observe(this, Observer { sess ->
            session = sess
            updateUI(sess)
            timerStateContext = TimerStateContext(widgets, session)
            main_fab.setOnClickListener {
                timerStateContext!!.doAction()
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
