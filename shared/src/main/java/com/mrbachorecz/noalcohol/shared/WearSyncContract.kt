package com.mrbachorecz.noalcohol.shared

/** Contract for phone ↔ watch sobriety date sync via the Wearable Data Layer. */
object WearSyncContract {
    const val PATH_LAST_DRINK_DATE = "/sobriety/last_drink_date"
    const val KEY_DATE = "date"
    const val CAPABILITY_PHONE = "noalcohol_phone"
    const val CAPABILITY_WEAR = "noalcohol_wear"
    const val PHONE_PACKAGE = "com.mrbachorecz.noalcohol"
}
