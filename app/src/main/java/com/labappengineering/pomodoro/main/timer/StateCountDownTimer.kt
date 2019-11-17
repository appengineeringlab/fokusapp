package com.labappengineering.pomodoro.main.timer

import android.os.CountDownTimer

data class StateCountDownTimer(
    var timeCountInMilliSeconds: Long = (1 * 60000).toLong(),
    var countDownTimer: CountDownTimer? = null
)