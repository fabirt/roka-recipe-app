package com.fabirt.roka.features

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fabirt.roka.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        setContentView(R.layout.activity_main)
    }
}