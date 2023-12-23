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

    fun getAccessCerbung(cerbung_id: Int){
        val q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/cerbungdb/get_cerbung.php"
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
                            val restrictedFragment = ReadRestrictedFragment.newInstance(cerbung)

                            supportFragmentManager?.beginTransaction()?.let {
                                it.replace(R.id.container, restrictedFragment)
                                it.addToBackStack(null)
                                it.commit()
                            }
                        } else {
                            val publicFragment = ReadPublicFragment.newInstance(cerbung)
                            supportFragmentManager?.beginTransaction()?.let {
                                it.replace(R.id.container, publicFragment)
                                it.addToBackStack(null)
                                it.commit()
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
        val cerbung_id = intent.getIntExtra(CerbungAdapter.KEY_CERBUNG_ID, 0)
        getAccessCerbung(cerbung_id)
    }
}