package com.mrbachorecz.noalcohol.sync

import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.mrbachorecz.noalcohol.shared.WearSyncContract

/** Handles watch requests to re-push the current last-drink date. */
class PhoneWearListenerService : WearableListenerService() {
    override fun onMessageReceived(messageEvent: MessageEvent) {
        if (messageEvent.path == WearSyncContract.PATH_LAST_DRINK_DATE) {
            Log.d(TAG, "Watch requested date refresh")
            WearSync.syncLastDrinkingDate(this)
        }
    }

    companion object {
        private const val TAG = "PhoneWearListener"
    }
}
