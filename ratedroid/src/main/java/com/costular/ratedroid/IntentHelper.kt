package com.costular.ratedroid

import android.content.Intent
import android.net.Uri

object IntentHelper {

    private const val GOOGLE_PLAY = "https://play.google.com/store/apps/details?id="

    fun generatePlayStoreIntent(packageName: String): Intent {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY + packageName))
        return intent
    }


}