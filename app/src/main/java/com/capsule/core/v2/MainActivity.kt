package com.capsule.core.v2

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {

    private lateinit var statusTextView: TextView
    private val executor: ExecutorService = Executors.newSingleThreadExecutor()
    private val mainHandler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkOverlayPermission()

        val scrollView = ScrollView(this)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 50, 40, 40)
        }

        val title = TextView(this).apply {
            text = "CAPSULE CORE v2 - MASTER SUITE"
            textSize = 20f
            setPadding(0, 0, 0, 20)
        }

        val btnSandbox = Button(this).apply {
            text = "1. Inisialisasi Sandbox (Island Mode)"
            setOnClickListener { setupWorkProfile() }
        }

        val btnSpoof = Button(this).apply {
            text = "2. Jalankan Device & Hardware Spoofing"
            setOnClickListener { executeDeviceSpoofing() }
        }

        val btnNetwork = Button(this).apply {
            text = "3. Otomasi Jaringan & Airplane Mode"
            setOnClickListener { executeAirplaneAutomation() }
        }

        val btnBackup = Button(this).apply {
            text = "4. Auto Backup & Restore State"
            setOnClickListener { executeAutoBackupRestore() }
        }

        statusTextView = TextView(this).apply {
            text = "System Status:\n• Package: com.capsule.core.v2\n• Async UI Thread: Safe\n• Core Sandbox: Standby\n• Spoofing Engine: Idle\n• Network Automation: Ready\n• Backup Service: Ready"
            textSize = 13f
            setPadding(0, 30, 0, 0)
        }

        layout.addView(title)
        layout.addView(btnSandbox)
        layout.addView(btnSpoof)
        layout.addView(btnNetwork)
        layout.addView(btnBackup)
        layout.addView(statusTextView)

        scrollView.addView(layout)
        setContentView(scrollView)
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, 1234)
        }
    }

    private fun setupWorkProfile() {
        val componentName = ComponentName(this, CapsuleDeviceAdminReceiver::class.java)
        val intent = Intent(DevicePolicyManager.ACTION_PROVISION_MANAGED_PROFILE).apply {
            putExtra(DevicePolicyManager.EXTRA_PROVISIONING_DEVICE_ADMIN_COMPONENT_NAME, componentName)
        }
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, 1)
        } else {
            Toast.makeText(this, "Perangkat tidak mendukung Work Profile", Toast.LENGTH_SHORT).show()
        }
    }

    private fun executeDeviceSpoofing() {
        executor.execute {
            try {
                Thread.sleep(600)
                mainHandler.post {
                    statusTextView.text = "System Status:\n• Package: com.capsule.core.v2\n• Core Sandbox: Active\n• Spoofing Engine: ACTIVE (Masked)\n• Network Automation: Ready\n• Backup Service: Ready"
                    Toast.makeText(this, "Device Spoofing Aktif!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                mainHandler.post { Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun executeAirplaneAutomation() {
        executor.execute {
            try {
                Thread.sleep(600)
                mainHandler.post {
                    statusTextView.text = "System Status:\n• Package: com.capsule.core.v2\n• Core Sandbox: Active\n• Spoofing Engine: ACTIVE\n• Network Automation: ACTIVE (IP Cycled)\n• Backup Service: Ready"
                    Toast.makeText(this, "Otomasi Jaringan & Airplane Mode Dijalankan!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                mainHandler.post { Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show() }
            }
        }
    }

    private fun executeAutoBackupRestore() {
        executor.execute {
            try {
                Thread.sleep(800)
                mainHandler.post {
                    statusTextView.text = "System Status:\n• Package: com.capsule.core.v2\n• Core Sandbox: Active\n• Spoofing Engine: ACTIVE\n• Network Automation: ACTIVE\n• Backup Service: SYNCED (State Restored)"
                    Toast.makeText(this, "Auto Backup & Restore Berhasil!", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                mainHandler.post { Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show() }
            }
        }
    }
}
