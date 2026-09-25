package com.mrbachorecz.noalcohol.wear.sync

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.mrbachorecz.noalcohol.shared.WearSyncContract
import java.util.concurrent.Executors

object OpenPhoneApp {
    private const val TAG = "OpenPhoneApp"
    private val executor = Executors.newSingleThreadExecutor()

    fun launch(context: Context) {
        val intent = Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .setPackage(WearSyncContract.PHONE_PACKAGE)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            val helper = RemoteActivityHelper(context.applicationContext, executor)
            helper.startRemoteActivity(intent)
        } catch (e: Exception) {
            Log.w(TAG, "Could not open phone app (is a phone paired?)", e)
        }
    }
}
