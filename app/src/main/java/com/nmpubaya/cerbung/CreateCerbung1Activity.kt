package com.nmpubaya.cerbung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nmpubaya.cerbung.databinding.ActivityCreateCerbung1Binding
import com.nmpubaya.cerbung.databinding.CardPlaylistBinding

class CreateCerbung1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCerbung1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCerbung1Binding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}