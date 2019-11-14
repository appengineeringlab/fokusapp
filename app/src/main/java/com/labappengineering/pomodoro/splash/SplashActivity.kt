package com.labappengineering.pomodoro.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.labappengineering.pomodoro.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animateText()
        animateButton()
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
