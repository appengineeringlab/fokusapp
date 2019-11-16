package com.labappengineering.pomodoro.util

import java.util.concurrent.TimeUnit

class Converters {
    /**
     * method to convert millisecond to time format
     *
     * @param milliSeconds
     * @return HH:mm:ss time formatted string
     */
    companion object{
        @JvmStatic
        fun hmsTimeFormatter(milliSeconds: Long): String {

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

        @JvmStatic
        fun minutesToMilliseconds(time: Int) : Long{
            return (time * 60000).toLong()
        }
    }
}