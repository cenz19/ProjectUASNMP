package com.nmpubaya.cerbung

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmpubaya.cerbung.databinding.ActivityMainBinding
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding

    companion object {
        val KEY_USER_ID = "id"
        val KEY_USERNAME = "username"
        val KEY_NUM_FOLLOWER = "num_follower"
        val KEY_URL_PROFILE = "url_profile"
    }

    fun cekLogin(username:String, password:String) {
        val sharedFile = "com.nmpubaya.cerbung"
        val sharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val id = sharedPreferences.getInt(KEY_USER_ID, 0)
        val username_user = sharedPreferences.getString(KEY_USERNAME, "")
        val num_follower = sharedPreferences.getInt(KEY_NUM_FOLLOWER, 0)
        val url_profile = sharedPreferences.getString(KEY_URL_PROFILE, "")
        val editor = sharedPreferences.edit()

        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421005/get_user.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it)
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    if (data.length() > 0) {
                        val dataUser = data.getJSONObject(0)
                        val sType = object : TypeToken<User>() { }.type
                        val user = Gson().fromJson(dataUser.toString(), sType) as User
                        val dialog = AlertDialog.Builder(this)
                        dialog.setMessage("Login Successful, Welcome ${user.username}")
                        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            editor.putInt(KEY_USER_ID, user.id)
                            editor.putString(KEY_USERNAME, user.username)
                            editor.putString(KEY_URL_PROFILE, user.url_profile)
                            editor.apply()
                            val i = Intent(this, HomeActivity::class.java)
                            startActivity(i)
                            finish()
                        })
                        dialog.create().show()
                    } else {
                        val dialog = AlertDialog.Builder(this)
                        dialog.setMessage("Username or Password is incorrect")
                        dialog.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
                            dialog.dismiss()
                        })
                        dialog.create().show()
                    }
                }
            },
            {
                Log.e("apiresult", it.printStackTrace().toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["username"] = username
                params["password"] = password
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedFile = "com.nmpubaya.cerbung"
        val sharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        val nightMode = sharedPreferences.getBoolean("night", false)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsernameLogin.text.toString()
            val password = binding.txtPasswordLogin.text.toString()
            cekLogin(username, password)
        }

        binding.btnSignUp.setOnClickListener {
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }
    }
}