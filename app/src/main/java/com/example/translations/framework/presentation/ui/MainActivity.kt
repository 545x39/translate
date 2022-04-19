package com.example.translations.framework.presentation.ui

import android.Manifest.permission.INTERNET
import android.content.pm.PackageManager.PERMISSION_GRANTED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.translations.R
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (ContextCompat.checkSelfPermission(this, INTERNET) != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(INTERNET), 0)
        }
    }

    fun showSnackBar(message: String?) {
        message?.let {
            Snackbar.make(findViewById(R.id.root), it, Snackbar.LENGTH_LONG).show()
        }
    }
}