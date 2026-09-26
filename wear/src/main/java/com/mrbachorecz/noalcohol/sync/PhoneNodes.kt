package com.mrbachorecz.noalcohol.sync

import android.content.Context
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Node
import com.google.android.gms.wearable.Wearable
import com.mrbachorecz.noalcohol.shared.WearSyncContract
import kotlinx.coroutines.tasks.await

/** Resolves phone nodes for Data Layer messages (capability first, then connected nodes). */
internal suspend fun resolvePhoneNodes(context: Context): Collection<Node> {
    val capabilityClient = Wearable.getCapabilityClient(context)
    val reachable = capabilityClient
        .getCapability(WearSyncContract.CAPABILITY_PHONE, CapabilityClient.FILTER_REACHABLE)
        .await()
        .nodes
    if (reachable.isNotEmpty()) return reachable

    val allCapable = capabilityClient
        .getCapability(WearSyncContract.CAPABILITY_PHONE, CapabilityClient.FILTER_ALL)
        .await()
        .nodes
    if (allCapable.isNotEmpty()) return allCapable

    // Last resort: any connected node (paired phone).
    return Wearable.getNodeClient(context).connectedNodes.await()
}
