package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nmpubaya.cerbung.databinding.ActivityCreateCerbung3Binding
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateCerbung3Activity : AppCompatActivity() {
    fun insertAndPublishCerbung(id: Int, title: String, desc: String, img_cover: String,
                                genre_id: Int, access: Int, first_paragraph: String) {
        val q = Volley.newRequestQueue(this)
        val url = "https://ubaya.me/native/160421005/create_cerbung.php"
        val stringRequest = object : StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val msg = obj.getJSONArray("msg")
                    Log.d("apiresult", msg.toString())
                } else {
                    Log.e("apiresult", it.toString())
                }
            },
            {
                Log.e("apiresult", it.printStackTrace().toString())
                Log.e("apiresult", it.message.toString())
            }) {
            override fun getParams(): MutableMap<String, String>? {
                val params = HashMap<String, String>()
                params["title"] = title
                params["description"] = desc
                params["num_likes"] = "0"
                params["access"] = access.toString()
                params["genre_id"] = genre_id.toString()
                params["num_paragraph"] = "0"
                params["url_gambar"] = img_cover
//                val date = LocalDateTime.now()
//                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
//                params["waktu_dibuat"] = date.format(formatter)
                params["users_id"] = id.toString()
                return params
            }
        }
        q.add(stringRequest)
    }
    private lateinit var binding: ActivityCreateCerbung3Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCerbung3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(CreateCerbung1Activity.KEY_ID, 0)
        val title = intent.getStringExtra(CreateCerbung1Activity.KEY_TITLE)
        val desc = intent.getStringExtra(CreateCerbung1Activity.KEY_DESC)
        val img_cover = intent.getStringExtra(CreateCerbung1Activity.KEY_IMG_COVER)
        val genre_id = intent.getIntExtra(CreateCerbung1Activity.KEY_GENRE_ID, 0)
        val access = intent.getIntExtra(CreateCerbung2Activity.KEY_ACCESS, 0)
        val first_paragraph = intent.getStringExtra(CreateCerbung2Activity.KEY_FIRST_PARAGRAPH)

        binding.txtTitleCreateCerbung3.text = title
        binding.txtShortDescriptionCreateCerbung3.text = desc
        if (access == 1) {
            binding.txtAccessCreateCerbung3.text = "Restricted"
        } else{
            binding.txtAccessCreateCerbung3.text = "Public"
        }
        binding.txtFirstParagraphCreateCerbung3.text = first_paragraph

        binding.btnPublish3.setOnClickListener {
            if (binding.checkBox.isChecked) {
                val new_title = binding.txtTitleCreateCerbung3.text.toString()
                val new_desc = binding.txtShortDescriptionCreateCerbung3.text.toString()
                val new_img_cover = img_cover
                val new_genre_id = genre_id
                val new_access = access
                val new_first_paragraph = binding.txtFirstParagraphCreateCerbung3.text.toString()
                val new_id = id
                if (new_title != null && new_desc != null && new_img_cover != null && new_first_paragraph != null) {
                    insertAndPublishCerbung(new_id, new_title, new_desc, new_img_cover, new_genre_id, new_access, new_first_paragraph)
                }
            }
        }
    }
}