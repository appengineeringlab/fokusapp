package com.labappengineering.pomodoro.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.labappengineering.pomodoro.R
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.view.menu.MenuBuilder




class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_bap)
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
