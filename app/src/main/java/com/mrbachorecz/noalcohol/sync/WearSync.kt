package com.mrbachorecz.noalcohol.sync

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import com.mrbachorecz.noalcohol.shared.WearSyncContract
import com.mrbachorecz.noalcohol.storage.readLastDrinkingDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

object WearSync {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private const val TAG = "WearSync"

    fun syncLastDrinkingDate(context: Context, date: String = readLastDrinkingDate(context)) {
        val appContext = context.applicationContext
        scope.launch {
            try {
                val request = PutDataMapRequest.create(WearSyncContract.PATH_LAST_DRINK_DATE).apply {
                    dataMap.putString(WearSyncContract.KEY_DATE, date)
                    // Ensure an update is delivered even when the value is unchanged.
                    dataMap.putLong("updatedAt", System.currentTimeMillis())
                }.asPutDataRequest().setUrgent()

                Wearable.getDataClient(appContext).putDataItem(request).await()
            } catch (e: Exception) {
                Log.w(TAG, "Failed to sync last drinking date to wear", e)
            }
        }
    }
}
