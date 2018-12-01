package com.costular.ratedroid

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference
import java.util.*

object RateDroid {

    private var context: WeakReference<Context>? = null

    var debug = BuildConfig.DEBUG
    var appPackage = ""
    var countDayByDay: Boolean = true
    var launchTimes = 5
    var remindIntervalDays = 3
    var dialog: RateDroidDialog = SimpleRateDroidDialog()

    fun with(context: Context): RateDroid {
        this.context = WeakReference(context)
        appPackage = context.packageName
        return this
    }

    fun init(debug: Boolean = false): RateDroid {
        checkContext()
        val launchDate = PreferencesHelper.getInstallDate(getContext())

        if (launchDate == -1L) {
            PreferencesHelper.setInstallDate(getContext())
        } else {
            val now = Date()
            val daysPassed = DateUtils.daysPassed(Date(launchDate), now)

            if (daysPassed > 1) {
                val incrementDays = if (countDayByDay) 1 else daysPassed
                PreferencesHelper.incrementLaunchTimes(getContext(), increment = incrementDays)
            }
        }

        if (debug) {
            this.debug = true
        }

        return this
    }

    private fun checkContext() {
        requireNotNull(context) {
            "Context is required to use RateDroid. Use with(context) method to initialize library"
        }
        requireNotNull(context?.get()) {
            "Context is required to use RateDroid. Use with(context) method to initialize library"
        }
    }

    fun restore() {
        checkContext()
        PreferencesHelper.restore(getContext())
    }

    private fun getContext(): Context = context!!.get()!!

    fun showIfNeeded(activity: AppCompatActivity) {
        if(shouldShowRate()) {
            handlePackageName()
            dialog.show(activity.supportFragmentManager, null)
        }
    }

    fun showIfNeeded(fragment: Fragment) {
        if(shouldShowRate()) {
            handlePackageName()
            dialog.show(fragment.childFragmentManager, null)
        }
    }

    private fun handlePackageName() {
        if (appPackage.isNotEmpty()) {
            dialog.arguments = Bundle().apply {
                putString(RateDroidDialog.PARAM_PACKAGE, appPackage)
            }
        }
    }

    private fun shouldShowRate(): Boolean = (hasPassedLaunchTimes() && hasPassedRemindDays() && !dialogHasBeenShown()) || debug

    private fun dialogHasBeenShown(): Boolean = PreferencesHelper.dialogHasShown(getContext())

    private fun hasPassedLaunchTimes(): Boolean = PreferencesHelper.getLaunchTimes(getContext()) > launchTimes

    private fun hasPassedRemindDays(): Boolean {
        val remindTimestamp = PreferencesHelper.getRemindDate(getContext())

        if (remindTimestamp == -1L) {
            return true
        }

        val remindDate = remindTimestamp.toDate()
        val daysPassed = DateUtils.daysPassed(remindDate, Date())
        return daysPassed > remindIntervalDays
    }

    fun clickRemindLater() {
        checkContext()
        PreferencesHelper.setRemindDate(getContext())
    }

    fun clickNo() {
        checkContext()
        PreferencesHelper.setDialogHasShown(getContext(), true)
    }

    fun clickRate() {
        checkContext()
        PreferencesHelper.setDialogHasShown(getContext(), true)

        val intent = IntentHelper.generatePlayStoreIntent(appPackage)
        getContext().startActivity(intent)
    }

}