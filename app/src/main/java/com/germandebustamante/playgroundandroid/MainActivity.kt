package com.germandebustamante.playgroundandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.time.TrustedTime
import com.google.android.gms.time.TrustedTimeClient

class MainActivity : AppCompatActivity() {

    private var trustedTimeClient: TrustedTimeClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        TrustedTime.createClient(this).addOnSuccessListener {
            trustedTimeClient = it
        }.addOnFailureListener {
            Log.e("MainActivity", "Failed to create TrustedTimeClient", it)
        }

        Log.i("MainActivity", "Current time in millis: ${getCurrentTimeInMillis()}")
    }

    private fun getCurrentTimeInMillis(): Long =
        trustedTimeClient?.computeCurrentUnixEpochMillis() ?: System.currentTimeMillis()
}