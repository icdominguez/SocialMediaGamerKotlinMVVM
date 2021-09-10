package com.icdominguez.socialmediagamerkotlin.common

import android.app.Application
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

class RelativeTime : Application() {

    private val SECOND_MILLIS = 1000
    private val MINUTE_MILLIS = 60 * SECOND_MILLIS
    private val HOUR_MILLIS = 60 * MINUTE_MILLIS
    private val DAY_MILLIS = 24 * HOUR_MILLIS


    fun getTimeAgo(time: Long, ctx: Context?): String? {
        var time = time
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return "Hace un momento"
        }

        // TODO: localize
        val diff = now - time
        return when {
            diff < MINUTE_MILLIS -> {
                "Hace un momento"
            }
            diff < 2 * MINUTE_MILLIS -> {
                "Hace un minuto"
            }
            diff < 50 * MINUTE_MILLIS -> {
                "Hace " + diff / MINUTE_MILLIS + " minutos"
            }
            diff < 90 * MINUTE_MILLIS -> {
                "Hace una hora"
            }
            diff < 24 * HOUR_MILLIS -> {
                "Hace " + diff / HOUR_MILLIS + " horas"
            }
            diff < 48 * HOUR_MILLIS -> {
                "Ayer"
            }
            else -> {
                "Hace " + diff / DAY_MILLIS + " dias"
            }
        }
    }

    fun timeFormatAMPM(time: Long, ctx: Context?): String? {
        var time = time
        val formatter = SimpleDateFormat("hh:mm a")
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000
        }
        val now = System.currentTimeMillis()
        if (time > now || time <= 0) {
            return formatter.format(Date(time))
        }
        val diff = now - time
        return when {
            diff < 24 * HOUR_MILLIS -> {
                formatter.format(Date(time))
            }
            diff < 48 * HOUR_MILLIS -> {
                "Ayer"
            }
            else -> {
                "Hace " + diff / DAY_MILLIS + " dias"
            }
        }
    }

}