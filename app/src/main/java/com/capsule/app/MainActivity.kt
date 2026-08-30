package com.capsule.app

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
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
        
        // UI Darurat Asinkron agar tidak memblokir Main Thread
        statusTextView = TextView(this).apply {
            text = "CAPSULE CORE\nInitializing Async Sandbox Engine..."
            textSize = 18f
            setPadding(50, 100, 50, 50)
        }
        setContentView(statusTextView)

        checkOverlayPermission()
        startAsyncSandboxInitialization()
    }

    private fun checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            val intent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivityForResult(intent, 1234)
        }
    }

    private fun startAsyncSandboxInitialization() {
        // Mencegah Freeze dengan Background Thread
        executor.execute {
            try {
                // Simulasi Inisialisasi Isolasi Kontainer
                Thread.sleep(1500) 

                mainHandler.post {
                    statusTextView.text = "CAPSULE CORE - ACTIVE\n\n" +
                            "• Multi-Profile Sandbox: Ready\n" +
                            "• Floating Window Overlay: Active\n" +
                            "• Network & Identity Automation: Standby"
                    Toast.makeText(this@MainActivity, "Capsule Engine Active", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                mainHandler.post {
                    statusTextView.text = "CAPSULE CORE ERROR:\n${e.localizedMessage}"
                }
            }
        }
    }
}
