package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
    }

    fun cekLogin(username:String, password:String) {
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
                        Toast.makeText(this,"Welcome ${user.username}", Toast.LENGTH_LONG).show()
                        val i = Intent(this, HomeActivity::class.java)
                        i.putExtra(KEY_USER_ID, user.id)
                        i.putExtra(KEY_USERNAME, user.username)
                        i.putExtra(KEY_NUM_FOLLOWER, user.num_follower)
                        startActivity(i)
                        finish()
                    } else {
                        Toast.makeText(this, "Username or Password is incorrect", Toast.LENGTH_LONG).show()
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