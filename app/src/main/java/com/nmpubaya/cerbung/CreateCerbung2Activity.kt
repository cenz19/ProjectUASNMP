package com.nmpubaya.cerbung

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nmpubaya.cerbung.databinding.ActivityCreateCerbung2Binding

class CreateCerbung2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateCerbung2Binding
    companion object {
        val KEY_ACCESS = "access"
        val KEY_FIRST_PARAGRAPH = "first_paragraph"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateCerbung2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getIntExtra(CreateCerbung1Activity.KEY_ID, 0)
        val title = intent.getStringExtra(CreateCerbung1Activity.KEY_TITLE)
        val desc = intent.getStringExtra(CreateCerbung1Activity.KEY_DESC)
        val img_cover = intent.getStringExtra(CreateCerbung1Activity.KEY_IMG_COVER)
        val genre_id = intent.getIntExtra(CreateCerbung1Activity.KEY_GENRE_ID, 0)
        var access = intent.getIntExtra(KEY_ACCESS, 0)
        var first_par = intent.getStringExtra(KEY_FIRST_PARAGRAPH)

        if (access == 1) {
            binding.radioBtnRestricted.isChecked = true
            binding.radioBtnPublic.isChecked = false
        } else {
            binding.radioBtnPublic.isChecked = true
            binding.radioBtnRestricted.isChecked = false
        }

        binding.txtWriteFirstParagraph.setText(first_par)

        binding.radioGroupAccess.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.radioBtnRestricted) {
                access = 1
            } else if (checkedId == R.id.radioBtnPublic) {
                access = 2
            }
        }

        binding.btnPrev2.setOnClickListener {
            val i = Intent(this, CreateCerbung1Activity::class.java)
            startActivity(i)
        }
        binding.btnNext2.setOnClickListener {
            val first_paragraph = binding.txtWriteFirstParagraph.text.toString()
            val i = Intent(this, CreateCerbung3Activity::class.java)
            i.putExtra(CreateCerbung1Activity.KEY_ID, id)
            i.putExtra(CreateCerbung1Activity.KEY_TITLE, title)
            i.putExtra(CreateCerbung1Activity.KEY_DESC, desc)
            i.putExtra(CreateCerbung1Activity.KEY_IMG_COVER, img_cover)
            i.putExtra(CreateCerbung1Activity.KEY_GENRE_ID, genre_id)
            i.putExtra(KEY_ACCESS, access)
            i.putExtra(KEY_FIRST_PARAGRAPH, first_paragraph)
            startActivity(i)
        }
    }
}