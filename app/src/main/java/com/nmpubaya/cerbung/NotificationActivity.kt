package com.nmpubaya.cerbung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmpubaya.cerbung.databinding.ActivityCreateCerbung1Binding
import com.nmpubaya.cerbung.databinding.ActivityNotificationBinding
import org.json.JSONObject

class NotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNotificationBinding
    var notifs: ArrayList<Notification> = arrayListOf()
    fun updateList() {
        //Fungsi ini untuk setup adapter
        val lm = LinearLayoutManager(this)
        with(binding.notifRecView) {
            layoutManager = lm
            setHasFixedSize(true)
            adapter = NotifAdapter(notifs)
        }
    }
    fun reload(){
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421005/get_all_notifs.php"
        val stringRequest = object: StringRequest(
            Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Notification>>() { }.type
                    notifs = Gson().fromJson(data.toString(), sType) as ArrayList<Notification>
                    updateList()
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                return super.getParams()
            }
        }
        q.add(stringRequest)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        reload()
    }
}