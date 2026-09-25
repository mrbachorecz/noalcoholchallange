package com.mrbachorecz.noalcohol.wear.sync

import android.util.Log
import androidx.wear.tiles.TileService
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.mrbachorecz.noalcohol.shared.WearSyncContract
import com.mrbachorecz.noalcohol.wear.storage.writeLastDrinkingDate
import com.mrbachorecz.noalcohol.wear.tile.DaysTileService

class WearDataListenerService : WearableListenerService() {

    override fun onDataChanged(dataEvents: DataEventBuffer) {
        try {
            for (event in dataEvents) {
                if (event.type != DataEvent.TYPE_CHANGED &&
                    event.type != DataEvent.TYPE_DELETED
                ) {
                    continue
                }
                val path = event.dataItem.uri.path ?: continue
                if (path != WearSyncContract.PATH_LAST_DRINK_DATE) continue

                val date = if (event.type == DataEvent.TYPE_DELETED) {
                    ""
                } else {
                    DataMapItem.fromDataItem(event.dataItem)
                        .dataMap
                        .getString(WearSyncContract.KEY_DATE)
                        ?: ""
                }
                writeLastDrinkingDate(this, date)
                TileService.getUpdater(this).requestUpdate(DaysTileService::class.java)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Failed to handle data change", e)
        } finally {
            dataEvents.release()
        }
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {
        // Phone-initiated pushes use DataItems; messages are unused on wear for now.
        if (messageEvent.path == WearSyncContract.PATH_LAST_DRINK_DATE) {
            Log.d(TAG, "Received refresh-related message from phone")
        }
    }

    companion object {
        private const val TAG = "WearDataListener"
    }
}
