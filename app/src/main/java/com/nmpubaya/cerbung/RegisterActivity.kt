package com.nmpubaya.cerbung

import android.app.AlertDialog
import android.content.DialogInterface
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

    fun register(username:String, password:String, regPass:String, urlProfile:String) {
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
                                val dialog = AlertDialog.Builder(this)
                                dialog.setMessage("Thanks for registering\nPlease login with your new account")
                                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                                    val i = Intent(this, MainActivity::class.java)
                                    startActivity(i)
                                    finish()
                                })
                                dialog.create().show()
                            },
                            {
                                Log.e("apiresult", it.printStackTrace().toString())
                                val dialog = AlertDialog.Builder(this)
                                dialog.setMessage("Failed to register")
                                dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                                    dialog.dismiss()
                                })
                                dialog.create().show()
                            }
                        ) {
                            override fun getParams(): MutableMap<String, String>? {
                                val params = HashMap<String, String>()
                                params["username"] = username
                                params["password"] = password
                                params["num_follower"] = "0"
                                params["url_profile"] = urlProfile
                                return params
                            }
                        }
                        q.add(regStrRequest)
                    } else {
                        val dialog = AlertDialog.Builder(this)
                        dialog.setMessage("Password doesn't match")
                        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                        dialog.create().show()
                    }
                } else if (obj.getString("result") == "OK") {
                    val dialog = AlertDialog.Builder(this)
                    dialog.setMessage("Username already exists")
                    dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    dialog.create().show()
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
            val url_profile = binding.txtProfilePic.text.toString()
            register(username, password, regPass, url_profile)
        }

        binding.btnCancel.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
    }
}