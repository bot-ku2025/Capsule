package com.capsule.core.v2

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class CapsuleDeviceAdminReceiver : DeviceAdminReceiver() {
    override fun onProfileProvisioningComplete(context: Context, intent: Intent) {
        super.onProfileProvisioningComplete(context, intent)
        Toast.makeText(context, "Sandbox Profile Berhasil Dibuat!", Toast.LENGTH_LONG).show()
    }
}
