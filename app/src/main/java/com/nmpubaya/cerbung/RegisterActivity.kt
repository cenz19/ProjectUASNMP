package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.snackbar.Snackbar
import com.nmpubaya.cerbung.databinding.ActivityRegisterBinding
import org.json.JSONObject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding

    fun register(username:String, password:String, regPass:String) {
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421005/get_user.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "ERROR") {
                    if (password == regPass) {
                        val regUrl = "https://ubaya.me/native/160421005/create_user.php"
                        val regStrRequest = object : StringRequest(Request.Method.POST, regUrl,
                            {
                                Toast.makeText(this, "Thanks for registering\nPlease login with your new account",
                                    Toast.LENGTH_LONG).show()
                                val i = Intent(this, MainActivity::class.java)
                                startActivity(i)
                                finish()
                            },
                            {
                                Log.e("apiresult", it.printStackTrace().toString())
                                Toast.makeText(this, "Failed to register", Toast.LENGTH_LONG).show()
                            }
                        ) {
                            override fun getParams(): MutableMap<String, String>? {
                                val params = HashMap<String, String>()
                                params["username"] = username
                                params["password"] = password
                                params["num_follower"] = "0"
                                return params
                            }
                        }
                        q.add(regStrRequest)
                    } else {
                        Toast.makeText(this, "Password doesn't match", Toast.LENGTH_LONG).show()
                    }
                } else if (obj.getString("result") == "OK") {
                    Toast.makeText(this, "Username already exists", Toast.LENGTH_LONG).show()
                }
            },
            {
                Log.e("apiresult", it.printStackTrace().toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            val username = binding.txtUsernameRegister.text.toString()
            val password = binding.txtPasswordRegister.text.toString()
            val regPass = binding.txtRepeatPasswordRegister.text.toString()
            register(username, password, regPass)
        }

        binding.btnCancel.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}