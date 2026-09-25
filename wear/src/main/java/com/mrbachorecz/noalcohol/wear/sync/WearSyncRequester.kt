package com.mrbachorecz.noalcohol.wear.sync

import android.content.Context
import android.util.Log
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.Wearable
import com.mrbachorecz.noalcohol.shared.WearSyncContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

/** Asks the phone to re-push the last-drink date. */
object WearSyncRequester {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private const val TAG = "WearSyncRequester"

    fun requestRefresh(context: Context) {
        val appContext = context.applicationContext
        scope.launch {
            try {
                val nodes = Wearable.getCapabilityClient(appContext)
                    .getCapability(
                        WearSyncContract.CAPABILITY_PHONE,
                        CapabilityClient.FILTER_REACHABLE
                    )
                    .await()
                    .nodes
                val messageClient: MessageClient = Wearable.getMessageClient(appContext)
                for (node in nodes) {
                    messageClient.sendMessage(
                        node.id,
                        WearSyncContract.PATH_LAST_DRINK_DATE,
                        ByteArray(0)
                    ).await()
                }
            } catch (e: Exception) {
                Log.w(TAG, "Could not request refresh from phone", e)
            }
        }
    }
}
