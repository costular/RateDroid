package com.costular.ratedroid

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import java.lang.ref.WeakReference
import java.util.*

object RateDroid {

    private var context: WeakReference<Context>? = null

    private var appsPackage = ""
    var countDayByDay: Boolean = true
    var launchTimes = 5
    var remindIntervalDays = 3
    var dialog: RateDroidDialog = SimpleRateDroidDialog()

    fun with(context: Context): RateDroid {
        this.context = WeakReference(context)
        appsPackage = context.packageName
        return this
    }

    fun init() {
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
        if (appsPackage.isNotEmpty()) {
            dialog.arguments = Bundle().apply {
                putString(RateDroidDialog.PARAM_PACKAGE, appsPackage)
            }
        }
    }

    fun shouldShowRate(): Boolean = (hasPassedLaunchTimes() && hasPassedRemindDays()) || BuildConfig.DEBUG

    fun hasPassedLaunchTimes(): Boolean = PreferencesHelper.getLaunchTimes(getContext()) > launchTimes

    fun hasPassedRemindDays(): Boolean {
        val remindTimestamp = PreferencesHelper.getRemindDate(getContext())

        if (remindTimestamp == -1L) {
            return true
        }

        val remindDate = remindTimestamp.toDate()
        val daysPassed = DateUtils.daysPassed(remindDate, Date())
        return daysPassed > remindIntervalDays
    }

}