package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nmpubaya.cerbung.databinding.ActivityCreateCerbung1Binding
import com.nmpubaya.cerbung.databinding.CardPlaylistBinding
import org.json.JSONObject

class CreateCerbung1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCerbung1Binding

    companion object {
        val KEY_ID = "id"
        val KEY_TITLE = "title"
        val KEY_DESC = "desc"
        val KEY_IMG_COVER = "img_cover"
        val KEY_GENRE_ID = "genre_id"
    }

    fun getDataPage1(id: Int) {
        val q = Volley.newRequestQueue(this)
        val url = "http://10.0.2.2/cerbungdb/get_all_genre.php"
        val stringRequest = StringRequest(Request.Method.POST, url,
            {
                Log.d("apiresult", it.toString())
                val obj = JSONObject(it)
                if (obj.getString("result") == "OK") {
                    val data = obj.getJSONArray("data")
                    val sType = object : TypeToken<List<Genre>>() { }.type
                    val genres = Gson().fromJson(data.toString(), sType) as ArrayList<Genre>

                    val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, genres)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinner.adapter = adapter

                    binding.btnNext1.setOnClickListener {
                        val title = binding.txtCerbungTitle.text.toString()
                        val desc = binding.txtShortDescription.text.toString()
                        val img_cover = binding.txtCerbungImageCover.text.toString()
                        val genre = binding.spinner.selectedItem as Genre
                        val genre_id = genre.id

                        val i = Intent(this, CreateCerbung2Activity::class.java)
                        i.putExtra(KEY_ID, id)
                        i.putExtra(KEY_TITLE, title)
                        i.putExtra(KEY_DESC, desc)
                        i.putExtra(KEY_IMG_COVER, img_cover)
                        i.putExtra(KEY_GENRE_ID, genre_id)
                        startActivity(i)
                    }
                }
            },
            {
                Log.e("apiresult", it.message.toString())
            }
        )
        q.add(stringRequest)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCerbung1Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(CreateFragment.KEY_ID, 0)
        val title = intent.getStringExtra(KEY_TITLE)
        val desc = intent.getStringExtra(KEY_DESC)
        val img_cover = intent.getStringExtra(KEY_IMG_COVER)

        binding.txtCerbungTitle.setText(title)
        binding.txtShortDescription.setText(desc)
        binding.txtCerbungImageCover.setText(img_cover)

        getDataPage1(id)

    }
}