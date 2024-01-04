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
import com.nmpubaya.cerbung.databinding.ActivityReadCerbungBinding
import org.json.JSONObject

class ReadCerbungActivity : AppCompatActivity() {
    private lateinit var binding: ActivityReadCerbungBinding

    fun getAccessCerbung(cerbung_id: Int, users_id: Int){
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421005/get_cerbung.php"
        var stringRequest = object : StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    if (data.length() > 0) {
                        val dataCerbung = data.getJSONObject(0)
                        val sType = object : TypeToken<Cerbung>() { }.type
                        val cerbung = Gson().fromJson(dataCerbung.toString(), sType) as Cerbung
                        if (cerbung.access == 1) {
                            val restrictedFragment = ReadRestrictedFragment.newInstance(cerbung, users_id)
                            supportFragmentManager.beginTransaction().apply {
                                add(R.id.container, restrictedFragment)
                                commit()
                            }
                        } else {
                            val publicFragment = ReadPublicFragment.newInstance(cerbung, users_id)
                            supportFragmentManager.beginTransaction().apply {
                                add(R.id.container, publicFragment)
                                commit()
                            }
                        }
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["id"] = cerbung_id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReadCerbungBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val cerbungId = intent.getIntExtra(CerbungAdapter.KEY_CERBUNG_ID, 0)
        val users_id = intent.getIntExtra(CerbungAdapter.KEY_USERS_ID, 0)
        getAccessCerbung(cerbungId, users_id)
    }
}