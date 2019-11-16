package com.labappengineering.pomodoro.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.data.Session
import com.labappengineering.pomodoro.data.source.SessionsRepository
import com.labappengineering.pomodoro.data.source.local.PomodoroDB
import com.labappengineering.pomodoro.main.MainActivity
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var splashViewModel: SplashViewModel
    lateinit var sessions : LiveData<List<Session>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        AndroidInjection.inject(this)
        animateText()
        animateButton()
        splash_mb_go.setOnClickListener{
            // On app start initialize DB if empty, if not continue to another screen.
            sessions = splashViewModel.getAllSessions()

            sessions.observe(this, Observer {
//                splashViewModel.clearDB()
                splashViewModel.initializeDB(it)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            })

        }
    }

    private fun animateButton() {
        val pushUpIn: Animation = AnimationUtils.loadAnimation(this, R.anim.push_up_in)
        splash_mb_go.startAnimation(pushUpIn)
    }

    private fun animateText() {
        val pushDownIn: Animation = AnimationUtils.loadAnimation(this, R.anim.push_down_in)
        splash_tv_welcome_to.startAnimation(pushDownIn)
        splash_tv_pomodoroapp.startAnimation(pushDownIn)
    }
}
