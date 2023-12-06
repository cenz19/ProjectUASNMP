package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.nmpubaya.cerbung.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {
    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RECEIVE DATA FROM LOGIN
        val username = intent.getStringExtra(CerbungAdapters.CARD_AUTHOR)

        val lm: LinearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = lm
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = CerbungAdapters()



        binding.btnCreate.setOnClickListener {
            intent = Intent(this, CreatePage::class.java)
            intent.putExtra(CerbungAdapters.CARD_AUTHOR, username)
            startActivity(intent)
        }
    }
}