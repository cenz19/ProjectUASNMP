package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nmpubaya.cerbung.databinding.ActivityCerbungFullStoryPageBinding
import com.squareup.picasso.Picasso

class CerbungFullStoryPage : AppCompatActivity() {
    private lateinit var binding: ActivityCerbungFullStoryPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCerbungFullStoryPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //IMAGE FROM CERBUNG ADAPTERS
        val builder = Picasso.Builder(this)
        builder.listener { picasso, uri, exception -> exception.printStackTrace() }
        builder.build().load(intent.getStringExtra(CerbungAdapters.URL)).into(binding.imgFullStory)

        //ALL DATA FROM CERBUNG ADAPTERS
        binding.txtFullStoryTitle.text = intent.getStringExtra(CerbungAdapters.CARD_TITLE)
        binding.txtFullStoryAuthor.text = intent.getStringExtra(CerbungAdapters.CARD_AUTHOR)
        binding.txtFullStoryIconList.text = intent.getIntExtra(CerbungAdapters.ICON_LIST, 0).toString()
        binding.txtFullStoryIconLike.text = intent.getIntExtra(CerbungAdapters.ICON_LIKE, 0).toString()
        binding.txtFullStoryGenre.text = intent.getStringExtra(CerbungAdapters.CARD_GENRE)
        binding.txtFullStoryStory.text = intent.getStringExtra(CerbungAdapters.CARD_STORY)

        //BUTTON BACK
        binding.btnFullStoryBack.setOnClickListener{
            intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }
    }
}