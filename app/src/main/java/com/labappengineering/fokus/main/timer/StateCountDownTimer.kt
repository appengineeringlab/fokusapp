package com.labappengineering.fokus.main.timer

import android.os.CountDownTimer
import com.labappengineering.fokus.util.Converters

data class StateCountDownTimer(
    var timeCountInMilliSeconds: Long = Converters.minutesToMilliseconds(1),
    var countDownTimer: CountDownTimer? = null
)