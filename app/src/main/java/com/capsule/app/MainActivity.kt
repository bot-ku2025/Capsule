package com.capsule.app

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 100, 50, 50)
        }

        val title = TextView(this).apply {
            text = "CAPSULE CORE"
            textSize = 24f
            setPadding(0, 0, 0, 50)
        }

        val btnCreateSandbox = Button(this).apply {
            text = "BUAT SANDBOX (ISLAND MODE)"
            setOnClickListener { setupWorkProfile() }
        }

        layout.addView(title)
        layout.addView(btnCreateSandbox)
        setContentView(layout)
    }

    private fun setupWorkProfile() {
        val componentName = ComponentName(this, CapsuleDeviceAdminReceiver::class.java)
        val intent = Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE).apply {
            putExtra(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME, componentName)
        }

        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 1)
        } else {
            Toast.makeText(this, "Perangkat ini tidak mendukung fitur Sandbox/Work Profile", Toast.LENGTH_LONG).show()
        }
    }
}
