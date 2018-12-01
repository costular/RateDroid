package com.costular.ratedroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

abstract class RateDroidDialog : DialogFragment() {

    companion object {
        const val PARAM_PACKAGE = "param_package"
    }

    abstract fun dialogTheme(): Int
    abstract fun dialogLayout(): Int

    internal var packageName: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(dialogLayout(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        packageName = arguments?.getString(PARAM_PACKAGE, "") ?: ""
    }

}