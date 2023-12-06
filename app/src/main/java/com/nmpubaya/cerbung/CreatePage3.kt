package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.nmpubaya.cerbung.databinding.ActivityCreatePage3Binding

class CreatePage3 : AppCompatActivity() {
    private lateinit var binding: ActivityCreatePage3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePage3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        //DATA FROM CREATE PAGE 2
        val url = intent.getStringExtra(CerbungAdapters.URL)
        val title = intent.getStringExtra(CerbungAdapters.CARD_TITLE)
        val author = intent.getStringExtra(CerbungAdapters.CARD_AUTHOR)
        val permission = intent.getBooleanExtra(CerbungAdapters.CARD_PERMISSION, true)
        val genre = intent.getStringExtra(CerbungAdapters.CARD_GENRE)
        val short_description = intent.getStringExtra(CerbungAdapters.CARD_SHORT_DESCRIPTION)
        val story = intent.getStringExtra(CerbungAdapters.CARD_STORY)

        //DISPLAYING THE DATA FROM CREATE PAGE 2 IN THIS PAGE
        binding.txtCreateTitle3.text = title
        binding.txtCreateGenre3.text = genre
        var permission_text = ""
        if (permission == true)
            permission_text = "Restricted"
        else
            permission_text = "Public"
        binding.txtCreatePermission3.text = permission_text
        binding.txtCreateShortDescription3.text = short_description
        binding.txtCreateStory3.text = story


        binding.btnPrev2.setOnClickListener {
            intent = Intent(this, CreatePage2::class.java)
//            intent.putExtra(CreatePage2.PARAGRAPH_KEY, paragraph)
//            intent.putExtra(CreatePage.TITLE_KEY, title)
//            intent.putExtra(CreatePage.DESC_KEY, desc)
//            intent.putExtra(CreatePage.IMG_CVR_KEY, img_cover)
            startActivity(intent)
        }

        binding.btnPublish.setOnClickListener{
            if (binding.checkBoxAgree.isChecked) {
                val newCrebung = Cerbung(url.toString(), title.toString(), author.toString(), permission,
                    0, 0, genre.toString(), short_description.toString(), story.toString())
                Global.cerbung.add(newCrebung)
                Toast.makeText(this, "Cerbung $title Successfully Created", Toast.LENGTH_SHORT).show()
                intent = Intent(this, HomePage::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please agree to our Terms of Agreement", Toast.LENGTH_LONG).show()
            }

        }
    }
}