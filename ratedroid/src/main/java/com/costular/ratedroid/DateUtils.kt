package com.costular.ratedroid

import java.util.*



object DateUtils {

    fun daysPassed(from: Date, now: Date): Int {
        val difference = now.time - from.time
        val differenceDays = difference / (1000 * 60 * 60 * 24)
        return differenceDays.toInt()
    }

}