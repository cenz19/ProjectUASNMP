package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nmpubaya.cerbung.databinding.ActivityCreateCerbung3Binding
import org.json.JSONObject
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateCerbung3Activity : AppCompatActivity() {
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

        binding.btnPrev3.setOnClickListener {
            val i = Intent(this, CreateCerbung2Activity::class.java)
            startActivity(i)
        }

        binding.btnPublish3.setOnClickListener {
            Toast.makeText(this, "ID:$id, Title:$title, Desc:$desc, Img:$img_cover, Genre:$genre_id, access:$access, first:$first_paragraph", Toast.LENGTH_LONG).show()
        }
    }
}