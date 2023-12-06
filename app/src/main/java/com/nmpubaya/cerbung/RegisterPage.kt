package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nmpubaya.cerbung.databinding.ActivityRegisterPageBinding

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCancel.setOnClickListener{
            intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {
            val username = binding.txtUsernameRegister.text.toString()
            val profile_url = binding.txtUrlRegister.text.toString()
            val password = binding.txtPasswordRegister.text.toString()

            if (password == binding.txtRePasswordRegister.text.toString()) {
                val user = User(username, profile_url, password)
                Global.LIST_USER.add(user)
                Toast.makeText(this, "Berhasil. Silahkan login menggunakan akun anda", Toast.LENGTH_SHORT).show()
                intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Password dengan Re-Password tidak sama. Silahkan ulangi", Toast.LENGTH_SHORT).show()
                binding.txtPasswordRegister.text.clear()
                binding.txtRePasswordRegister.text.clear()
            }
        }
    }
}