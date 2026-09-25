package com.mrbachorecz.noalcohol.sync

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.wear.remote.interactions.RemoteActivityHelper
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.mrbachorecz.noalcohol.shared.WearSyncContract
import java.util.concurrent.Executors

object OpenPhoneApp {
    private const val TAG = "OpenPhoneApp"
    private val executor = Executors.newSingleThreadExecutor()

    fun launch(context: Context) {
        val intent = Intent(Intent.ACTION_MAIN)
            .addCategory(Intent.CATEGORY_LAUNCHER)
            .setClassName(
                WearSyncContract.APP_PACKAGE,
                WearSyncContract.PHONE_LAUNCHER_ACTIVITY
            )
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            val helper = RemoteActivityHelper(context.applicationContext, executor)
            val future = helper.startRemoteActivity(intent)
            Futures.addCallback(
                future,
                object : FutureCallback<Void> {
                    override fun onSuccess(result: Void?) {
                        Log.d(TAG, "Remote phone launch requested")
                    }

                    override fun onFailure(t: Throwable) {
                        Log.w(TAG, "Could not open phone app (is a phone paired?)", t)
                    }
                },
                executor
            )
        } catch (e: Exception) {
            Log.w(TAG, "Could not open phone app (is a phone paired?)", e)
        }
    }
}
