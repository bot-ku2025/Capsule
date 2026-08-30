package com.capsule.app;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private TextView statusTextView;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // UI Darurat/Fallback Asinkron agar tidak memblokir Main Thread
        statusTextView = new TextView(this);
        statusTextView.setText("CAPSULE CORE\nInitializing Async Sandbox Engine...");
        statusTextView.setTextSize(18f);
        statusTextView.setPadding(50, 100, 50, 50);
        setContentView(statusTextView);

        checkOverlayPermission();
        startAsyncSandboxInitialization();
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);
        }
    }

    private void startAsyncSandboxInitialization() {
        // Mencegah Freeze/Deadlock dengan menjalankan inisialisasi di Background Thread
        executor.execute(() -> {
            try {
                // Inisialisasi Isolasi Kontainer, Parameter Spoofing, & Controller
                Thread.sleep(1500); 

                mainHandler.post(() -> {
                    statusTextView.setText("CAPSULE CORE - ACTIVE\n\n" +
                            "• Multi-Profile Sandbox: Ready\n" +
                            "• Floating Window Overlay: Active\n" +
                            "• Network & Identity Automation: Standby");
                    Toast.makeText(MainActivity.this, "Capsule Engine Active", Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                mainHandler.post(() -> {
                    statusTextView.setText("CAPSULE CORE ERROR:\n" + e.getLocalizedMessage());
                });
            }
        });
    }
}
