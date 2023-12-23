package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nmpubaya.cerbung.databinding.ActivityReadCerbungBinding

class ReadCerbungActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadCerbungBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadCerbungBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cerbung_id = intent.getIntExtra(CerbungAdapter.KEY_CERBUNG_ID, 0)
    }
}