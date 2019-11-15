package com.labappengineering.pomodoro.splash

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.labappengineering.pomodoro.R
import com.labappengineering.pomodoro.main.MainActivity
import kotlinx.android.synthetic.main.activity_splash.*
import android.os.Build



class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        animateText()
        animateButton()
        splash_mb_go.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
//            if (Build.VERSION.SDK_INT > 20) {
//                val options = ActivityOptions.makeSceneTransitionAnimation(this)
//                startActivity(intent, options.toBundle())
//            } else {
//                startActivity(intent)
//            }
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)

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
