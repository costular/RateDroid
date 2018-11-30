package com.costular.ratedroid

import android.content.Context
import android.content.SharedPreferences
import java.util.*

object PreferencesHelper {

    private const val PREF_NAME = "ratedroidprefs"

    private const val PREF_INSTALL_DATE = "install_date"
    private const val PREF_LAUNCH_TIMES = "launch_times"
    private const val PREF_REMINDER_INTERVAL_DATE = "reminder_interval_date"
    private const val PREF_DIALOG_HAS_SHOWN = "dialog_has_shown"

    fun preferences(context: Context): SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun setInstallDate(context: Context) {
        preferences(context)
            .edit()
            .putLong(PREF_INSTALL_DATE, Date().time)
            .apply()
    }

    fun getInstallDate(context: Context): Long = preferences(context).getLong(PREF_INSTALL_DATE, -1)

    fun getLaunchTimes(context: Context): Int = preferences(context).getInt(PREF_LAUNCH_TIMES, -1)

    fun incrementLaunchTimes(context: Context, increment: Int = 1) {
        val launchTimes = getLaunchTimes(context)

        preferences(context)
            .edit()
            .putInt(PREF_LAUNCH_TIMES, launchTimes + increment)
            .apply()
    }

    fun getRemindDate(context: Context): Long = preferences(context).getLong(PREF_REMINDER_INTERVAL_DATE, -1)

    fun setRemindDate(context: Context) {
        preferences(context)
            .edit()
            .putLong(PREF_REMINDER_INTERVAL_DATE, Date().time)
            .apply()
    }

    fun dialogHasShown(context: Context): Boolean = preferences(context).getBoolean(PREF_DIALOG_HAS_SHOWN, false)

    fun setDialogHasShown(context: Context, value: Boolean) {
        preferences(context)
            .edit()
            .putBoolean(PREF_DIALOG_HAS_SHOWN, value)
            .apply()
    }

    fun restore(context: Context) {
        preferences(context)
            .edit()
            .remove(PREF_INSTALL_DATE)
            .remove(PREF_LAUNCH_TIMES)
            .remove(PREF_REMINDER_INTERVAL_DATE)
            .apply()
    }

}