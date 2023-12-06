package com.nmpubaya.cerbung

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.nmpubaya.cerbung.databinding.ActivityCreatePageBinding

class CreatePage : AppCompatActivity() {
    private lateinit var binding:ActivityCreatePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //RECEIVE DATA FROM HOME PAGE (CHANGE VAL NAME FROM USERNAME TO AUTHOR)
        val author = intent.getStringExtra(CerbungAdapters.CARD_AUTHOR)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, Global.genre)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinGenre.adapter = adapter

        binding.btnCancelCerbung.setOnClickListener{
            intent = Intent(this, HomePage::class.java)
            startActivity(intent)
        }

        binding.btnNext.setOnClickListener {
            intent = Intent(this, CreatePage2::class.java)
            //SENDING DATA TO CREATE PAGE 2
            intent.putExtra(CerbungAdapters.CARD_TITLE, binding.txtCreateTitle.text.toString())
            intent.putExtra(CerbungAdapters.CARD_AUTHOR, author)
            intent.putExtra(CerbungAdapters.CARD_SHORT_DESCRIPTION, binding.txtCreateShortDescription.text.toString())
            intent.putExtra(CerbungAdapters.URL, binding.txtCreateUrl.text.toString())
            intent.putExtra(CerbungAdapters.CARD_GENRE, binding.spinGenre.selectedItem.toString())
            startActivity(intent)
        }

    }
}