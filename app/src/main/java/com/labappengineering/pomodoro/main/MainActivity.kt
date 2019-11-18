package com.labappengineering.pomodoro.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle

import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuBuilder
import androidx.core.app.ShareCompat
import androidx.lifecycle.Observer
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.main.timer.ATimerState
import com.labappengineering.pomodoro.main.timer.TimerStateContext
import com.labappengineering.pomodoro.settings.SettingsActivity
import com.labappengineering.pomodoro.util.BaseViewModel
import com.labappengineering.pomodoro.util.Converters
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel : BaseViewModel<Session>

    lateinit var sessionsViewModel : SessionsViewModel
    val widgets = ArrayList<View>(3)
    private val PAYPAL_ME_URI = "https://www.paypal.me/appengineeringlab"
    private val EMAIL = "labappengineering@gmail.com"
    private val SUBJECT = "Contact from Pomodoro App"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        AndroidInjection.inject(this)

        sessionsViewModel = viewModel as SessionsViewModel


        setSupportActionBar(main_bap)
        main_fab.isEnabled = false


        widgets.add(main_progress_bar_circle)
        widgets.add(main_tv_time)
        widgets.add(main_fab)


        sessionsViewModel.sessionsLiveData = sessionsViewModel.getAllEntities()
        sessionsViewModel.sessionsLiveData.observe(this, Observer { sessionsList ->
            if(sessionsList != null && sessionsList.isNotEmpty()){
                sessionsViewModel.sessionLiveData.value = sessionsList[0]
            }
        })
        sessionsViewModel.sessionLiveData.observe(this, Observer { sess ->
            if(sessionsViewModel.session != null && sessionsViewModel.session != sess) {
                main_fab.isEnabled = false
                sessionsViewModel.session = sess.copy()
                val res = sessionsViewModel.update(sessionsViewModel.session!!)
                if(res == 1) {
                    updateUI(sessionsViewModel.session!!)
                }
                Log.i("MainActivity", "Rezultat; $res")
            } else if(sessionsViewModel.session == null) {
                sessionsViewModel.session = sess.copy()
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
        if(sessionsViewModel.timerStateContext != null) {
            sessionsViewModel.timerStateContext!!.resetProgresBarUI(
                sessionsViewModel.timerStateContext!!.currentState as ATimerState
            )
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_bap_menu, menu)

        if (menu is MenuBuilder) {
            menu.setOptionalIconsVisible(true)
        }


        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                return true
            }
            R.id.main_menu_donate -> {
                val openURL = Intent(Intent.ACTION_VIEW)
                openURL.data = Uri.parse(PAYPAL_ME_URI)
                startActivity(openURL)
                return true
            }
            R.id.main_menu_contact -> {

            ShareCompat.IntentBuilder.from(this)
                .setType("message/rfc822")
                .addEmailTo(EMAIL)
                .setSubject(SUBJECT)
                .setText("")
                //.setHtmlText(body) //If you are using HTML in your body text
                .setChooserTitle("Choose Email App")
                .startChooser()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        sessionsViewModel.sessionsLiveData = sessionsViewModel.getAllEntities()
    }

    override fun onRestart() {
        super.onRestart()
        sessionsViewModel.sessionsLiveData = sessionsViewModel.getAllEntities()
    }

}
