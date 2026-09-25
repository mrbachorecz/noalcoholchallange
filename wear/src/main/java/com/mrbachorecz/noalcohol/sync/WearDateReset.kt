package com.mrbachorecz.noalcohol.sync

import android.content.Context
import android.util.Log
import androidx.wear.tiles.TileService
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Wearable
import com.mrbachorecz.noalcohol.shared.WearSyncContract
import com.mrbachorecz.noalcohol.storage.writeLastDrinkingDate
import com.mrbachorecz.noalcohol.tile.DaysTileService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.nio.charset.StandardCharsets
import java.time.LocalDate

/** Writes today's date on the watch and asks the phone to adopt it. */
object WearDateReset {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private const val TAG = "WearDateReset"

    fun resetToToday(context: Context): String {
        val today = LocalDate.now().toString()
        writeLastDrinkingDate(context, today)
        TileService.getUpdater(context).requestUpdate(DaysTileService::class.java)
        pushDateToPhone(context, today)
        return today
    }

    private fun pushDateToPhone(context: Context, isoDate: String) {
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
                val payload = isoDate.toByteArray(StandardCharsets.UTF_8)
                val messageClient = Wearable.getMessageClient(appContext)
                for (node in nodes) {
                    messageClient.sendMessage(
                        node.id,
                        WearSyncContract.PATH_SET_LAST_DRINK_DATE,
                        payload
                    ).await()
                }
            } catch (e: Exception) {
                Log.w(TAG, "Could not push reset date to phone", e)
            }
        }
    }
}
