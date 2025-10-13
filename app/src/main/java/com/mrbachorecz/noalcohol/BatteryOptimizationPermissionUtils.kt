package com.mrbachorecz.noalcohol


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.PowerManager
import android.provider.Settings
import androidx.core.net.toUri

object BatteryOptimizationPermissionUtils {
    /**
     * Checks if the app is already ignoring battery optimizations.
     *
     * @param context The application context.
     * @return `true` if the app is ignoring battery optimizations, `false` otherwise.
     */
    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return powerManager.isIgnoringBatteryOptimizations(context.packageName)
    }

    /**
     * If the app is not already ignoring battery optimizations, this function
     * creates and launches an intent to the system settings screen where the
     * user can manually grant this permission.
     *
     * It's highly recommended to show a rationale dialog to the user before calling this.
     *
     * @param context The activity context to launch the intent.
     */
    @SuppressLint("BatteryLife")
    fun requestIgnoreBatteryOptimizations(context: Context) {
        // The permission is not requested at runtime, but through a system intent.
        // This is the intent that opens the system dialog for the user.
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", context.packageName, null)
        }
        context.startActivity(intent)
    }
}