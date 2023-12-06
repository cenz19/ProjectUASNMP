package com.nmpubaya.cerbung

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nmpubaya.cerbung.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    var url = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsernameLogin.text.toString()
            val password = binding.txtPasswordLogin.text.toString()
            var isUserValid = false

            for (user in Global.LIST_USER) {
                if (user.username == username && user.password == password) {
                    url = user.picture_url
                    isUserValid = true
                    break
                }
            }

            if (isUserValid) {
                Toast.makeText(this, "Login Success, welcome ${username}", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomePage::class.java)
                intent.putExtra(CerbungAdapters.CARD_AUTHOR, username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCreateAccount.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }
}