package com.mrbachorecz.noalcohol.shared

/** Contract for phone ↔ watch sobriety date sync via the Wearable Data Layer. */
object WearSyncContract {
    const val PATH_LAST_DRINK_DATE = "/sobriety/last_drink_date"
    const val PATH_SET_LAST_DRINK_DATE = "/sobriety/set_last_drink_date"
    const val KEY_DATE = "date"
    const val CAPABILITY_PHONE = "noalcohol_phone"
    const val CAPABILITY_WEAR = "noalcohol_wear"
    /** Shared applicationId for phone and Wear (Play form factor). */
    const val APP_PACKAGE = "com.mrbachorecz.noalcohol"
}
