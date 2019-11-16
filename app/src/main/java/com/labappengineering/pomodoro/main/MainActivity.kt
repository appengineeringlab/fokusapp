package com.labappengineering.pomodoro.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.labappengineering.pomodoro.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.view.menu.MenuBuilder
import android.view.animation.Animation.RELATIVE_TO_SELF
import android.view.animation.RotateAnimation
import android.os.CountDownTimer








class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_bap)


        val makeVertical = RotateAnimation(0f, -90f, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f)
        makeVertical.fillAfter = true
        main_pb.startAnimation(makeVertical)
        main_pb.setSecondaryProgress(50)
        main_pb.setProgress(0)
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
