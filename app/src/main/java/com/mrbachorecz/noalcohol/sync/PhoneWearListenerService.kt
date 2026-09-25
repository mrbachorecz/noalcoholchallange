package com.mrbachorecz.noalcohol.sync

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.mrbachorecz.noalcohol.shared.WearSyncContract
import com.mrbachorecz.noalcohol.storage.writeLastDrinkingDate
import java.nio.charset.StandardCharsets

/** Handles watch requests: refresh push, or set last-drink date from a reset. */
class PhoneWearListenerService : WearableListenerService() {
    override fun onMessageReceived(messageEvent: MessageEvent) {
        when (messageEvent.path) {
            WearSyncContract.PATH_LAST_DRINK_DATE -> {
                Log.d(TAG, "Watch requested date refresh")
                WearSync.syncLastDrinkingDate(this)
            }

            WearSyncContract.PATH_SET_LAST_DRINK_DATE -> {
                val date = String(messageEvent.data, StandardCharsets.UTF_8).trim()
                if (date.isNotEmpty()) {
                    Log.d(TAG, "Watch set last-drink date to $date")
                    writeLastDrinkingDate(this, date)
                }
            }
        }
    }

    companion object {
        private const val TAG = "PhoneWearListener"
    }
}
