package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nmpubaya.cerbung.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsernameLogin.text.toString()
            val password = binding.txtPasswordLogin.text.toString()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
//
//        binding.btnCreateAccount.setOnClickListener {
//            val intent = Intent(this, RegisterPage::class.java)
//            startActivity(intent)
//        }
    }
}