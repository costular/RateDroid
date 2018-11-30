package com.costular.ratedroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class SimpleRateDroidDialog : RateDroidDialog() {

    override fun dialogTheme(): Int = R.style.Theme_AppCompat_Dialog

    override fun dialogLayout(): Int = R.layout.dialog_simple_rate

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(dialogLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val imageIcon = view!!.findViewById<ImageView>(R.id.imageIcon)
        val dialogTitle = view!!.findViewById<TextView>(R.id.textSimpleRateTitle)
        val dialogDescription = view!!.findViewById<TextView>(R.id.textSimpleRateDescription)

        if (packageName.isNotEmpty()) {
            val packageManager = context!!.packageManager
            val appIcon = packageManager.getApplicationIcon(packageName)
            val appName = packageManager.getApplicationLabel(packageManager.getApplicationInfo(packageName, 0))

            imageIcon.setImageDrawable(appIcon)
            dialogTitle.text = String.format(getString(R.string.dialog_simple_rate_title), appName)
            dialogDescription.text = String.format(getString(R.string.dialog_simple_rate_description), appName)
        } else {
            val appName = getString(R.string.dialog_simple_rate_app_alternative)

            imageIcon.visibility = View.GONE
            dialogTitle.text = String.format(getString(R.string.dialog_simple_rate_title), appName)
            dialogDescription.text = String.format(getString(R.string.dialog_simple_rate_description), appName)
        }
    }

}