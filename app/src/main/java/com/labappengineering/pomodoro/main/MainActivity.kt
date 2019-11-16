package com.labappengineering.pomodoro.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import androidx.appcompat.view.menu.MenuBuilder
import android.os.CountDownTimer
import android.widget.Toast
import com.labappengineering.pomodoro.R
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private var timeCountInMilliSeconds = (1 * 60000).toLong()

    private enum class TimerStatus {
        STARTED,
        STOPPED
    }

    private var timerStatus = TimerStatus.STOPPED

    private var countDownTimer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(main_bap)

        main_fab.setOnClickListener {
            startStop()
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


    /**
     * method to reset count down timer
     */
    private fun reset() {
        stopCountDownTimer()
        startCountDownTimer()
    }


    /**
     * method to start and stop count down timer
     */
    private fun startStop() {
        if (timerStatus === TimerStatus.STOPPED) {

            // call to initialize the timer values
            setTimerValues()
            // call to initialize the progress bar values
            setProgressBarValues()
            // showing the reset icon
            // TODO: Change fab state
            main_et_minute.isEnabled = false
            // changing the timer status to started
            timerStatus = TimerStatus.STARTED
            // call to start the count down timer
            startCountDownTimer()

        } else {

            // TODO: Change fab state
            // making edit text editable
            main_et_minute.isEnabled = true
            // changing the timer status to stopped
            timerStatus = TimerStatus.STOPPED
            stopCountDownTimer()

        }

    }

    /**
     * method to initialize the values for count down timer
     */
    private fun setTimerValues() {
        var time = 0
        if (!main_et_minute.text.toString().isEmpty()) {
            // fetching value from edit text and type cast to integer
            time = Integer.parseInt(main_et_minute.text.toString().trim())
        } else {
            // toast message to fill edit text
            Toast.makeText(
                applicationContext,
                "min",
                Toast.LENGTH_LONG
            ).show()
        }
        // assigning values after converting to milliseconds
        timeCountInMilliSeconds = (time * 60 * 1000).toLong()
    }

    /**
     * method to start count down timer
     */
    private fun startCountDownTimer() {

        countDownTimer = object : CountDownTimer(timeCountInMilliSeconds, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                main_tv_time.text = hmsTimeFormatter(millisUntilFinished)

                main_progress_bar_circle.progress = (millisUntilFinished / 1000).toInt()

            }

            override fun onFinish() {

                main_tv_time.setText(hmsTimeFormatter(timeCountInMilliSeconds))
                // call to initialize the progress bar values
                setProgressBarValues()
                // TODO:
                // hiding the reset icon
                // changing stop icon to start icon
                // making edit text editable
                main_et_minute.isEnabled = true
                // changing the timer status to stopped
                timerStatus = TimerStatus.STOPPED
            }

        }.start()
        countDownTimer!!.start()
    }

    /**
     * method to stop count down timer
     */
    private fun stopCountDownTimer() {
        countDownTimer!!.cancel()
    }

    /**
     * method to set circular progress bar values
     */
    private fun setProgressBarValues() {

        main_progress_bar_circle.max = (timeCountInMilliSeconds / 1000L).toInt()
        main_progress_bar_circle.progress = (timeCountInMilliSeconds / 1000L).toInt()
    }


    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    private fun hmsTimeFormatter(milliSeconds: Long): String {

        return String.format(
            "%02d:%02d:%02d",
            TimeUnit.MILLISECONDS.toHours(milliSeconds),
            TimeUnit.MILLISECONDS.toMinutes(milliSeconds) - TimeUnit.HOURS.toMinutes(
                TimeUnit.MILLISECONDS.toHours(
                    milliSeconds
                )
            ),
            TimeUnit.MILLISECONDS.toSeconds(milliSeconds) - TimeUnit.MINUTES.toSeconds(
                TimeUnit.MILLISECONDS.toMinutes(
                    milliSeconds
                )
            )
        )


    }


}
